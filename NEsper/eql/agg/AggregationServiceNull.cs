///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.events;

namespace net.esper.eql.agg
{
    /// <summary>
    /// A null object implementation of the AggregationServiceinterface.
    /// </summary>

    public class AggregationServiceNull : AggregationService
    {

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public void ApplyEnter(EventBean[] eventsPerStream,
                               MultiKeyUntyped optionalGroupKeyPerRow)
        {
        }

        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
        public void ApplyLeave(EventBean[] eventsPerStream,
                               MultiKeyUntyped optionalGroupKeyPerRow)
        {
        }

        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <param name="groupKey">key identify the row of aggregation states</param>
        public void SetCurrentRow(MultiKeyUntyped groupKey)
        {
        }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
        public Object GetValue(int column)
        {
            return null;
        }
    }
} // End of namespace
