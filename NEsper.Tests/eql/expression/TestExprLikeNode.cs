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
	public class TestExprLikeNode
	{
	    private ExprLikeNode likeNodeNormal;
	    private ExprLikeNode likeNodeNot;
	    private ExprLikeNode likeNodeNormalEscaped;

	    [SetUp]
	    public void SetUp()
	    {
	        likeNodeNormal = SupportExprNodeFactory.MakeLikeNode(false, null);
	        likeNodeNot = SupportExprNodeFactory.MakeLikeNode(true, null);
	        likeNodeNormalEscaped = SupportExprNodeFactory.MakeLikeNode(false, "!");
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), likeNodeNormal.ReturnType);
	        Assert.AreEqual(typeof(bool?), likeNodeNot.ReturnType);
	        Assert.AreEqual(typeof(bool?), likeNodeNormalEscaped.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(new ExprLikeNode(true));

	        // singe child node not possible, must be 2 at least
	        likeNodeNormal = new ExprLikeNode(false);
	        likeNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(likeNodeNormal);

	        // test a type mismatch
	        likeNodeNormal = new ExprLikeNode(true);
	        likeNodeNormal.AddChildNode(new SupportExprNode("sx"));
	        likeNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(likeNodeNormal);

	        // test numeric supported
	        likeNodeNormal = new ExprLikeNode(false);
	        likeNodeNormal.AddChildNode(new SupportExprNode(4));
	        likeNodeNormal.AddChildNode(new SupportExprNode("sx"));

	        // test invalid escape char
	        likeNodeNormal = new ExprLikeNode(false);
	        likeNodeNormal.AddChildNode(new SupportExprNode(4));
	        likeNodeNormal.AddChildNode(new SupportExprNode("sx"));
	        likeNodeNormal.AddChildNode(new SupportExprNode(5));
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        // Build :      s0.string like "%abc__"  (with or witout escape)
	        Assert.IsFalse((Boolean) likeNodeNormal.Evaluate(MakeEvent("abcx"), false));
	        Assert.IsTrue((Boolean) likeNodeNormal.Evaluate(MakeEvent("dskfsljkdfabcxx"), false));
	        Assert.IsTrue((Boolean) likeNodeNot.Evaluate(MakeEvent("abcx"), false));
	        Assert.IsFalse((Boolean) likeNodeNot.Evaluate(MakeEvent("dskfsljkdfabcxx"), false));
	    }

	    [Test]
	    public void testEquals()
	    {
	        ExprLikeNode otherLikeNodeNot = SupportExprNodeFactory.MakeLikeNode(true, "@");
	        ExprLikeNode otherLikeNodeNot2 = SupportExprNodeFactory.MakeLikeNode(true, "!");

	        Assert.IsTrue(likeNodeNot.EqualsNode(otherLikeNodeNot2));
	        Assert.IsTrue(otherLikeNodeNot2.EqualsNode(otherLikeNodeNot)); // Escape char itself is an expression
	        Assert.IsFalse(likeNodeNormal.EqualsNode(otherLikeNodeNot));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("s0.string like \"%abc__\"", likeNodeNormal.ExpressionString);
	        Assert.AreEqual("s0.string not like \"%abc__\"", likeNodeNot.ExpressionString);
	        Assert.AreEqual("s0.string like \"%abc__\" escape \"!\"", likeNodeNormalEscaped.ExpressionString);
	    }

	    private EventBean[] MakeEvent(String stringValue)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetString(stringValue);
	        return new EventBean[] {SupportEventBeanFactory.CreateObject(_event)};
	    }

	    private void TryInvalidValidate(ExprLikeNode exprLikeRegexpNode)
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
