using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.core;
using net.esper.events;

namespace net.esper.support.eql
{

    public class SupportAggregationService : AggregationService
    {
        virtual public MultiKey<Object> CurrentRow
        {
            set
            {
            }
        }

        private IList<Pair<EventBean[], MultiKey<Object>>> leaveList = new List<Pair<EventBean[], MultiKey<Object>>>();
        private IList<Pair<EventBean[], MultiKey<Object>>> enterList = new List<Pair<EventBean[], MultiKey<Object>>>();

        public virtual void applyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            leaveList.Add(new Pair<EventBean[], MultiKey<Object>>(eventsPerStream, optionalGroupKeyPerRow));
        }

        public virtual void applyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            enterList.Add(new Pair<EventBean[], MultiKey<Object>>(eventsPerStream, optionalGroupKeyPerRow));
        }

        public IList<Pair<EventBean[], MultiKey<Object>>> getLeaveList()
        {
            return leaveList;
        }

        public IList<Pair<EventBean[], MultiKey<Object>>> getEnterList()
        {
            return enterList;
        }

        public virtual Object getValue(int column)
        {
            return null;
        }
    }
}
