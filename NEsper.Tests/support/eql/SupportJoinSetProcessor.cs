using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join;
using net.esper.events;

namespace net.esper.support.eql
{

    public class SupportJoinSetProcessor : JoinSetProcessor
    {
        private ISet<MultiKey<EventBean>> lastNewEvents;
        private ISet<MultiKey<EventBean>> lastOldEvents;

        public void Process(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            lastNewEvents = newEvents;
            lastOldEvents = oldEvents;
        }

        public ISet<MultiKey<EventBean>> getLastNewEvents()
        {
            return lastNewEvents;
        }

        public ISet<MultiKey<EventBean>> getLastOldEvents()
        {
            return lastOldEvents;
        }

        public virtual void reset()
        {
            lastNewEvents = null;
            lastOldEvents = null;
        }
    }
}
