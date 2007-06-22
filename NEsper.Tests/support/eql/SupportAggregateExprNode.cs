///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.compat;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportAggregateExprNode : ExprAggregateNode
	{
	    private static int validateCount;

	    private Type type;
	    private Object value;
	    private int validateCountSnapshot;

	    public static void SetValidateCount(int validateCount)
	    {
	        SupportAggregateExprNode.validateCount = validateCount;
	    }

	    public SupportAggregateExprNode(Type type)
	    	: base(false)
	    {
	        this.type = type;
	        this.value = null;
	    }

	    public SupportAggregateExprNode(Object value)
	    	: base(false)
	    {
	        this.type = value.GetType();
	        this.value = value;
	    }

	    public SupportAggregateExprNode(Object value, Type type)
	    	: base(false)
	    {
	        this.value = value;
	        this.type = type;
	    }

	    protected override AggregationMethod ValidateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
	    {
	        // Keep a count for if and when this was validated
	        validateCount++;
	        validateCountSnapshot = validateCount;
	        return null;
	    }

		public override Type ReturnType
		{
	    	get { return type; }
		}
	    
	    public int ValidateCountSnapshot
	    {
	    	get { return validateCountSnapshot; }
	    }

//	    public AggregationMethod AggregationFunction
//	    {
//	    	get { return null; }
//	    }

	    public override String AggregationFunctionName
	    {
	    	get { return "support"; }
	    }

	    public override bool EqualsNodeAggregate(ExprAggregateNode node)
	    {
	        throw new UnsupportedOperationException("not implemented");
	    }

	    public void EvaluateEnter(EventBean[] eventsPerStream)
	    {
	    }

	    public void EvaluateLeave(EventBean[] eventsPerStream)
	    {
	    }

	    public void SetValue(Object value)
	    {
	        this.value = value;
	    }
	}
} // End of namespace
