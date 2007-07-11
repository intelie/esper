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
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprConcatNode
	{
	    private ExprConcatNode concatNode;

	    [SetUp]
	    public void SetUp()
	    {
	        concatNode = new ExprConcatNode();
	    }

	    [Test]
	    public void testGetType()
	    {
            Assert.AreEqual(typeof(String), concatNode.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        concatNode = new ExprConcatNode();
	        concatNode.AddChildNode(new SupportExprNode("a"));
	        concatNode.AddChildNode(new SupportExprNode("b"));
	        Assert.AreEqual("(\"a\"||\"b\")", concatNode.ExpressionString);
	        concatNode.AddChildNode(new SupportExprNode("c"));
	        Assert.AreEqual("(\"a\"||\"b\"||\"c\")", concatNode.ExpressionString);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Must have 2 or more String subnodes
	        try
	        {
	            concatNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Must have only string-type subnodes
	        concatNode.AddChildNode(new SupportExprNode(typeof(String)));
	        concatNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            concatNode.Validate(null, null, null);
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
	        concatNode.AddChildNode(new SupportExprNode("x"));
	        concatNode.AddChildNode(new SupportExprNode("y"));
	        Assert.AreEqual("xy", concatNode.Evaluate(null, false));
	        concatNode.AddChildNode(new SupportExprNode("z"));
	        Assert.AreEqual("xyz", concatNode.Evaluate(null, false));
	        concatNode.AddChildNode(new SupportExprNode(null));
	        Assert.AreEqual(null, concatNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(concatNode.EqualsNode(concatNode));
	        Assert.IsFalse(concatNode.EqualsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
	    }
	}
} // End of namespace
