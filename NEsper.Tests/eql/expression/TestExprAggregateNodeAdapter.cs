///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public abstract class TestExprAggregateNodeAdapter
	{
	    protected ExprAggregateNode validatedNodeToTest;

	    [Test]
	    public void testEvaluate()
	    {
	        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
	        validatedNodeToTest.SetAggregationResultFuture(future, 1);

	        Assert.AreEqual(20, validatedNodeToTest.Evaluate(null, false));
	    }
	}

} // End of namespace
