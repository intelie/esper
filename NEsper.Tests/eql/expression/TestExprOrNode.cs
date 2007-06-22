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
	public class TestExprOrNode
	{
	    private ExprOrNode orNode;

	    [SetUp]
	    public void SetUp()
	    {
	        orNode = new ExprOrNode();
	    }

	    [Test]
	    public void TestGetType()
	    {
	        Assert.AreEqual(typeof(Boolean), orNode.GetType());
	    }

	    [Test]
	    public void TestValidate()
	    {
	        // test success
	        orNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        orNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        orNode.Validate(null, null, null);

	        // test failure, type mismatch
	        orNode.AddChildNode(new SupportExprNode(typeof(String)));
	        try
	        {
	            orNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }

	        // test failed - with just one child
	        orNode = new ExprOrNode();
	        orNode.AddChildNode(new SupportExprNode(typeof(Boolean)));
	        try
	        {
	            orNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestEvaluate()
	    {
	        orNode.AddChildNode(new SupportBoolExprNode(true));
	        orNode.AddChildNode(new SupportBoolExprNode(false));
	        Assert.IsTrue( (Boolean) orNode.Evaluate(null, false));

	        orNode = new ExprOrNode();
	        orNode.AddChildNode(new SupportBoolExprNode(false));
	        orNode.AddChildNode(new SupportBoolExprNode(false));
	        Assert.IsFalse( (Boolean) orNode.Evaluate(null, false));
	    }

	    [Test]
	    public void TestToExpressionString()
	    {
	        orNode.AddChildNode(new SupportExprNode(true));
	        orNode.AddChildNode(new SupportExprNode(false));
	        Assert.AreEqual("(true OR false)", orNode.ExpressionString);
	    }

	    [Test]
	    public void TestEqualsNode()
	    {
	        Assert.IsTrue(orNode.EqualsNode(orNode));
	        Assert.IsFalse(orNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
	        Assert.IsTrue(orNode.EqualsNode(new ExprOrNode()));
	    }
	}
} // End of namespace
