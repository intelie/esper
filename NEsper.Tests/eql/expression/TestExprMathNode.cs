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
	public class TestExprMathNode
	{
	    private ExprMathNode arithNode;

	    [SetUp]
	    public void SetUp()
	    {
	        arithNode = new ExprMathNode(MathArithTypeEnum.ADD);
	    }

	    [Test]
	    public void testGetType()
	    {
	        arithNode.AddChildNode(new SupportExprNode(typeof(double?)));
	        arithNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        arithNode.Validate(null, null, null);
	        Assert.AreEqual(typeof(double?), arithNode.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        // Build (5*(4-2)), not the same as 5*4-2
	        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
	        arithNodeChild.AddChildNode(new SupportExprNode(4));
	        arithNodeChild.AddChildNode(new SupportExprNode(2));

	        arithNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
	        arithNode.AddChildNode(new SupportExprNode(5));
	        arithNode.AddChildNode(arithNodeChild);

	        Assert.AreEqual("(5*(4-2))", arithNode.ExpressionString);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Must have exactly 2 subnodes
	        try
	        {
	            arithNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Must have only number-type subnodes
	        arithNode.AddChildNode(new SupportExprNode(typeof(String)));
	        arithNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            arithNode.Validate(null, null, null);
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
	        arithNode.AddChildNode(new SupportExprNode(10));
	        arithNode.AddChildNode(new SupportExprNode(1.5));
	        arithNode.GetValidatedSubtree(null, null, null);
	        Assert.AreEqual(11.5d, arithNode.Evaluate(null, false));

	        arithNode = MakeNode(null, typeof(int?), 5d, typeof(double?));
	        Assert.IsNull(arithNode.Evaluate(null, false));

	        arithNode = MakeNode(5, typeof(int?), null, typeof(double?));
	        Assert.IsNull(arithNode.Evaluate(null, false));

	        arithNode = MakeNode(null, typeof(int?), null, typeof(double?));
	        Assert.IsNull(arithNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(arithNode.EqualsNode(arithNode));
	        Assert.IsFalse(arithNode.EqualsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
	    }

	    private ExprMathNode MakeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight)
	    {
	        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
	        mathNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
	        mathNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
	        return mathNode;
	    }
	}
} // End of namespace
