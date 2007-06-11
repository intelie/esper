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

	    public override void ApplyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, true);
	            aggregators[j].Enter(columnResult);
	        }
	    }

	    public override void ApplyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        for (int j = 0; j < evaluators.Length; j++)
	        {
	            Object columnResult = evaluators[j].Evaluate(eventsPerStream, false);
	            aggregators[j].Leave(columnResult);
	        }
	    }

	    public override void SetCurrentRow(MultiKeyUntyped groupKey)
	    {
	        // no action needed - this implementation does not group and the current row is the single group
	    }

	    public override Object GetValue(int column)
	    {
	        return aggregators[column].Value;
	    }
	}
} // End of namespace
