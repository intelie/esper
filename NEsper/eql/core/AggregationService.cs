using System;

using net.esper.collection;
using net.esper.events;

namespace net.esper.eql.core
{
	/// <summary> Service for maintaing aggregation state. Processes events entering (a window, a join etc,) and
	/// events leaving. Answers questions about current aggrataion state for a given row.
	/// </summary>
	public interface AggregationService:AggregationResultFuture
	{
		/// <summary> Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.</summary>
		/// <param name="groupKey">- key identify the row of aggregation states
		/// </param>

        MultiKey<Object> CurrentRow
		{
			set;
		}

		/// <summary> Apply events as entering a window (new events).</summary>
		/// <param name="eventsPerStream">- events for each stream entering window
		/// </param>
		/// <param name="optionalGroupKeyPerRow">- can be null if grouping without keys is desired, else the keys
		/// to use for grouping, each distinct key value results in a new row of aggregation state.
		/// </param>
        void applyEnter(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow);
		
		/// <summary> Apply events as leaving a window (old events).</summary>
		/// <param name="eventsPerStream">- events for each stream entering window
		/// </param>
		/// <param name="optionalGroupKeyPerRow">- can be null if grouping without keys is desired, else the keys
		/// to use for grouping, each distinct key value results in a new row of aggregation state.
		/// </param>
        void applyLeave(EventBean[] eventsPerStream, MultiKey<Object> optionalGroupKeyPerRow);
	}
}