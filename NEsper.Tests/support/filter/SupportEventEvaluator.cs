using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{

    public class SupportEventEvaluator : EventEvaluator
    {
        virtual public EventBean LastEvent
        {
            get
            {
                return lastEvent;
            }

            set
            {
                this.lastEvent = value;
            }

        }
        virtual public int CountInvoked
        {
            set
            {
                this.countInvoked = value;
            }

        }
        virtual public int AndResetCountInvoked
        {
            get
            {
                int count = countInvoked;
                countInvoked = 0;
                return count;
            }

        }
        private int countInvoked;
        private EventBean lastEvent;
        private IList<FilterCallback> lastMatches;

        public void matchEvent(EventBean _event, IList<FilterCallback> matches)
        {
            countInvoked++;
            lastEvent = _event;
            lastMatches = matches;
        }

        public IList<FilterCallback> getLastMatches()
        {
            return lastMatches;
        }

        public void setLastMatches(IList<FilterCallback> lastMatches)
        {
            this.lastMatches = lastMatches;
        }
    }
}
