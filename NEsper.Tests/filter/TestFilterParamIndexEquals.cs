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
	public class TestFilterParamIndexEquals
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
	    public void testLong()
	    {
	        FilterParamIndexEquals index = new FilterParamIndexEquals("shortBoxed", testEventType);

	        index[(short) 1] = testEvaluator;
	        index[(short) 20] = testEvaluator;

	        VerifyShortBoxed(index, (short) 10, 0);
	        VerifyShortBoxed(index, (short) 1, 1);
	        VerifyShortBoxed(index, (short) 20, 1);
	        VerifyShortBoxed(index, null, 0);

	        Assert.AreEqual(testEvaluator, index[(short) 1]);
	        Assert.IsTrue(index.ReadWriteLock != null);
	        Assert.IsTrue(index.Remove((short) 1));
	        Assert.IsFalse(index.Remove((short) 1));
            Assert.AreEqual(null, index[(short)1]);

	        try
	        {
	            index["a"] = testEvaluator;
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testBoolean()
	    {
	        FilterParamIndexEquals index = new FilterParamIndexEquals("boolPrimitive", testEventType);

	        index[false] = testEvaluator;

	        VerifyBooleanPrimitive(index, false, 1);
	        VerifyBooleanPrimitive(index, true, 0);
	    }

	    [Test]
	    public void testString()
	    {
	        FilterParamIndexEquals index = new FilterParamIndexEquals("string", testEventType);

	        index["hello"] = testEvaluator;
	        index["test"] = testEvaluator;

	        VerifyString(index, null, 0);
	        VerifyString(index, "dudu", 0);
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

	    [Test]
	    public void testFloatPrimitive()
	    {
	        FilterParamIndexEquals index = new FilterParamIndexEquals("floatPrimitive", testEventType);

	        index[1.5f] = testEvaluator;

	        VerifyFloatPrimitive(index, 1.5f, 1);
	        VerifyFloatPrimitive(index, 2.2f, 0);
	        VerifyFloatPrimitive(index, 0, 0);

	        try
	        {
	            index[20] = testEvaluator;
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }

	    private void VerifyShortBoxed(FilterParamIndexBase index, short? testValue, int numExpected)
	    {
	        testBean.SetShortBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
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

	    private void VerifyFloatPrimitive(FilterParamIndexBase index, float testValue, int numExpected)
	    {
	        testBean.SetFloatPrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }
	}
} // End of namespace
