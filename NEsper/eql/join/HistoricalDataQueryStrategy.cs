using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.view;

namespace net.esper.eql.join
{
    /// <summary> Query strategy for use with {@link HistoricalEventViewable}
    /// to perform lookup for a given stream using the poll method on a viewable.
    /// </summary>

    public class HistoricalDataQueryStrategy : QueryStrategy
    {
        private readonly int myStreamNumber;
        private readonly int historicalStreamNumber;
        private readonly HistoricalEventViewable historicalEventViewable;
        private readonly EventBean[][] lookupRows1Event;
        private readonly bool isOuterJoin;
        private readonly ExprEqualsNode outerJoinCompareNode;

        /// <summary> Ctor.</summary>
        /// <param name="myStreamNumber">is the strategy's stream number
        /// </param>
        /// <param name="historicalStreamNumber">is the stream number of the view to be polled
        /// </param>
        /// <param name="historicalEventViewable">is the view to be polled from
        /// </param>
        /// <param name="isOuterJoin">is this is an outer join
        /// </param>
        /// <param name="outerJoinCompareNode">is the node to perform the on-comparison for outer joins
        /// </param>
        public HistoricalDataQueryStrategy(int myStreamNumber, int historicalStreamNumber, HistoricalEventViewable historicalEventViewable, bool isOuterJoin, ExprEqualsNode outerJoinCompareNode)
        {
            this.myStreamNumber = myStreamNumber;
            this.historicalStreamNumber = historicalStreamNumber;
            this.historicalEventViewable = historicalEventViewable;
            this.isOuterJoin = isOuterJoin;
            this.outerJoinCompareNode = outerJoinCompareNode;

            lookupRows1Event = new EventBean[1][];
            lookupRows1Event[0] = new EventBean[2];
        }

        /// <summary>
        /// Look up events returning tuples of joined events.
        /// </summary>
        /// <param name="lookupEvents">events to use to perform the join</param>
        /// <param name="joinSet">result join tuples of events</param>
        public void Lookup(EventBean[] lookupEvents, ISet<MultiKey<EventBean>> joinSet)
        {
            EventBean[][] lookupRows;

            // If looking up a single event, reuse the buffered array
            if (lookupEvents.Length == 1)
            {
                lookupRows = lookupRows1Event;
                lookupRows[0][myStreamNumber] = lookupEvents[0];
            }
            else
            {
                // Prepare rows with each row N events where N is the number of streams
                lookupRows = new EventBean[lookupEvents.Length][];
                for (int i = 0; i < lookupEvents.Length; i++)
                {
                    lookupRows[i] = new EventBean[2];
                    lookupRows[i][myStreamNumber] = lookupEvents[i];
                }
            }

            IList<EventBean>[] result = historicalEventViewable.Poll(lookupRows);

            int count = 0;
            foreach (IList<EventBean> rowsPerLookup in result)
            {
                // In an outer join 
                if ((isOuterJoin) && (rowsPerLookup.Count == 0))
                {
                    EventBean[] resultRow = new EventBean[2];
                    resultRow[myStreamNumber] = lookupEvents[count];
                    joinSet.Add(new MultiKey<EventBean>(resultRow));
                }
                else
                {
                    foreach (EventBean resultEvent in rowsPerLookup)
                    {
                        EventBean[] resultRow = new EventBean[2];
                        resultRow[myStreamNumber] = lookupEvents[count];
                        resultRow[historicalStreamNumber] = resultEvent;

                        // In an outer join compare the on-fields
                        if (isOuterJoin)
                        {
                            Boolean compareResult = (Boolean) outerJoinCompareNode.Evaluate(resultRow);
                            if (compareResult)
                            {
                                joinSet.Add(new MultiKey<EventBean>(resultRow));
                            }
                        }
                        else
                        {
                            joinSet.Add(new MultiKey<EventBean>(resultRow));
                        }
                    }
                }
                count++;
            }
        }
    }
}