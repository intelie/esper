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
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), orNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
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
	    public void testEvaluate()
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
	    public void testToExpressionString()
	    {
	        orNode.AddChildNode(new SupportExprNode(true));
	        orNode.AddChildNode(new SupportExprNode(false));
	        Assert.AreEqual("(True OR False)", orNode.ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        Assert.IsTrue(orNode.EqualsNode(orNode));
	        Assert.IsFalse(orNode.EqualsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
	        Assert.IsTrue(orNode.EqualsNode(new ExprOrNode()));
	    }
	}
} // End of namespace
