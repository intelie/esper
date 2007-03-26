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
        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <value></value>
        virtual public MultiKey<Object> CurrentRow
        {
            set { }
        }

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public virtual void ApplyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
        }

        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public virtual void ApplyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow)
        {
        }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
        public virtual Object GetValue(int column)
        {
            return null;
        }
    }
}