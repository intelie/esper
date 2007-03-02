using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.filter
{
    /// <summary>
    /// Index for filter parameter constants for the comparison operators (less, greater, etc).
    /// The implementation is based on the SortedDictionary implementation of SortedDictionary.
    /// The index only accepts numeric constants. It keeps a lower and upper bounds of all constants in the index
    /// for fast range checking, since the assumption is that frequently values fall within a range.
    /// </summary>

    public sealed class FilterParamIndexCompare : FilterParamIndex
    {
        private readonly ETreeDictionary<Object, EventEvaluator> constantsMap;
        private readonly ReaderWriterLock constantsMapRWLock;

        private double? lowerBounds;
        private double? upperBounds;

        /// <summary> Constructs the index for matching comparison operators (<, >, <=, >=).</summary>
        /// <param name="propertyName">is the name of the event attribute field
        /// </param>
        /// <param name="filterOperator">is the type of relational comparison operator
        /// </param>
        /// <param name="eventType">describes the event type and is used to obtain a getter instance for the property
        /// for fast get value access.
        /// </param>

        public FilterParamIndexCompare(
        	String propertyName,
        	FilterOperator filterOperator,
        	EventType eventType)
            : base(propertyName, filterOperator, eventType)
        {
            constantsMap = new ETreeDictionary<Object, EventEvaluator>(sm_doubleComparer);
            constantsMapRWLock = new ReaderWriterLock();

            if ((filterOperator != FilterOperator.GREATER) && 
                (filterOperator != FilterOperator.GREATER_OR_EQUAL) &&
                (filterOperator != FilterOperator.LESS) &&
                (filterOperator != FilterOperator.LESS_OR_EQUAL))
            {
                throw new ArgumentException("Invalid filter operator for index of " + filterOperator);
            }

            if (!TypeHelper.IsNumeric(this.PropertyBoxedType))
            {
                throw new ArgumentException("Property named '" + propertyName + "' is not numeric");
            }
        }

        public override EventEvaluator this[Object filterConstant]
        {
            get
            {
                CheckType(filterConstant);
                Double constant = Convert.ToDouble(filterConstant);
                return constantsMap.Fetch(constant, null);
            }

            set
            {
                CheckType(filterConstant);
                Double constant = Convert.ToDouble(filterConstant);
                constantsMap[constant] = value;

                if ((lowerBounds == null) || (constant < lowerBounds))
                {
                    lowerBounds = constant;
                }
                
                if ((upperBounds == null) || (constant > upperBounds))
                {
                    upperBounds = constant;
                }
            }
        }

        public override bool Remove(Object filterConstant)
        {
            Double constant = Convert.ToDouble(filterConstant);
        	if (! constantsMap.Remove(constant))
            {
                return false;
            }

            UpdateBounds();

            return true;
        }

        public override int Count
        {
            get
            {
                return constantsMap.Count;
            }
        }

        public override ReaderWriterLock ReadWriteLock
        {
            get
            {
                return constantsMapRWLock;
            }
        }

        public override void MatchEvent(EventBean eventBean, IList<FilterCallback> matches)
        {
            Object propertyValue = this.Getter.GetValue(eventBean);

            if (propertyValue == null)
            {
                return;
            }

            // A undefine lower bound indicates an empty index
            if (lowerBounds == null)
            {
                return;
            }

            FilterOperator filterOperator = this.FilterOperator;
            Double propertyValueDouble = Convert.ToDouble(propertyValue);

            if (log.IsDebugEnabled)
            {
                log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") propertyValue=" + propertyValue + "  filterOperator=" + filterOperator);
            }

            // Based on current lower and upper bounds check if the property value falls outside - shortcut submap generation
            if ((filterOperator == FilterOperator.GREATER) && (propertyValueDouble <= lowerBounds))
            {
                return;
            }
            else if ((filterOperator == FilterOperator.GREATER_OR_EQUAL) && (propertyValueDouble < lowerBounds))
            {
                return;
            }
            else if ((filterOperator == FilterOperator.LESS) && (propertyValueDouble >= upperBounds))
            {
                return;
            }
            else if ((filterOperator == FilterOperator.LESS_OR_EQUAL) && (propertyValueDouble > upperBounds))
            {
                return;
            }

            // Look up in table
            constantsMapRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );

            // Get the head or tail end of the map depending on comparison type
            IDictionary<Object, EventEvaluator> subMap;

            if ((filterOperator == FilterOperator.GREATER) ||
                (filterOperator == FilterOperator.GREATER_OR_EQUAL))
            {
                // At the head of the map are those with a lower numeric constants
                subMap = constantsMap.Head(propertyValueDouble);
            }
            else
            {
                subMap = constantsMap.Tail(propertyValueDouble);
            }

            // All entries in the subMap are elgibile, with an exception
            EventEvaluator exactEquals = null;
            if (filterOperator == FilterOperator.LESS)
            {
                exactEquals = constantsMap.Fetch(propertyValueDouble);
            }

            foreach (EventEvaluator matcher in subMap.Values)
            {
                // For the LESS comparison type we ignore the exactly equal case
                // The subMap is sorted ascending, thus the exactly equals case is the first
                if (exactEquals != null)
                {
                    exactEquals = null;
                    continue;
                }

                matcher.MatchEvent(eventBean, matches);
            }

            if (filterOperator == FilterOperator.GREATER_OR_EQUAL)
            {
                EventEvaluator matcher = constantsMap.Fetch(propertyValueDouble);
                if (matcher != null)
                {
                    matcher.MatchEvent(eventBean, matches);
                }
            }

            constantsMapRWLock.ReleaseReaderLock();
        }

        private void UpdateBounds()
        {
            if (constantsMap.Count == 0)
            {
                lowerBounds = null;
                upperBounds = null;
                return;
            }

            lowerBounds = Convert.ToDouble(constantsMap.FirstKey);
            upperBounds = Convert.ToDouble(constantsMap.LastKey);
        }

        private void CheckType(Object filterConstant)
        {
            if (this.PropertyBoxedType != TypeHelper.GetBoxedType(filterConstant.GetType()))
            {
                throw new ArgumentException(
                    "Invalid type of filter constant of " + filterConstant.GetType().FullName + 
                    " for property " + this.PropertyName);
            }
        }

        internal class DoubleComparer : IComparer<Object>
        {
            public int Compare(Object valueA, Object valueB)
            {
                Double v1 = Convert.ToDouble(valueA);
                Double v2 = Convert.ToDouble(valueB);
                return v1.CompareTo(v2);
            }
        }

        private static DoubleComparer sm_doubleComparer = new DoubleComparer();

        private static readonly Log log = LogFactory.GetLog(typeof(FilterParamIndexCompare));
    }
}
