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
using net.esper.type;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprRelationalOpNode
	{
	    private ExprRelationalOpNode opNode;

	    [SetUp]
	    public void SetUp()
	    {
	        opNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
	    }

	    [Test]
	    public void testGetType()
	    {
	        opNode.AddChildNode(new SupportExprNode(typeof(long?)));
	        opNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        Assert.AreEqual(typeof(bool?), opNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // Test success
	        opNode.AddChildNode(new SupportExprNode(typeof(String)));
	        opNode.AddChildNode(new SupportExprNode(typeof(String)));
	        opNode.Validate(null, null, null);

	        opNode.ChildNodes.Clear();
	        opNode.AddChildNode(new SupportExprNode(typeof(String)));

	        // Test too few nodes under this node
	        try
	        {
	            opNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected
	        }

	        // Test mismatch type
	        opNode.AddChildNode(new SupportExprNode(typeof(int?)));
	        try
	        {
	            opNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // Test type cannot be compared
	        opNode.ChildNodes.Clear();
	        opNode.AddChildNode(new SupportExprNode(typeof(bool?)));
	        opNode.AddChildNode(new SupportExprNode(typeof(bool?)));

	        try
	        {
	            opNode.Validate(null, null, null);
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
	        SupportExprNode childOne = new SupportExprNode("d");
	        SupportExprNode childTwo = new SupportExprNode("c");
	        opNode.AddChildNode(childOne);
	        opNode.AddChildNode(childTwo);
	        opNode.Validate(null, null, null);       // Type initialization

	        Assert.AreEqual(true, opNode.Evaluate(null, false));

	        childOne.SetValue("c");
	        Assert.AreEqual(true, opNode.Evaluate(null, false));

	        childOne.SetValue("b");
	        Assert.AreEqual(false, opNode.Evaluate(null, false));

	        opNode = MakeNode(null, typeof(int?), 2, typeof(int?));
	        Assert.AreEqual(false, opNode.Evaluate(null, false));
	        opNode = MakeNode(1, typeof(int?), null, typeof(int?));
	        Assert.AreEqual(false, opNode.Evaluate(null, false));
	        opNode = MakeNode(null, typeof(int?), null, typeof(int?));
	        Assert.AreEqual(false, opNode.Evaluate(null, false));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        opNode.AddChildNode(new SupportExprNode(10));
	        opNode.AddChildNode(new SupportExprNode(5));
	        Assert.AreEqual("10>=5", opNode.ExpressionString);
	    }

	    private ExprRelationalOpNode MakeNode(Object valueLeft, Type typeLeft, Object valueRight, Type typeRight)
	    {
	        ExprRelationalOpNode relOpNode = new ExprRelationalOpNode(RelationalOpEnum.GE);
	        relOpNode.AddChildNode(new SupportExprNode(valueLeft, typeLeft));
	        relOpNode.AddChildNode(new SupportExprNode(valueRight, typeRight));
	        return relOpNode;
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(opNode.EqualsNode(opNode));
	        Assert.IsFalse(opNode.EqualsNode(new ExprRelationalOpNode(RelationalOpEnum.LE)));
	        Assert.IsFalse(opNode.EqualsNode(new ExprOrNode()));
	    }
	}
} // End of namespace
