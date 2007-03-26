using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.core;
using net.esper.events;

namespace net.esper.support.eql
{
    public class SupportAggregationService : AggregationService
    {
        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <value></value>
        virtual public MultiKey<Object> CurrentRow
        {
            set
            {
            }
        }

        private IList<Pair<EventBean[], MultiKey<Object>>> leaveList = new List<Pair<EventBean[], MultiKey<Object>>>();
        private IList<Pair<EventBean[], MultiKey<Object>>> enterList = new List<Pair<EventBean[], MultiKey<Object>>>();

        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public virtual void ApplyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            leaveList.Add(new Pair<EventBean[], MultiKey<Object>>(eventsPerStream, optionalGroupKeyPerRow));
        }

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public virtual void ApplyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
            enterList.Add(new Pair<EventBean[], MultiKey<Object>>(eventsPerStream, optionalGroupKeyPerRow));
        }

        public IList<Pair<EventBean[], MultiKey<Object>>> LeaveList
        {
            get { return leaveList; }
        }

        public IList<Pair<EventBean[], MultiKey<Object>>> EnterList
        {
            get { return enterList; }
        }

        public virtual Object GetValue(int column)
        {
            return null;
        }
    }
}
