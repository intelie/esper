///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterCallbackSetNode
	{
	    private SupportEventEvaluator testEvaluator;
	    private FilterHandleSetNode testNode;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvaluator = new SupportEventEvaluator();
	        testNode = new FilterHandleSetNode();
	    }

	    [Test]
	    public void testNodeGetSet()
	    {
	        FilterHandle exprOne = new SupportFilterHandle();

	        // Check pre-conditions
	        Assert.IsTrue(testNode.NodeRWLock != null);
	        Assert.IsFalse(testNode.Contains(exprOne));
	        Assert.AreEqual(0, testNode.FilterCallbackCount);
	        Assert.AreEqual(0, testNode.Indizes.Count);
	        Assert.IsTrue(testNode.IsEmpty);

	        testNode.Add(exprOne);

	        // Check after add
	        Assert.IsTrue(testNode.Contains(exprOne));
	        Assert.AreEqual(1, testNode.FilterCallbackCount);
	        Assert.IsFalse(testNode.IsEmpty);

	        // Add an indexOne
	        FilterParamIndexBase indexOne = new SupportFilterParamIndex();
	        testNode.Add(indexOne);

	        // Check after add
	        Assert.AreEqual(1, testNode.Indizes.Count);
	        Assert.AreEqual(indexOne, testNode.Indizes[0]);

	        // Check removes
	        Assert.IsTrue(testNode.Remove(exprOne));
	        Assert.IsFalse(testNode.IsEmpty);
	        Assert.IsFalse(testNode.Remove(exprOne));
	        Assert.IsTrue(testNode.Remove(indexOne));
	        Assert.IsFalse(testNode.Remove(indexOne));
	        Assert.IsTrue(testNode.IsEmpty);
	    }

	    [Test]
	    public void testNodeMatching()
	    {
	        SupportBeanSimple eventObject = new SupportBeanSimple("DepositEvent_1", 1);
	        EventBean _eventBean = SupportEventBeanFactory.CreateObject(eventObject);

	        FilterHandle expr = new SupportFilterHandle();
	        testNode.Add(expr);

	        // Check matching without an index node
	        IList<FilterHandle> matches = new List<FilterHandle>();
	        testNode.MatchEvent(_eventBean, matches);
	        Assert.AreEqual(1, matches.Count);
	        Assert.AreEqual(expr, matches[0]);
	        matches.Clear();

	        // Create, add and populate an index node
	        FilterParamIndexBase index = new FilterParamIndexEquals("myString", _eventBean.EventType);
	        testNode.Add(index);
	        index["DepositEvent_1"] = testEvaluator;

	        // Verify matcher instance stored in index is called
	        testNode.MatchEvent(_eventBean, matches);

	        Assert.IsTrue(testEvaluator.GetAndResetCountInvoked() == 1);
	        Assert.IsTrue(testEvaluator.GetLastEvent() == _eventBean);
	        Assert.AreEqual(1, matches.Count);
	        Assert.AreEqual(expr, matches[0]);
	    }
	}
} // End of namespace
