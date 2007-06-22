///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprEqualsNode
	{
	    private ExprEqualsNode[] equalsNodes;

	    [SetUp]
	    public void SetUp()
	    {
	        equalsNodes = new ExprEqualsNode[4];
	        equalsNodes[0] = new ExprEqualsNode(false);

	        equalsNodes[1] = new ExprEqualsNode(false);
	        equalsNodes[1].AddChildNode(new SupportExprNode(1L));
	        equalsNodes[1].AddChildNode(new SupportExprNode(1));

	        equalsNodes[2] = new ExprEqualsNode(true);
	        equalsNodes[2].AddChildNode(new SupportExprNode(1.5D));
	        equalsNodes[2].AddChildNode(new SupportExprNode(1));

	        equalsNodes[3] = new ExprEqualsNode(false);
	        equalsNodes[3].AddChildNode(new SupportExprNode(1D));
	        equalsNodes[3].AddChildNode(new SupportExprNode(1));
	    }

	    [Test]
	    public void TestGetType()
	    {
	        Assert.AreEqual(typeof(Boolean), equalsNodes[0].GetType());
	    }

	    [Test]
	    public void TestValidate()
	    {
	        // Test success
	        equalsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));
	        equalsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));
	        equalsNodes[0].Validate(null, null, null);

	        equalsNodes[1].Validate(null, null, null);
	        equalsNodes[2].Validate(null, null, null);
	        equalsNodes[3].Validate(null, null, null);

	        equalsNodes[0].ChildNodes.Clear();
	        equalsNodes[0].AddChildNode(new SupportExprNode(typeof(String)));

	        // Test too few nodes under this node
	        try
	        {
	            equalsNodes[0].Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (IllegalStateException)
	        {
	            // Expected
	        }

	        // Test mismatch type
	        equalsNodes[0].AddChildNode(new SupportExprNode(typeof(Boolean)));
	        try
	        {
	            equalsNodes[0].Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestEvaluateEquals()
	    {
	        equalsNodes[0] = MakeNode(true, false, false);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(false, false, false);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(true, true, false);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(true, typeof(Boolean), null, typeof(Boolean), false);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(null, typeof(String), "ss", typeof(String), false);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(null, typeof(String), null, typeof(String), false);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        // try a long and int
	        equalsNodes[1].Validate(null, null, null);
	        Assert.IsTrue((Boolean)equalsNodes[1].Evaluate(null, false));

	        // try a double and int
	        equalsNodes[2].Validate(null, null, null);
	        Assert.IsTrue((Boolean)equalsNodes[2].Evaluate(null, false));

	        equalsNodes[3].Validate(null, null, null);
	        Assert.IsTrue((Boolean)equalsNodes[3].Evaluate(null, false));
	    }

	    [Test]
	    public void TestEvaluateNotEquals()
	    {
	        equalsNodes[0] = MakeNode(true, false, true);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(false, false, true);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(true, true, true);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(true, typeof(Boolean), null, typeof(Boolean), true);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(null, typeof(String), "ss", typeof(String), true);
	        Assert.IsTrue((Boolean)equalsNodes[0].Evaluate(null, false));

	        equalsNodes[0] = MakeNode(null, typeof(String), null, typeof(String), true);
	        Assert.IsFalse((Boolean)equalsNodes[0].Evaluate(null, false));
	    }

	    [Test]
	    public void TestToExpressionString()
	    {
	        equalsNodes[0].AddChildNode(new SupportExprNode(true));
	        equalsNodes[0].AddChildNode(new SupportExprNode(false));
	        Assert.AreEqual("true = false", equalsNodes[0].ExpressionString);
	    }

	    private static ExprEqualsNode MakeNode(Object valueLeft, Object valueRight, bool isNot)
	    {
	        ExprEqualsNode equalsNode = new ExprEqualsNode(isNot);
	        equalsNode.AddChildNode(new SupportExprNode(valueLeft));
	        equalsNode.AddChildNode(new SupportExprNode(valueRight));
	        return equalsNode;
	    }

	    private static ExprEqualsNode MakeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight, bool isNot)
	    {
	        ExprEqualsNode equalsNode = new ExprEqualsNode(isNot);
	        equalsNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
	        equalsNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
	        return equalsNode;
	    }

	    [Test]
	    public void TestEqualsNode()
	    {
	        Assert.IsTrue(equalsNodes[0].EqualsNode(equalsNodes[1]));
	        Assert.IsFalse(equalsNodes[0].EqualsNode(equalsNodes[2]));
	    }
	}
} // End of namespace
