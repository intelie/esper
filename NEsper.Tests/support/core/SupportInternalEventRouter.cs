using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.events;

namespace net.esper.support.core
{

    public class SupportInternalEventRouter : InternalEventRouter
    {
        private IList<EventBean> routed = new List<EventBean>();

        public virtual void Route(EventBean _event)
        {
            routed.Add(_event);
        }

        public IList<EventBean> getRouted()
        {
            return routed;
        }

        public virtual void reset()
        {
            routed.Clear();
        }
    }
}
