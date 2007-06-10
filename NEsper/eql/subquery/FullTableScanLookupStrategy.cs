///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.events;

namespace net.esper.eql.subquery
{
    /// <summary>
    /// Lookup on an unindexed table returning the full table as matching events.
    /// </summary>
    public class FullTableScanLookupStrategy : SubqueryTableLookupStrategy
    {
        private UnindexedEventTable eventIndex;

        /// <summary>Ctor.</summary>
        /// <param name="eventIndex">table to use</param>
        public FullTableScanLookupStrategy(UnindexedEventTable eventIndex)
        {
            this.eventIndex = eventIndex;
        }

        public Set<EventBean> Lookup(EventBean[] eventPerStream)
        {
            Set<EventBean> result = eventIndex.GetEventSet();
            if (result.Count == 0)
            {
                return null;
            }
            return result;
        }

        /// <summary>Returns the associated table.</summary>
        /// <returns>table for lookup.</returns>
        public UnindexedEventTable GetEventIndex()
        {
            return eventIndex;
        }
    }
}
