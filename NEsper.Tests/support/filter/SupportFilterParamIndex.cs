using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.filter
{
    public class SupportFilterParamIndex : FilterParamIndex
    {
        override public ReaderWriterLock ReadWriteLock
        {
            get { return null; }
        }

        public SupportFilterParamIndex()
            : base("intPrimitive", FilterOperator.EQUAL, SupportEventTypeFactory.createBeanType(typeof(SupportBean)))
        {
        }

        public override EventEvaluator this[Object expressionValue]
        {
            get { return null; }
            set { }
        }

        public override bool Remove(Object expressionValue)
        {
            return true;
        }

        public override int Count
        {
            get { return 0; }
        }

        public override void matchEvent(EventBean _event, IList<FilterCallback> matches)
        {
        }
    }
}
