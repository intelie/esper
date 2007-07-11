// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view.internals;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprPriorNode
	{
	    private ExprPriorNode priorNode;

	    [SetUp]
	    public void SetUp()
	    {
	        priorNode = SupportExprNodeFactory.MakePriorNode();
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(double?), priorNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        priorNode = new ExprPriorNode();

	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(priorNode);

	        // singe child node not possible, must be 2 at least
	        priorNode.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(priorNode);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        PriorEventBufferUnbound buffer = new PriorEventBufferUnbound(10);
	        priorNode.ViewResource = buffer;
	        EventBean[] events = new EventBean[] {MakeEvent(1d), MakeEvent(10d)};
	        buffer.Update(events, null);

	        Assert.AreEqual(1d, priorNode.Evaluate(events, true));
	    }

	    [Test]
	    public void testEquals()
	    {
	        ExprPriorNode node1 = new ExprPriorNode();
	        Assert.IsTrue(node1.EqualsNode(priorNode));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("prior(1,s1.doublePrimitive)", priorNode.ExpressionString);
	    }

	    private EventBean MakeEvent(double doublePrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetDoublePrimitive(doublePrimitive);
	        return SupportEventBeanFactory.CreateObject(_event);
	    }

	    private void TryInvalidValidate(ExprPriorNode exprPriorNode)
	    {
	        try {
	            exprPriorNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
