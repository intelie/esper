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
	public class TestExprMinMaxRowNode
	{
	    private ExprMinMaxRowNode minMaxNode;

	    [SetUp]
	    public void SetUp()
	    {
	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
	    }

	    [Test]
	    public void testGetType()
	    {
	        minMaxNode.AddChildNode(new SupportExprNode(typeof(double?)));
	        minMaxNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        minMaxNode.Validate(null, null, null);
	        Assert.AreEqual(typeof(double?), minMaxNode.ReturnType);

	        minMaxNode.AddChildNode(new SupportExprNode(typeof(double?)));
	        minMaxNode.Validate(null, null, null);
	        Assert.AreEqual(typeof(double?), minMaxNode.ReturnType);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        minMaxNode.AddChildNode(new SupportExprNode(9d));
	        minMaxNode.AddChildNode(new SupportExprNode(6));
	        Assert.AreEqual("max(9,6)", minMaxNode.ExpressionString);
	        minMaxNode.AddChildNode(new SupportExprNode(0.5d));
	        Assert.AreEqual("max(9,6,0.5)", minMaxNode.ExpressionString);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Must have 2 or more subnodes
	        try
	        {
	            minMaxNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Must have only number-type subnodes
	        minMaxNode.AddChildNode(new SupportExprNode(typeof(String)));
	        minMaxNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            minMaxNode.Validate(null, null, null);
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
	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
	        SetupNode(minMaxNode, 10, 1.5, null);
	        Assert.AreEqual(10d, minMaxNode.Evaluate(null, false));

	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
	        SetupNode(minMaxNode, 1, 1.5, null);
	        Assert.AreEqual(1.5d, minMaxNode.Evaluate(null, false));

	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
	        SetupNode(minMaxNode, 1, 1.5, null);
	        Assert.AreEqual(1d, minMaxNode.Evaluate(null, false));

	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
	        SetupNode(minMaxNode, 1, 1.5, 2.0f);
	        Assert.AreEqual(2.0d, minMaxNode.Evaluate(null, false));

	        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
	        SetupNode(minMaxNode, 6, 3.5, 2.0f);
	        Assert.AreEqual(2.0d, minMaxNode.Evaluate(null, false));

	        minMaxNode = MakeNode(null, typeof(int?), 5, typeof(int?), 6, typeof(int?));
	        Assert.IsNull(minMaxNode.Evaluate(null, false));
	        minMaxNode = MakeNode(7, typeof(int?), null, typeof(int?), 6, typeof(int?));
	        Assert.IsNull(minMaxNode.Evaluate(null, false));
	        minMaxNode = MakeNode(3, typeof(int?), 5, typeof(int?), null, typeof(int?));
	        Assert.IsNull(minMaxNode.Evaluate(null, false));
	        minMaxNode = MakeNode(null, typeof(int?), null, typeof(int?), null, typeof(int?));
	        Assert.IsNull(minMaxNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(minMaxNode.EqualsNode(minMaxNode));
	        Assert.IsFalse(minMaxNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
	        Assert.IsFalse(minMaxNode.EqualsNode(new ExprOrNode()));
	    }

	    private static void SetupNode(ExprMinMaxRowNode nodeMin, int intValue, double doubleValue, float? floatValue)
	    {
	        nodeMin.AddChildNode(new SupportExprNode(intValue));
	        nodeMin.AddChildNode(new SupportExprNode(doubleValue));
	        if (floatValue != null)
	        {
	            nodeMin.AddChildNode(new SupportExprNode(floatValue));
	        }
	        nodeMin.GetValidatedSubtree(null, null, null);
	    }

	    private ExprMinMaxRowNode MakeNode(Object valueOne, Type typeOne,
	                                       Object valueTwo, Type typeTwo,
	                                       Object valueThree, Type typeThree)
	    {
	        ExprMinMaxRowNode maxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
	        maxNode.AddChildNode(new SupportExprNode(valueOne, typeOne));
	        maxNode.AddChildNode(new SupportExprNode(valueTwo, typeTwo));
	        maxNode.AddChildNode(new SupportExprNode(valueThree, typeThree));
	        return maxNode;
	    }

	}
} // End of namespace
