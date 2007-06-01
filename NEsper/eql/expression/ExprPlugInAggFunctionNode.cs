///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>Represents a custom aggregation function in an expresson tree.</summary>
	public class ExprPlugInAggFunctionNode : ExprAggregateNode
	{
	    private AggregationSupport aggregationSupport;

	    /// <summary>Ctor.</summary>
	    /// <param name="distinct">flag indicating unique or non-unique value aggregation</param>
	    /// <param name="aggregationSupport">
	    /// is the base class for plug-in aggregation functions
	    /// </param>
	    /// <param name="functionName">is the aggregation function name</param>
	    public ExprPlugInAggFunctionNode(bool distinct, AggregationSupport aggregationSupport, String functionName)
			: base(distinct)
	    {
	        this.aggregationSupport = aggregationSupport;
	        aggregationSupportFunctionName = functionName;
	    }

	    public AggregationMethod ValidateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
	    {
	        Class childType = null;
	        if (this.ChildNodes.Size() > 1)
	        {
	            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.FunctionName + "' requires a single parameter");
	        }
	        if (this.ChildNodes.Size() == 1)
	        {
	            childType = this.ChildNodes.Get(0).Type;
	        }

	        try
	        {
	            aggregationSupport.Validate(childType);
	        }
	        catch (RuntimeException ex)
	        {
	            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.FunctionName + "' failed validation: " + ex.Message);
	        }

	        return aggregationSupport;
	    }

	    public String GetAggregationFunctionName()
	    {
	        return aggregationSupport.FunctionName;
	    }

	    public override bool EqualsNodeAggregate(ExprAggregateNode node)
	    {
	        if (!(node is ExprPlugInAggFunctionNode))
	        {
	            return false;
	        }

	        ExprPlugInAggFunctionNode other = (ExprPlugInAggFunctionNode) node;
	        return other.AggregationFunctionName.Equals(this.AggregationFunctionName);
	    }
	}
} // End of namespace
