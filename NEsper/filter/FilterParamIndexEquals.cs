using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.filter
{
    /// <summary> Index for filter parameter constants to match using the equals (=) operator.
    /// The implementation is based on a regular HashMap.
    /// </summary>

    public sealed class FilterParamIndexEquals : FilterParamIndex
    {
        private readonly EDictionary<Object, EventEvaluator> constantsMap;
        private readonly ReaderWriterLock constantsMapRWLock;

        /// <summary> Constructs the index for exact matches.</summary>
        /// <param name="propertyName">is the name of the event property
        /// </param>
        /// <param name="eventType">describes the event type and is used to obtain a getter instance for the property
        /// </param>
        public FilterParamIndexEquals(String propertyName, EventType eventType)
            : base(propertyName, FilterOperator.EQUAL, eventType)
        {
            constantsMap = new EHashDictionary<Object, EventEvaluator>();
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
                checkType(filterConstant);
                return constantsMap.Fetch(filterConstant);
            }
            set
            {
                checkType(filterConstant);
                constantsMap[filterConstant] = value;
            }
        }

        public override bool Remove(Object filterConstant)
        {
            return constantsMap.Remove(filterConstant);
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

            if (log.IsDebugEnabled)
            {
                log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") attributeValue=" + attributeValue);
            }

            if (attributeValue == null)
            {
                return;
            }

            // Look up in hashtable
            constantsMapRWLock.AcquireReaderLock(LockConstants.ReaderTimeout);
            EventEvaluator evaluator = constantsMap.Fetch(attributeValue, null);
            constantsMapRWLock.ReleaseReaderLock();

            // No listener found for the value, return
            if (evaluator == null)
            {
                return;
            }

            evaluator.matchEvent(eventBean, matches);
        }

        private void checkType(Object filterConstant)
        {
            if (this.PropertyBoxedType != TypeHelper.GetBoxedType(filterConstant.GetType()))
            {
                throw new ArgumentException("Invalid type of filter constant of " + filterConstant.GetType().FullName + " for property " + this.PropertyName);
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(FilterParamIndexEquals));
    }
}
