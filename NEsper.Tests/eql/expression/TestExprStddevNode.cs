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
	public class TestExprStddevNode : TestExprAggregateNodeAdapter
	{
	    [SetUp]
	    public void SetUp()
	    {
	        base.validatedNodeToTest = MakeNode(5, typeof(int?));
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(double?), validatedNodeToTest.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("stddev(5)", validatedNodeToTest.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(validatedNodeToTest.EqualsNode(validatedNodeToTest));
	        Assert.IsFalse(validatedNodeToTest.EqualsNode(new ExprSumNode(false)));
	    }

	    private ExprStddevNode MakeNode(Object value, Type type)
	    {
	        ExprStddevNode stddevNode = new ExprStddevNode(false);
	        stddevNode.AddChildNode(new SupportExprNode(value, type));
	        SupportExprNodeFactory.Validate(stddevNode);
	        return stddevNode;
	    }
	}
} // End of namespace
