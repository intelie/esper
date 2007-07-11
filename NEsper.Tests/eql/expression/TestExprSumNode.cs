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
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprSumNode : TestExprAggregateNodeAdapter
	{
	    private ExprSumNode sumNode;

	    [SetUp]
	    public void SetUp()
	    {
	        sumNode = new ExprSumNode(false);

	        base.validatedNodeToTest = MakeNode(5, typeof(int?));
	    }

	    [Test]
	    public void testGetType()
	    {
	        sumNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        SupportExprNodeFactory.Validate(sumNode);
	        Assert.AreEqual(typeof(int?), sumNode.ReturnType);

	        sumNode = new ExprSumNode(false);
	        sumNode.AddChildNode(new SupportExprNode(typeof(float?)));
	        SupportExprNodeFactory.Validate(sumNode);
	        Assert.AreEqual(typeof(float?), sumNode.ReturnType);

	        sumNode = new ExprSumNode(false);
	        sumNode.AddChildNode(new SupportExprNode(typeof(short?)));
	        SupportExprNodeFactory.Validate(sumNode);
	        Assert.AreEqual(typeof(int?), sumNode.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        // Build Sum(4-2)
	        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
	        arithNodeChild.AddChildNode(new SupportExprNode(4));
	        arithNodeChild.AddChildNode(new SupportExprNode(2));

	        sumNode = new ExprSumNode(false);
	        sumNode.AddChildNode(arithNodeChild);

	        Assert.AreEqual("sum((4-2))", sumNode.ExpressionString);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Must have exactly 1 subnodes
	        try
	        {
	            sumNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Must have only number-type subnodes
	        sumNode.AddChildNode(new SupportExprNode(typeof(String)));
	        sumNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            sumNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testMakeAggregator()
	    {
            Assert.IsTrue(MakeNode(5, typeof(int?)).PrototypeAggregator is IntegerSumAggregator);
	        Assert.IsTrue(MakeNode(5, typeof(float?)).PrototypeAggregator is FloatSumAggregator);
            Assert.IsTrue(MakeNode(5, typeof(double?)).PrototypeAggregator is DoubleSumAggregator);
            Assert.IsTrue(MakeNode(5, typeof(short?)).PrototypeAggregator is NumIntegerSumAggregator);
            Assert.IsTrue(MakeNode(5, typeof(long?)).PrototypeAggregator is LongSumAggregator);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(sumNode.EqualsNode(sumNode));
	        Assert.IsFalse(sumNode.EqualsNode(new ExprOrNode()));
	    }

	    private ExprSumNode MakeNode(Object value, Type type)
	    {
	        ExprSumNode sumNode = new ExprSumNode(false);
	        sumNode.AddChildNode(new SupportExprNode(value, type));
	        SupportExprNodeFactory.Validate(sumNode);
	        return sumNode;
	    }
	}
} // End of namespace
