///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprConstantNode
	{
	    private ExprConstantNode constantNode;

	    [SetUp]
	    public void SetUp()
	    {
	        constantNode = new ExprConstantNode("5");
	    }

	    [Test]
	    public void testGetType()
	    {
            Assert.AreEqual(typeof(String), constantNode.ReturnType);

	        constantNode = new ExprConstantNode(null);
	        Assert.IsNull(constantNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        constantNode.Validate(null, null, null);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        Assert.AreEqual("5", constantNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        constantNode = new ExprConstantNode("5");
	        Assert.AreEqual("\"5\"", constantNode.ExpressionString);

	        constantNode = new ExprConstantNode(10);
	        Assert.AreEqual("10", constantNode.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(constantNode.EqualsNode(new ExprConstantNode("5")));
	        Assert.IsFalse(constantNode.EqualsNode(new ExprOrNode()));
	        Assert.IsFalse(constantNode.EqualsNode(new ExprConstantNode(null)));
	        Assert.IsFalse(constantNode.EqualsNode(new ExprConstantNode(3)));

	        constantNode = new ExprConstantNode(null);
	        Assert.IsTrue(constantNode.EqualsNode(new ExprConstantNode(null)));
	    }
	}
} // End of namespace
