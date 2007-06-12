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
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.agg
{
    /// <summary>
	/// Implementation for handling aggregation with grouping by group-keys.
	/// </summary>
	public class AggregationServiceGroupByImpl : AggregationServiceBase
	{
	    // maintain for each group a row of aggregator states that the expression node canb pull the data from via index
	    private EDictionary<MultiKeyUntyped, AggregationMethod[]> aggregatorsPerGroup;

	    // maintain a current row for random access into the aggregator state table
	    // (row=groups, columns=expression nodes that have aggregation functions)
	    private AggregationMethod[] currentAggregatorRow;

	    private MethodResolutionService methodResolutionService;

	    /// <summary>Ctor.</summary>
	    /// <param name="evaluators">
	    /// evaluate the sub-expression within the aggregate function (ie. Sum(4*myNum))
	    /// </param>
	    /// <param name="prototypes">
	    /// collect the aggregation state that evaluators evaluate to, act as prototypes for new aggregations
	    /// aggregation states for each group
	    /// </param>
	    /// <param name="methodResolutionService">
	    /// factory for creating additional aggregation method instances per group key
	    /// </param>
	    public AggregationServiceGroupByImpl(ExprEvaluator[] evaluators, AggregationMethod[] prototypes, MethodResolutionService methodResolutionService)
            : base(evaluators, prototypes)
	    {
	        this.methodResolutionService = methodResolutionService;
	        this.aggregatorsPerGroup = new EHashDictionary<MultiKeyUntyped, AggregationMethod[]>();
	    }

        /// <summary>
        /// Applies the enter.
        /// </summary>
        /// <param name="eventsPerStream">The events per stream.</param>
        /// <param name="groupByKey">The group by key.</param>
	    public override void ApplyEnter(EventBean[] eventsPerStream, MultiKeyUntyped groupByKey)
	    {
	        AggregationMethod[] groupAggregators = aggregatorsPerGroup.Fetch(groupByKey);

	        // The aggregators for this group do not exist, need to create them from the prototypes
	        if (groupAggregators == null)
	        {
	            groupAggregators = methodResolutionService.NewAggregators(aggregators, groupByKey);
	            aggregatorsPerGroup[groupByKey] = groupAggregators;
	        }

	        // For this row, evaluate sub-expressions, enter result
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, true);
	            groupAggregators[j].Enter(columnResult);
	        }
	    }

        /// <summary>
        /// Applies the leave.
        /// </summary>
        /// <param name="eventsPerStream">The events per stream.</param>
        /// <param name="groupByKey">The group by key.</param>
	    public override void ApplyLeave(EventBean[] eventsPerStream, MultiKeyUntyped groupByKey)
	    {
	        AggregationMethod[] groupAggregators = aggregatorsPerGroup.Fetch(groupByKey);

	        // The aggregators for this group do not exist, need to create them from the prototypes
	        if (groupAggregators == null)
	        {
	            groupAggregators = methodResolutionService.NewAggregators(aggregators, groupByKey);
	            aggregatorsPerGroup[groupByKey] = groupAggregators;
	        }

	        // For this row, evaluate sub-expressions, enter result
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, false);
	            groupAggregators[j].Leave(columnResult);
	        }
	    }

        /// <summary>
        /// Sets the current row.
        /// </summary>
        /// <param name="groupByKey">The group by key.</param>
	    public override void SetCurrentRow(MultiKeyUntyped groupByKey)
	    {
	        currentAggregatorRow = aggregatorsPerGroup.Fetch(groupByKey);

	        if (currentAggregatorRow == null)
	        {
	            currentAggregatorRow = methodResolutionService.NewAggregators(aggregators, groupByKey);
	            aggregatorsPerGroup[groupByKey] = currentAggregatorRow;
	        }
	    }

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
	    public override Object GetValue(int column)
	    {
	        return currentAggregatorRow[column].Value;
	    }
	}
} // End of namespace
