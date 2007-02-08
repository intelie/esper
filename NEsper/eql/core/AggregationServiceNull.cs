using System;

using net.esper.collection;
using net.esper.events;

namespace net.esper.eql.core
{
    /// <summary> A null object implementation of the AggregationService
    /// interface.
    /// </summary>
    
    public class AggregationServiceNull : AggregationService
    {
        virtual public MultiKey<Object> CurrentRow
        {
            set { }
        }

        public virtual void applyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
        }

        public virtual void applyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
        }

        public virtual Object getValue(int column)
        {
            return null;
        }
    }
}