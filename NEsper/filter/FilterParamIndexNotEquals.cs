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
    /// Index for filter parameter constants to match using the equals (=) operator.
	/// The implementation is based on a regular HashMap.
	/// </summary>
    public sealed class FilterParamIndexNotEquals : FilterParamIndexPropBase
    {
        private readonly EDictionary<Object, EventEvaluator> constantsMap;
        private readonly ReaderWriterLock constantsMapRWLock;

        /// <summary> Constructs the index for exact matches.</summary>
        /// <param name="propertyName">is the name of the event property
        /// </param>
        /// <param name="eventType">describes the event type and is used to obtain a getter instance for the property
        /// </param>
        public FilterParamIndexNotEquals(String propertyName, EventType eventType)
            : base(propertyName, FilterOperator.NOT_EQUAL, eventType)
        {
            constantsMap = new HashDictionary<Object, EventEvaluator>();
            constantsMapRWLock = new ReaderWriterLock();
        }

        /// <summary>
        /// Gets or sets the <see cref="net.esper.filter.EventEvaluator"/> with the specified filter constant.
        /// Returns null if no entry found for the constant.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded access, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <value></value>
        public override EventEvaluator this[Object filterConstant]
        {
            get
            {
                CheckType(filterConstant);
                return constantsMap.Fetch(filterConstant, null);
            }
            set
            {
                CheckType(filterConstant);
                constantsMap[filterConstant] = value;
            }
        }

        /// <summary>
        /// Remove the event evaluation instance for the given constant. Returns true if
        /// the constant was found, or false if not.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <param name="filterConstant">is the value supplied in the filter paremeter</param>
        /// <returns>
        /// true if found and removed, false if not found
        /// </returns>
        public override bool Remove(Object filterConstant)
        {
            return constantsMap.Remove(filterConstant) ;
        }

        /// <summary>
        /// Return the number of distinct filter parameter constants stored.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <value></value>
        /// <returns> Number of entries in index
        /// </returns>
        public override int Count
        {
            get
            {
                return constantsMap.Count;
            }
        }

        /// <summary>
        /// Supplies the lock for protected access.
        /// </summary>
        /// <value></value>
        /// <returns> lock
        /// </returns>
        public override ReaderWriterLock ReadWriteLock
        {
            get
            {
                return constantsMapRWLock;
            }
        }

        /// <summary>
        /// Matches the event.
        /// </summary>
        /// <param name="eventBean">The event bean.</param>
        /// <param name="matches">The matches.</param>
        public override void MatchEvent(EventBean eventBean, IList<FilterHandle> matches)
        {
            Object attributeValue = this.Getter.GetValue(eventBean);

            if (FilterParamIndexNotEquals.log.IsDebugEnabled)
            {
                FilterParamIndexNotEquals.log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") attributeValue=" + attributeValue);
            }

            try
            {
                // Look up in hashtable
                constantsMapRWLock.AcquireReaderLock(LockConstants.ReaderTimeout);

		        foreach (Object key in constantsMap.Keys)
		        {
		            if (key == null)
		            {
		                if (attributeValue != null)
		                {
		                    EventEvaluator evaluator = constantsMap.Fetch(key);
		                    evaluator.MatchEvent(eventBean, matches);
		                }
		            }
		            else
		            {
		                if (attributeValue != null)
		                {
		                    if (!key.Equals(attributeValue))
		                    {
		                        EventEvaluator evaluator = constantsMap.Fetch(key);
		                        evaluator.MatchEvent(eventBean, matches);
		                    }
		                }
		                else
		                {
		                    // no this should not match: "val != null" doesn't match if val is 'a'
		                }
		            }
		        }
            }
            finally
            {
                constantsMapRWLock.ReleaseReaderLock();
            }
        }

        private void CheckType(Object filterConstant)
        {
	        if (filterConstant != null)
	        {
                Type filterConstantType = TypeHelper.GetBoxedType(filterConstant.GetType());
	            if (this.PropertyBoxedType != filterConstantType)
	            {
	                throw new ArgumentException("Invalid type of filter constant of " +
	                        filterConstantType.FullName + " for property " + this.PropertyName);
	            }
	        }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
