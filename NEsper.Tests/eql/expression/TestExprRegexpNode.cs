///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprRegexpNode
	{
	    private ExprRegexpNode regexpNodeNormal;
	    private ExprRegexpNode regexpNodeNot;

	    [SetUp]
	    public void SetUp()
	    {
	        regexpNodeNormal = SupportExprNodeFactory.MakeRegexpNode(false);
	        regexpNodeNot = SupportExprNodeFactory.MakeRegexpNode(true);
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), regexpNodeNormal.ReturnType);
	        Assert.AreEqual(typeof(bool?), regexpNodeNot.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(new ExprRegexpNode(true));

	        // singe child node not possible, must be 2 at least
	        regexpNodeNormal = new ExprRegexpNode(false);
	        regexpNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(regexpNodeNormal);

	        // test a type mismatch
	        regexpNodeNormal = new ExprRegexpNode(true);
	        regexpNodeNormal.AddChildNode(new SupportExprNode("sx"));
	        regexpNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(regexpNodeNormal);

	        // test numeric supported
	        regexpNodeNormal = new ExprRegexpNode(false);
	        regexpNodeNormal.AddChildNode(new SupportExprNode(4));
	        regexpNodeNormal.AddChildNode(new SupportExprNode("sx"));
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        Assert.IsFalse((Boolean) regexpNodeNormal.Evaluate(MakeEvent("bcd"), false));
	        Assert.IsTrue((Boolean) regexpNodeNormal.Evaluate(MakeEvent("ab"), false));
	        Assert.IsTrue((Boolean) regexpNodeNot.Evaluate(MakeEvent("bcd"), false));
	        Assert.IsFalse((Boolean) regexpNodeNot.Evaluate(MakeEvent("ab"), false));
	    }

	    [Test]
	    public void testEquals()
	    {
	        ExprRegexpNode otherRegexpNodeNot = SupportExprNodeFactory.MakeRegexpNode(true);

	        Assert.IsTrue(regexpNodeNot.EqualsNode(otherRegexpNodeNot));
	        Assert.IsFalse(regexpNodeNormal.EqualsNode(otherRegexpNodeNot));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("s0.string regexp \"[a-z][a-z]\"", regexpNodeNormal.ExpressionString);
	        Assert.AreEqual("s0.string not regexp \"[a-z][a-z]\"", regexpNodeNot.ExpressionString);
	    }

	    private EventBean[] MakeEvent(String stringValue)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetString(stringValue);
            return new EventBean[] { SupportEventBeanFactory.CreateObject(_event) };
	    }

	    private void TryInvalidValidate(ExprRegexpNode exprLikeRegexpNode)
	    {
	        try {
	            exprLikeRegexpNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
