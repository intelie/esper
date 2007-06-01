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

	        if (evaluators.length != aggregators.length)
	        {
	            throw new IllegalArgumentException("Expected the same number of evaluates as computer prototypes");
	        }
	    }
	}
} // End of namespace
