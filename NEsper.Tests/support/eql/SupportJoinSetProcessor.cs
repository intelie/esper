using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join;
using net.esper.events;

namespace net.esper.support.eql
{

    public class SupportJoinSetProcessor : JoinSetProcessor
    {
        private Set<MultiKey<EventBean>> lastNewEvents;
        private Set<MultiKey<EventBean>> lastOldEvents;

        public void Process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
        {
            lastNewEvents = newEvents;
            lastOldEvents = oldEvents;
        }

        public Set<MultiKey<EventBean>> getLastNewEvents()
        {
            return lastNewEvents;
        }

        public Set<MultiKey<EventBean>> getLastOldEvents()
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
