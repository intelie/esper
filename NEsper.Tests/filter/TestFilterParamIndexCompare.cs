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
	public class TestFilterParamIndexCompare
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
	    public void TestInvalid()
	    {
	        try
	        {
	            new FilterParamIndexCompare("doublePrimitive", FilterOperator.EQUAL, testEventType);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }

	        try
	        {
	            new FilterParamIndexCompare("doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void TestMatchDoubleAndGreater()
	    {
	        FilterParamIndexCompare index = new FilterParamIndexCompare("doublePrimitive", FilterOperator.GREATER, testEventType);

	        index[1.5] = testEvaluator;
	        index[2.1] = testEvaluator;
	        index[2.2] = testEvaluator;

	        VerifyDoublePrimitive(index, 1.5, 0);
	        VerifyDoublePrimitive(index, 1.7, 1);
	        VerifyDoublePrimitive(index, 2.2, 2);
	        VerifyDoublePrimitive(index, 2.1999999, 2);
	        VerifyDoublePrimitive(index, -1, 0);
	        VerifyDoublePrimitive(index, 99, 3);

	        Assert.AreEqual(testEvaluator, index[1.5d]);
	        Assert.IsTrue(index.ReadWriteLock != null);
	        Assert.IsTrue(index.Remove(1.5d));
	        Assert.IsFalse(index.Remove(1.5d));
	        Assert.AreEqual(null, index[1.5d]);

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
	    public void TestMatchLongAndGreaterEquals()
	    {
	        FilterParamIndexCompare index = new FilterParamIndexCompare("longBoxed", FilterOperator.GREATER_OR_EQUAL, testEventType);

	        index[1L] = testEvaluator;
	        index[2L] = testEvaluator;
	        index[4L] = testEvaluator;

	        // Should not match with null
	        VerifyLongBoxed(index, null, 0);

	        VerifyLongBoxed(index, 0L, 0);
	        VerifyLongBoxed(index, 1L, 1);
	        VerifyLongBoxed(index, 2L, 2);
	        VerifyLongBoxed(index, 3L, 2);
	        VerifyLongBoxed(index, 4L, 3);
	        VerifyLongBoxed(index, 10L, 3);

	        // Put a long primitive in - should work
	        index[9L] = testEvaluator;
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
	    public void TestMatchLongAndLessThan()
	    {
	        FilterParamIndexCompare index = new FilterParamIndexCompare("longPrimitive", FilterOperator.LESS, testEventType);

	        index[1L] = testEvaluator;
	        index[10L] = testEvaluator;
	        index[100L] = testEvaluator;

	        VerifyLongPrimitive(index, 100, 0);
	        VerifyLongPrimitive(index, 101, 0);
	        VerifyLongPrimitive(index, 99, 1);
	        VerifyLongPrimitive(index, 11, 1);
	        VerifyLongPrimitive(index, 10, 1);
	        VerifyLongPrimitive(index, 9, 2);
	        VerifyLongPrimitive(index, 2, 2);
	        VerifyLongPrimitive(index, 1, 2);
	        VerifyLongPrimitive(index, 0, 3);
	    }

	    [Test]
	    public void TestMatchDoubleAndLessOrEqualThan()
	    {
	        FilterParamIndexCompare index = new FilterParamIndexCompare("doubleBoxed", FilterOperator.LESS_OR_EQUAL, testEventType);

	        index[7.4D] = testEvaluator;
	        index[7.5D] = testEvaluator;
	        index[7.6D] = testEvaluator;

	        VerifyDoubleBoxed(index, 7.39, 3);
	        VerifyDoubleBoxed(index, 7.4, 3);
	        VerifyDoubleBoxed(index, 7.41, 2);
	        VerifyDoubleBoxed(index, 7.5, 2);
	        VerifyDoubleBoxed(index, 7.51, 1);
	        VerifyDoubleBoxed(index, 7.6, 1);
	        VerifyDoubleBoxed(index, 7.61, 0);
	    }

	    private void VerifyDoublePrimitive(FilterParamIndexBase index, double testValue, int numExpected)
	    {
	        testBean.SetDoublePrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void VerifyDoubleBoxed(FilterParamIndexBase index, double? testValue, int numExpected)
	    {
	        testBean.SetDoubleBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void VerifyLongBoxed(FilterParamIndexBase index, long? testValue, int numExpected)
	    {
	        testBean.SetLongBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void VerifyLongPrimitive(FilterParamIndexBase index, long testValue, int numExpected)
	    {
	        testBean.SetLongPrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }
	}
} // End of namespace
