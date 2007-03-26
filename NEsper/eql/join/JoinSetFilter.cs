using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.join
{
    /// <summary>
    /// Processes join tuple set by filtering out tuples.
    /// </summary>

    public class JoinSetFilter : JoinSetProcessor
    {
        private ExprNode filterExprNode;

        /// <summary> Ctor.</summary>
        /// <param name="filterExprNode">filter tree
        /// </param>

        public JoinSetFilter(ExprNode filterExprNode)
        {
            this.filterExprNode = filterExprNode;
        }

        /// <summary>
        /// Process join result set.
        /// </summary>
        /// <param name="newEvents">set of event tuples representing new data</param>
        /// <param name="oldEvents">set of event tuples representing old data</param>
        public void Process(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            // Filter
            if (filterExprNode != null)
            {
                Filter(filterExprNode, newEvents);
                Filter(filterExprNode, oldEvents);
            }
        }

        /// <summary> Filter event by applying the filter nodes evaluation method.</summary>
        /// <param name="filterExprNode">top node of the filter expression tree.
        /// </param>
        /// <param name="events">set of tuples of events
        /// </param>

        public static void Filter(ExprNode filterExprNode, ISet<MultiKey<EventBean>> events)
        {
            List<MultiKey<EventBean>> purgeList = null;
            
            foreach( MultiKey<EventBean> key in events )
            {
                EventBean[] eventArr = key.Array;

                bool matched = (bool)filterExprNode.Evaluate(eventArr);

                if (!matched)
                {
                    if (purgeList == null)
                    {
                        purgeList = new List<MultiKey<EventBean>>();
                    }

                    purgeList.Add(key);
                }
            }

            if (purgeList != null)
            {
                foreach (MultiKey<EventBean> key in purgeList)
                {
                    events.Remove(key);
                }
            }
        }
    }
}
