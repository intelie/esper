// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterParamIndexIn
	{
	    private SupportEventEvaluator testEvaluator;
	    private SupportBean testBean;
	    private EventBean testEventBean;
	    private EventType testEventType;
	    private IList<FilterHandle> matchesList;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvaluator = new SupportEventEvaluator();
	        testBean = new SupportBean();
	        testEventBean = SupportEventBeanFactory.CreateObject(testBean);
	        testEventType = testEventBean.EventType;
	        matchesList = new List<FilterHandle>();
	    }

	    [Test]
	    public void testIndex()
	    {
	        FilterParamIndexIn index = new FilterParamIndexIn("longBoxed", testEventType);
	        Assert.AreEqual(FilterOperator.IN_LIST_OF_VALUES, index.FilterOperator);

	        MultiKeyUntyped inList = new MultiKeyUntyped(new Object[] {2L, 5L});
	        index[inList] = testEvaluator;
	        inList = new MultiKeyUntyped(new Object[] {10L, 5L});
	        index[inList] = testEvaluator;

	        Verify(index, 1L, 0);
	        Verify(index, 2L, 1);
	        Verify(index, 5L, 2);
	        Verify(index, 10L, 1);
	        Verify(index, 999L, 0);
	        Verify(index, null, 0);

            Assert.AreEqual(testEvaluator, index[inList]);
	        Assert.IsTrue(index.ReadWriteLock != null);
	        Assert.IsTrue(index.Remove(inList));
	        Assert.IsFalse(index.Remove(inList));
	        Assert.AreEqual(null, index[inList]);

	        try
	        {
	            index["a"] = testEvaluator;
	            Assert.IsTrue(false);
	        }
	        catch (Exception ex)
	        {
	            // Expected
	        }
	    }

	    private void Verify(FilterParamIndexBase index, long? testValue, int numExpected)
	    {
	        testBean.SetLongBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }
	}
} // End of namespace
