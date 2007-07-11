///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.agg;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprAvgNode : TestExprAggregateNodeAdapter
	{
	    private ExprAvgNode avgNodeDistinct;

	    [SetUp]
	    public void SetUp()
	    {
	        base.validatedNodeToTest = MakeNode(5, typeof(int?), false);
	        this.avgNodeDistinct = MakeNode(6, typeof(int?), true);
	    }

	    [Test]
	    public void testAggregation()
	    {
	        AvgAggregator agg = new AvgAggregator();
	        Assert.AreEqual(typeof(double?), agg.ValueType);
	        Assert.AreEqual(null, agg.Value);

	        agg.Enter(5);
	        Assert.AreEqual(5d, agg.Value);

	        agg.Enter(10);
	        Assert.AreEqual(7.5d, agg.Value);

	        agg.Leave(5);
	        Assert.AreEqual(10d, agg.Value);
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(double?), validatedNodeToTest.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("avg(5)", validatedNodeToTest.ExpressionString);
	        Assert.AreEqual("avg(distinct 6)", avgNodeDistinct.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
	        Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
	    }

	    private ExprAvgNode MakeNode(Object value, Type type, bool isDistinct)
	    {
	        ExprAvgNode avgNode = new ExprAvgNode(isDistinct);
	        avgNode.AddChildNode(new SupportExprNode(value, type));
	        SupportExprNodeFactory.Validate(avgNode);
	        return avgNode;
	    }
	}
} // End of namespace
