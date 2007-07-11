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
	public class TestExprCountNode : TestExprAggregateNodeAdapter
	{
	    private ExprCountNode wildcardCount;

	    [SetUp]
	    public void SetUp()
	    {
	        base.validatedNodeToTest = MakeNode(5, typeof(int?));

	        wildcardCount = new ExprCountNode(false);
	        SupportExprNodeFactory.Validate(wildcardCount);
	    }

	    [Test]
	    public void testGetType()
	    {
            Assert.AreEqual(typeof(long?), validatedNodeToTest.ReturnType);
            Assert.AreEqual(typeof(long?), wildcardCount.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("count(5)", validatedNodeToTest.ExpressionString);
	        Assert.AreEqual("count(*)", wildcardCount.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
	        Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
	        Assert.IsTrue(wildcardCount.EqualsNode(wildcardCount));
	    }

	    private ExprCountNode MakeNode(Object value, Type type)
	    {
	        ExprCountNode countNode = new ExprCountNode(false);
	        countNode.AddChildNode(new SupportExprNode(value, type));
	        SupportExprNodeFactory.Validate(countNode);
	        return countNode;
	    }
	}
} // End of namespace
