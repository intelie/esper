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
	public class TestExprMinMaxAggrNode : TestExprAggregateNodeAdapter
	{
	    private ExprMinMaxAggrNode maxNode;
	    private ExprMinMaxAggrNode minNode;

	    [SetUp]
	    public void SetUp()
	    {
	        maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
	        minNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MIN);

	        base.validatedNodeToTest = MakeNode(MinMaxTypeEnum.MAX, 5, typeof(int?));
	    }

	    [Test]
	    public void testGetType()
	    {
	        maxNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        SupportExprNodeFactory.Validate(maxNode);
	        Assert.AreEqual(typeof(int?), maxNode.ReturnType);

	        minNode.AddChildNode(new SupportExprNode(typeof(float?)));
	        SupportExprNodeFactory.Validate(minNode);
            Assert.AreEqual(typeof(float?), minNode.ReturnType);

	        maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
	        maxNode.AddChildNode(new SupportExprNode(typeof(short?)));
	        SupportExprNodeFactory.Validate(maxNode);
	        Assert.AreEqual(typeof(short?), maxNode.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        // Build Sum(4-2)
	        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
	        arithNodeChild.AddChildNode(new SupportExprNode(4));
	        arithNodeChild.AddChildNode(new SupportExprNode(2));

	        maxNode.AddChildNode(arithNodeChild);
	        Assert.AreEqual("max((4-2))", maxNode.ExpressionString);
	        minNode.AddChildNode(arithNodeChild);
	        Assert.AreEqual("min((4-2))", minNode.ExpressionString);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Must have exactly 1 subnodes
	        try
	        {
	            minNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Must have only number-type subnodes
	        minNode.AddChildNode(new SupportExprNode(typeof(String)));
	        minNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            minNode.Validate(null, null, null);
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
	        MinMaxTypeEnum type = MinMaxTypeEnum.MAX;
	        Assert.IsTrue(MakeNode(type, 5, typeof(int?)).PrototypeAggregator is MinMaxAggregator);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(minNode.EqualsNode(minNode));
	        Assert.IsFalse(maxNode.EqualsNode(minNode));
	        Assert.IsFalse(minNode.EqualsNode(new ExprSumNode(false)));
	    }

	    private ExprMinMaxAggrNode MakeNode(MinMaxTypeEnum minMaxType, Object value, Type type)
	    {
	        ExprMinMaxAggrNode minMaxNode = new ExprMinMaxAggrNode(false, minMaxType);
	        minMaxNode.AddChildNode(new SupportExprNode(value, type));
	        SupportExprNodeFactory.Validate(minMaxNode);
	        return minMaxNode;
	    }
	}
} // End of namespace
