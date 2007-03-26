using System;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.table
{
    /// <summary>
    /// Simple table of events without an index.
    /// </summary>

    public class UnindexedEventTable : EventTable
    {
        private readonly int streamNum;
        private ISet<EventBean> eventSet = new EHashSet<EventBean>();

        /// <summary> Ctor.</summary>
        /// <param name="streamNum">is the indexed stream's number
        /// </param>

        public UnindexedEventTable(int streamNum)
        {
            this.streamNum = streamNum;
        }

        /// <summary>
        /// Adds the specified events.
        /// </summary>
        /// <param name="addEvents">The events to add.</param>
        public virtual void Add(EventBean[] addEvents)
        {
            if (addEvents == null)
            {
                return;
            }

            for (int i = 0; i < addEvents.Length; i++)
            {
                eventSet.Add(addEvents[i]);
            }
        }

        /// <summary>
        /// Removes the specified events.
        /// </summary>
        /// <param name="removeEvents">The events to remove.</param>
        public virtual void Remove(EventBean[] removeEvents)
        {
            if (removeEvents == null)
            {
                return;
            }

            for (int i = 0; i < removeEvents.Length; i++)
            {
                eventSet.Remove(removeEvents[i]);
            }
        }

        /// <summary> Returns events in table.</summary>
        /// <returns> all events
        /// </returns>

        public ISet<EventBean> getEventSet()
        {
            return eventSet;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "UnindexedEventTable streamNum=" + streamNum;
        }
    }
}