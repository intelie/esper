using System;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.join.table;

namespace net.esper.eql.join.exec
{
	/// <summary> Lookup on an unindexed table returning the full table as matching events.</summary>
    public class FullTableScanLookupStrategy : TableLookupStrategy
    {
        private UnindexedEventTable eventIndex;

        /**
         * Ctor.
         * @param eventIndex - table to use
         */
        public FullTableScanLookupStrategy(UnindexedEventTable eventIndex)
        {
            this.eventIndex = eventIndex;
        }

        public ISet<EventBean> lookup(EventBean ev)
        {
            ISet<EventBean> result = eventIndex.getEventSet();
            if (result.IsEmpty)
            {
                return null;
            }
            return result;
        }

        /**
         * Returns the associated table.
         * @return table for lookup.
         */
        public UnindexedEventTable getEventIndex()
        {
            return eventIndex;
        }
    }
}