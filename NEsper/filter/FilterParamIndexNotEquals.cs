using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary> Index for filter parameter constants to match using the equals (=) operator.
	/// The implementation is based on a regular HashMap.
	/// </summary>
    public sealed class FilterParamIndexNotEquals : FilterParamIndex
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
            constantsMap = new EHashDictionary<Object, EventEvaluator>();
            constantsMapRWLock = new ReaderWriterLock();
        }

        public override EventEvaluator this[Object filterConstant]
        {
            get
            {
                checkType(filterConstant);
                return constantsMap.Fetch(filterConstant, null);
            }
            set
            {
                checkType(filterConstant);
                constantsMap[filterConstant] = value;
            }
        }

        public override bool Remove(Object filterConstant)
        {
            return constantsMap.Remove(filterConstant) ;
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

        public override void matchEvent(EventBean eventBean, IList<FilterCallback> matches)
        {
            Object attributeValue = this.Getter.GetValue(eventBean);

            if (FilterParamIndexNotEquals.log.IsDebugEnabled)
            {
                FilterParamIndexNotEquals.log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") attributeValue=" + attributeValue);
            }

            if (attributeValue == null)
            {
                return;
            }

            // Look up in hashtable
            constantsMapRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );

            foreach (KeyValuePair<Object, EventEvaluator> entry in constantsMap)
            {
                if (!entry.Key.Equals(attributeValue))
                {
                    EventEvaluator evaluator = entry.Value;
                    evaluator.matchEvent(eventBean, matches);
                }
            }
            constantsMapRWLock.ReleaseReaderLock();
        }

        private void checkType(Object filterConstant)
        {
            if (this.PropertyBoxedType != filterConstant.GetType())
            {
                throw new ArgumentException("Invalid type of filter constant of " + filterConstant.GetType().FullName + " for property " + this.PropertyName);
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(FilterParamIndexNotEquals));
    }
}
