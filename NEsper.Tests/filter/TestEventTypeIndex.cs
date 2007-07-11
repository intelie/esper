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

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestEventTypeIndex
	{
	    private EventTypeIndex testIndex;

	    private EventBean testEventBean;
	    private EventType testEventType;

	    private FilterHandleSetNode handleSetNode;
	    private FilterHandle filterCallback;

	    [SetUp]
	    public void SetUp()
	    {
	        SupportBean testBean = new SupportBean();
	        testEventBean = SupportEventBeanFactory.CreateObject(testBean);
	        testEventType = testEventBean.EventType;

	        handleSetNode = new FilterHandleSetNode();
	        filterCallback = new SupportFilterHandle();
	        handleSetNode.Add(filterCallback);

	        testIndex = new EventTypeIndex();
	        testIndex.Add(testEventType, handleSetNode);
	    }

	    [Test]
	    public void testMatch()
	    {
	        IList<FilterHandle> matchesList = new List<FilterHandle>();

	        // Invoke match
	        testIndex.MatchEvent(testEventBean, matchesList);

	        Assert.AreEqual(1, matchesList.Count);
	        Assert.AreEqual(filterCallback, matchesList[0]);
	    }

	    [Test]
	    public void testInvalidSecondAdd()
	    {
	        try
	        {
	            testIndex.Add(testEventType, handleSetNode);
	            Assert.IsTrue(false);
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testGet()
	    {
	        Assert.AreEqual(handleSetNode, testIndex[testEventType]);
	    }

	    [Test]
	    public void testSuperclassMatch()
	    {
	        testEventBean = SupportEventBeanFactory.CreateObject(new ISupportAImplSuperGImplPlus());
	        testEventType = SupportEventTypeFactory.CreateBeanType(typeof(ISupportA));

	        testIndex = new EventTypeIndex();
	        testIndex.Add(testEventType, handleSetNode);

	        IList<FilterHandle> matchesList = new List<FilterHandle>();
	        testIndex.MatchEvent(testEventBean, matchesList);

	        Assert.AreEqual(1, matchesList.Count);
	        Assert.AreEqual(filterCallback, matchesList[0]);
	    }

	    [Test]
	    public void testInterfaceMatch()
	    {
	        testEventBean = SupportEventBeanFactory.CreateObject(new ISupportABCImpl("a", "b", "ab", "c"));
	        testEventType = SupportEventTypeFactory.CreateBeanType(typeof(ISupportBaseAB));

	        testIndex = new EventTypeIndex();
	        testIndex.Add(testEventType, handleSetNode);

	        IList<FilterHandle> matchesList = new List<FilterHandle>();
	        testIndex.MatchEvent(testEventBean, matchesList);

	        Assert.AreEqual(1, matchesList.Count);
	        Assert.AreEqual(filterCallback, matchesList[0]);
	    }
	}
} // End of namespace
