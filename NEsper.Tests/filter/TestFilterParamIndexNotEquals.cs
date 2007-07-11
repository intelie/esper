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
	public class TestFilterParamIndexNotEquals
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
	    public void testBoolean()
	    {
	        FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("boolPrimitive", testEventType);
	        Assert.AreEqual(FilterOperator.NOT_EQUAL, index.FilterOperator);
	        Assert.AreEqual("boolPrimitive", index.PropertyName);

	        index[false] = testEvaluator;

	        VerifyBooleanPrimitive(index, true, 1);
	        VerifyBooleanPrimitive(index, false, 0);
	    }

	    [Test]
	    public void testString()
	    {
	        FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("string", testEventType);

	        index["hello"] = testEvaluator;
	        index["test"] = testEvaluator;

	        VerifyString(index, null, 0);
	        VerifyString(index, "dudu", 2);
	        VerifyString(index, "hello", 1);
	        VerifyString(index, "test", 1);

	        try
	        {
	            index[10] = testEvaluator;
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }

	    private void VerifyBooleanPrimitive(FilterParamIndexBase index, bool testValue, int numExpected)
	    {
	        testBean.SetBoolPrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void VerifyString(FilterParamIndexBase index, String testValue, int numExpected)
	    {
	        testBean.SetString(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }
	}
} // End of namespace
