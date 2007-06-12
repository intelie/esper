///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.agg
{
    /// <summary>
	/// Implementation for handling aggregation without any grouping (no group-by).
	/// </summary>
	public class AggregationServiceGroupAllImpl : AggregationServiceBase
	{
	    /// <summary>Ctor.</summary>
	    /// <param name="evaluators">
	    /// evaluate the sub-expression within the aggregate function (ie. Sum(4*myNum))
	    /// </param>
	    /// <param name="aggregators">
	    /// collect the aggregation state that evaluators evaluate to
	    /// </param>
	    public AggregationServiceGroupAllImpl(ExprEvaluator[] evaluators, AggregationMethod[] aggregators)
            : base(evaluators, aggregators)
	    {
	    }

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
	    public override void ApplyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, true);
	            aggregators[j].Enter(columnResult);
	        }
	    }

        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
	    public override void ApplyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, false);
	            aggregators[j].Leave(columnResult);
	        }
	    }

        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <param name="groupKey">key identify the row of aggregation states</param>
	    public override void SetCurrentRow(MultiKeyUntyped groupKey)
	    {
	        // no action needed - this implementation does not group and the current row is the single group
	    }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
	    public override Object GetValue(int column)
	    {
	        return aggregators[column].Value;
	    }
	}
} // End of namespace
