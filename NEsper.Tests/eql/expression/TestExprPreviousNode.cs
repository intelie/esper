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
using net.esper.view.window;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprPreviousNode {
	    private ExprPreviousNode prevNode;

	    [SetUp]
	    public void SetUp()
	    {
	        prevNode = SupportExprNodeFactory.MakePreviousNode();
	    }

	    [Test]
	    public void testGetType()
	    {
	        Assert.AreEqual(typeof(double?), prevNode.ReturnType);
	    }

	    [Test]
	    public void testValidate()
	    {
	        prevNode = new ExprPreviousNode();

	        // No subnodes: Exception is thrown.
	        TryInvalidValidate(prevNode);

	        // singe child node not possible, must be 2 at least
	        prevNode.AddChildNode(new SupportExprNode(4));
	        TryInvalidValidate(prevNode);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        RandomAccessByIndexGetter getter = new RandomAccessByIndexGetter();
	        IStreamRandomAccess buffer = new IStreamRandomAccess(getter);
	        getter.Updated(buffer);

	        prevNode.ViewResource = getter;
	        EventBean[] events = MakeEvent(0, 5d);
	        buffer.Update(events, null);

	        Assert.AreEqual(5d, prevNode.Evaluate(events, true));
	    }

	    [Test]
	    public void testEquals()
	    {
	        ExprPreviousNode node1 = new ExprPreviousNode();
	        Assert.IsTrue(node1.EqualsNode(prevNode));
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        Assert.AreEqual("prev(s1.intPrimitive,s1.doublePrimitive)", prevNode.ExpressionString);
	    }

	    private EventBean[] MakeEvent(int intPrimitive, double doublePrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        _event.SetDoublePrimitive(doublePrimitive);
	        return new EventBean[] {null, SupportEventBeanFactory.CreateObject(_event)};
	    }

	    private void TryInvalidValidate(ExprPreviousNode exprPrevNode)
	    {
	        try {
	            exprPrevNode.Validate(null, null, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
