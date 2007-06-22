///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportBoolExprNode : ExprNode
	{
	    private bool evaluateResult;

	    public SupportBoolExprNode(bool evaluateResult)
	    {
	        this.evaluateResult = evaluateResult;
	    }

	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	    }

		public override Type ReturnType
		{
	    	get { return typeof(bool?); }
		}

	    public override bool IsConstantResult
	    {
	    	get { return false; }
	    }

	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        return evaluateResult;
	    }

	    public override String ExpressionString
	    {
	    	get { return null; }
	    }

	    public override bool EqualsNode(ExprNode node)
	    {
	        throw new UnsupportedOperationException("not implemented");
	    }
	}
} // End of namespace
