///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.agg;
using net.esper.eql.expression;

namespace net.esper.eql.agg
{
	/// <summary>
	/// All aggregation services require evaluation nodes which supply the value to be aggregated (summed, averaged, etc.)
	/// and aggregation state factories to make new aggregation states.
	/// </summary>
	public abstract class AggregationServiceBase : AggregationService
	{
	    /// <summary>Evaluation nodes under.</summary>
	    protected ExprEvaluator[] evaluators;

	    /// <summary>Aggregation states and factories.</summary>
	    protected AggregationMethod[] aggregators;

	    /// <summary>Ctor.</summary>
	    /// <param name="evaluators">
	    /// are the child node of each aggregation function used for computing the value to be aggregated
	    /// </param>
	    /// <param name="aggregators">aggregation states/factories</param>
	    public AggregationServiceBase(ExprEvaluator[] evaluators, AggregationMethod[] aggregators)
	    {
	        this.evaluators = evaluators;
	        this.aggregators = aggregators;

	        if (evaluators.Length != aggregators.Length)
	        {
	            throw new ArgumentException("Expected the same number of evaluates as computer prototypes");
	        }
	    }

        /// <summary>
        /// Apply events as entering a window (new events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
		public abstract void ApplyEnter(net.esper.events.EventBean[] eventsPerStream, net.esper.collection.MultiKeyUntyped optionalGroupKeyPerRow);
        /// <summary>
        /// Apply events as leaving a window (old events).
        /// </summary>
        /// <param name="eventsPerStream">events for each stream entering window</param>
        /// <param name="optionalGroupKeyPerRow">can be null if grouping without keys is desired, else the keys
        /// to use for grouping, each distinct key value results in a new row of aggregation state.</param>
		public abstract void ApplyLeave(net.esper.events.EventBean[] eventsPerStream, net.esper.collection.MultiKeyUntyped optionalGroupKeyPerRow);
        /// <summary>
        /// Set the current aggregation state row - for use when evaluation nodes are asked to evaluate.
        /// </summary>
        /// <param name="groupKey">key identify the row of aggregation states</param>
		public abstract void SetCurrentRow(net.esper.collection.MultiKeyUntyped groupKey);
        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
		public abstract object GetValue(int column);
	}
} // End of namespace
