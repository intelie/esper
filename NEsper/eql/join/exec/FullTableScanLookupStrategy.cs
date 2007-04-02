using System;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.join.table;

namespace net.esper.eql.join.exec
{
    /// <summary>
    /// Lookup on an unindexed table returning the full table as matching events.
    /// </summary>
    
    public class FullTableScanLookupStrategy : TableLookupStrategy
    {
        private UnindexedEventTable eventIndex;

        /// <summary>Ctor.</summary>
        /// <param name="eventIndex">table to use</param>

        public FullTableScanLookupStrategy(UnindexedEventTable eventIndex)
        {
            this.eventIndex = eventIndex;
        }

        /// <summary>
        /// Lookups the specified ev.
        /// </summary>
        /// <param name="ev">The ev.</param>
        /// <returns></returns>
        public ISet<EventBean> Lookup(EventBean ev)
        {
            ISet<EventBean> result = eventIndex.EventSet;
            if (result.IsEmpty)
            {
                return null;
            }
            return result;
        }

        /// <summary>
        /// Returns the associated table. 
        /// </summary>
        /// <returns>table for lookup</returns>

        public UnindexedEventTable EventIndex
        {
            get { return eventIndex; }
        }
    }
}