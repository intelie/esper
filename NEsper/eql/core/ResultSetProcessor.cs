using System;

using net.esper.events;
using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.core
{
	
	/// <summary> Processor for result sets coming from 2 sources. First, out of a simple view (on join).
	/// And second, out of a join of event streams. The processor must apply the select-clause, grou-by-clause and having-clauses
	/// as supplied. It must state what the event type of the result rows is.
	/// </summary>
    public interface ResultSetProcessor
    {
        /// <summary> Returns the event type of processed results.</summary>
        /// <returns> event type of the resulting events posted by the processor.
        /// </returns>
        EventType ResultEventType { get; }

        /// <summary> For use by views posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newData">new events posted by view
        /// </param>
        /// <param name="oldData">old events posted by view
        /// </param>
        /// <returns> pair of new events and old events
        /// </returns>

        Pair<EventBean[], EventBean[]> ProcessViewResult(EventBean[] newData, EventBean[] oldData);

        /// <summary> For use by joins posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newEvents">new events posted by join
        /// </param>
        /// <param name="oldEvents">old events posted by join
        /// </param>
        /// <returns> pair of new events and old events 
        /// </returns>

        Pair<EventBean[], EventBean[]> ProcessJoinResult(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents);
    }
}