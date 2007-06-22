using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{
    public class SupportResultSetProcessor : ResultSetProcessor
    {
        /// <summary>
        /// Returns the event type of processed results.
        /// </summary>
        /// <value></value>
        /// <returns> event type of the resulting events posted by the processor.
        /// </returns>
        virtual public EventType ResultEventType
        {
            get
            {
                return SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
            }
        }

        /// <summary>
        /// For use by views posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newData">new events posted by view</param>
        /// <param name="oldData">old events posted by view</param>
        /// <returns>pair of new events and old events</returns>
        public Pair<EventBean[], EventBean[]> ProcessViewResult(EventBean[] newData, EventBean[] oldData)
        {
            return new Pair<EventBean[], EventBean[]>(newData, oldData);
        }

        /// <summary>
        /// For use by joins posting their result, process the event rows that are entered and removed (new and old events).
        /// Processes according to select-clauses, group-by clauses and having-clauses and returns new events and
        /// old events as specified.
        /// </summary>
        /// <param name="newEvents">new events posted by join</param>
        /// <param name="oldEvents">old events posted by join</param>
        /// <returns>pair of new events and old events</returns>
        public Pair<EventBean[], EventBean[]> ProcessJoinResult(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents)
        {
            return new Pair<EventBean[], EventBean[]>(
                CollectionHelper.First( newEvents ).Array,
                CollectionHelper.First( oldEvents ).Array);
        }
    }
}
