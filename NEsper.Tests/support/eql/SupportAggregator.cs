using System;

using net.esper.eql.core;

namespace net.esper.support.eql
{

    public class SupportAggregator : Aggregator
    {
        virtual public Object Value
        {
            get
            {
                return sum;
            }

        }
        virtual public Type ValueType
        {
            get
            {
                return typeof(Int32);
            }

        }
        private int sum;

        public virtual void Enter(Object value)
        {
            if (value != null)
            {
                sum += (Int32)value;
            }
        }

        public virtual void Leave(Object value)
        {
            if (value != null)
            {
                sum -= (Int32)value;
            }
        }

        public virtual Aggregator NewAggregator()
        {
            return new SupportAggregator();
        }
    }
}
