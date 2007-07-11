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
	public class TestExprInNode
	{
	    private ExprInNode inNodeNormal;
	    private ExprInNode inNodeNotIn;

	    [SetUp]
	    public void SetUp()
	    {
	        inNodeNormal = SupportExprNodeFactory.MakeInSetNode(false);
	        inNodeNotIn = SupportExprNodeFactory.MakeInSetNode(true);
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(bool?), inNodeNormal.ReturnType);
	        Assert.AreEqual(typeof(bool?), inNodeNotIn.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        inNodeNormal = SupportExprNodeFactory.MakeInSetNode(true);
	        inNodeNormal.Validate(null, null, null);

	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(new ExprInNode(true));

	        // singe child node not possible, must be 2 at least
	        inNodeNormal = new ExprInNode(true);
	        inNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(inNodeNormal);

	        // test a type mismatch
	        inNodeNormal = new ExprInNode(true);
	        inNodeNormal.AddChildNode(new SupportExprNode("sx"));
	        inNodeNormal.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(inNodeNormal);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        Assert.IsFalse((Boolean) inNodeNormal.Evaluate(MakeEvent(0), false));
	        Assert.IsTrue((Boolean) inNodeNormal.Evaluate(MakeEvent(1), false));
	        Assert.IsTrue((Boolean) inNodeNormal.Evaluate(MakeEvent(2), false));
	        Assert.IsFalse((Boolean) inNodeNormal.Evaluate(MakeEvent(3), false));

	        Assert.IsTrue((Boolean) inNodeNotIn.Evaluate(MakeEvent(0), false));
	        Assert.IsFalse((Boolean) inNodeNotIn.Evaluate(MakeEvent(1), false));
	        Assert.IsFalse((Boolean) inNodeNotIn.Evaluate(MakeEvent(2), false));
	        Assert.IsTrue((Boolean) inNodeNotIn.Evaluate(MakeEvent(3), false));
	    }

	    [Test]
	    public void testEquals()
	    {
	        ExprInNode otherInNodeNormal = SupportExprNodeFactory.MakeInSetNode(false);
	        ExprInNode otherInNodeNotIn = SupportExprNodeFactory.MakeInSetNode(true);

	        Assert.IsTrue(inNodeNormal.EqualsNode(otherInNodeNormal));
	        Assert.IsTrue(inNodeNotIn.EqualsNode(otherInNodeNotIn));

	        Assert.IsFalse(inNodeNormal.EqualsNode(otherInNodeNotIn));
	        Assert.IsFalse(inNodeNotIn.EqualsNode(otherInNodeNormal));
	        Assert.IsFalse(inNodeNotIn.EqualsNode(SupportExprNodeFactory.MakeCaseSyntax1Node()));
	        Assert.IsFalse(inNodeNormal.EqualsNode(SupportExprNodeFactory.MakeCaseSyntax1Node()));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("s0.intPrimitive in (1,2)", inNodeNormal.ExpressionString);
	        Assert.AreEqual("s0.intPrimitive not in (1,2)", inNodeNotIn.ExpressionString);
	    }

	    private EventBean[] MakeEvent(int intPrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        return new EventBean[] {SupportEventBeanFactory.CreateObject(_event)};
	    }

	    private void TryInvalidValidate(ExprInNode exprInNode)
	    {
	        try {
	            exprInNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
