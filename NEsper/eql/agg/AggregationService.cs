///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.collection;
using net.esper.events;

namespace net.esper.eql.agg
{
    /// <summary>
	/// Service for maintaing aggregation state. Processes events entering (a window, a join etc,) and
	/// events leaving. Answers questions about current aggrataion state for a given row.
	/// </summary>
	public interface AggregationService : AggregationResultFuture
	{
	    /// <summary>Apply events as entering a window (new events).</summary>
	    /// <param name="eventsPerStream">events for each stream entering window</param>
	    /// <param name="optionalGroupKeyPerRow">
	    /// can be null if grouping without keys is desired, else the keys
	    /// to use for grouping, each distinct key value results in a new row of aggregation state.
	    /// </param>
	    void ApplyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow);

	    /// <summary>Apply events as leaving a window (old events).</summary>
	    /// <param name="eventsPerStream">events for each stream entering window</param>
	    /// <param name="optionalGroupKeyPerRow">
	    /// can be null if grouping without keys is desired, else the keys
	    /// to use for grouping, each distinct key value results in a new row of aggregation state.
	    /// </param>
	    void ApplyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow);

	    /// <summary>
	    /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
	    /// </summary>
	    /// <param name="groupKey">key identify the row of aggregation states</param>
	    void SetCurrentRow(MultiKeyUntyped groupKey);
	}
} // End of namespace
