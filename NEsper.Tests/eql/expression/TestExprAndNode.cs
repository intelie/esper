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
	public class TestExprAndNode
	{
	    private ExprAndNode andNode;

	    [SetUp]
	    public void SetUp()
	    {
	        andNode = new ExprAndNode();
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), andNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // test success
	        andNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        andNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        andNode.Validate(null, null, null);

	        // test failure, type mismatch
	        andNode.AddChildNode(new SupportExprNode(typeof(String)));
	        try
	        {
	            andNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // test failed - with just one child
	        andNode = new ExprAndNode();
	        andNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        try
	        {
	            andNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        andNode.AddChildNode(new SupportBoolExprNode(true));
	        andNode.AddChildNode(new SupportBoolExprNode(true));
	        Assert.IsTrue( (Boolean) andNode.Evaluate(null, false));

	        andNode = new ExprAndNode();
	        andNode.AddChildNode(new SupportBoolExprNode(true));
	        andNode.AddChildNode(new SupportBoolExprNode(false));
	        Assert.IsFalse( (Boolean) andNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        andNode.AddChildNode(new SupportExprNode(true));
	        andNode.AddChildNode(new SupportExprNode(false));

	        Assert.AreEqual("(True AND False)", andNode.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(andNode.EqualsNode(new ExprAndNode()));
	        Assert.IsFalse(andNode.EqualsNode(new ExprOrNode()));
	    }
	}
} // End of namespace
