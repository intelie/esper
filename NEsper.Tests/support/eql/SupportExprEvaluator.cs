///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportExprEvaluator : ExprEvaluator
	{
	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        return eventsPerStream[0]["boolPrimitive"];
	    }
	}
} // End of namespace
