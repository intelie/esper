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
	public class TestFilterParamIndexRange
	{
	    private SupportEventEvaluator testEvaluator;
	    private SupportBean testBean;
	    private EventBean testEventBean;
	    private EventType testEventType;
	    private IList<FilterHandle> matchesList;
	    private DoubleRange testRange;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvaluator = new SupportEventEvaluator();
	        testBean = new SupportBean();
	        testEventBean = SupportEventBeanFactory.CreateObject(testBean);
	        testEventType = testEventBean.EventType;
            matchesList = new List<FilterHandle>();

	        testRange = new DoubleRange(10d, 20d);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        try
	        {
	            new FilterParamIndexRange("doublePrimitive", FilterOperator.EQUAL, testEventType);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }

	        try
	        {
	            new FilterParamIndexCompare("string", FilterOperator.RANGE_CLOSED, testEventType);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void testLongBothEndpointsIncluded()
	    {
	        FilterParamIndexRange index = this.GetLongDataset(FilterOperator.RANGE_CLOSED);
	        VerifyLongPrimitive(index, -1, 0);
	        VerifyLongPrimitive(index, 0, 2);
	        VerifyLongPrimitive(index, 1, 5);
	        VerifyLongPrimitive(index, 2, 5);
	        VerifyLongPrimitive(index, 3, 7);
	        VerifyLongPrimitive(index, 4, 6);
	        VerifyLongPrimitive(index, 5, 6);
	        VerifyLongPrimitive(index, 6, 6);
	        VerifyLongPrimitive(index, 7, 6);
	        VerifyLongPrimitive(index, 8, 6);
	        VerifyLongPrimitive(index, 9, 5);
	        VerifyLongPrimitive(index, 10, 3);
	        VerifyLongPrimitive(index, 11, 1);

	        index[testRange] = testEvaluator;
	        Assert.AreEqual(testEvaluator, index[testRange]);
	        Assert.IsTrue(index.ReadWriteLock != null);
	        Assert.IsTrue(index.Remove(testRange));
	        Assert.IsFalse(index.Remove(testRange));
	        Assert.AreEqual(null, index[testRange]);

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
	    public void testLongLowEndpointIncluded()
	    {
	        FilterParamIndexRange index = this.GetLongDataset(FilterOperator.RANGE_HALF_OPEN);
	        VerifyLongPrimitive(index, -1, 0);
	        VerifyLongPrimitive(index, 0, 2);
	        VerifyLongPrimitive(index, 1, 5);
	        VerifyLongPrimitive(index, 2, 5);
	        VerifyLongPrimitive(index, 3, 6);
	        VerifyLongPrimitive(index, 4, 6);
	        VerifyLongPrimitive(index, 5, 3);
	        VerifyLongPrimitive(index, 6, 5);
	        VerifyLongPrimitive(index, 7, 4);
	        VerifyLongPrimitive(index, 8, 5);
	        VerifyLongPrimitive(index, 9, 3);
	        VerifyLongPrimitive(index, 10, 1);
	        VerifyLongPrimitive(index, 11, 1);
	    }

	    [Test]
	    public void testLongHighEndpointIncluded()
	    {
	        FilterParamIndexRange index = this.GetLongDataset(FilterOperator.RANGE_HALF_CLOSED);
	        VerifyLongPrimitive(index, -1, 0);
	        VerifyLongPrimitive(index, 0, 0);
	        VerifyLongPrimitive(index, 1, 2);
	        VerifyLongPrimitive(index, 2, 5);
	        VerifyLongPrimitive(index, 3, 5);
	        VerifyLongPrimitive(index, 4, 6);
	        VerifyLongPrimitive(index, 5, 6);
	        VerifyLongPrimitive(index, 6, 3);
	        VerifyLongPrimitive(index, 7, 5);
	        VerifyLongPrimitive(index, 8, 4);
	        VerifyLongPrimitive(index, 9, 5);
	        VerifyLongPrimitive(index, 10, 3);
	        VerifyLongPrimitive(index, 11, 1);
	    }

	    [Test]
	    public void testLongNeitherEndpointIncluded()
	    {
	        FilterParamIndexRange index = this.GetLongDataset(FilterOperator.RANGE_OPEN);
	        VerifyLongPrimitive(index, -1, 0);
	        VerifyLongPrimitive(index, 0, 0);
	        VerifyLongPrimitive(index, 1, 2);
	        VerifyLongPrimitive(index, 2, 5);
	        VerifyLongPrimitive(index, 3, 4);
	        VerifyLongPrimitive(index, 4, 6);
	        VerifyLongPrimitive(index, 5, 3);
	        VerifyLongPrimitive(index, 6, 2);
	        VerifyLongPrimitive(index, 7, 3);
	        VerifyLongPrimitive(index, 8, 3);
	        VerifyLongPrimitive(index, 9, 3);
	        VerifyLongPrimitive(index, 10, 1);
	        VerifyLongPrimitive(index, 11, 1);
	    }

	    [Test]
	    public void testDoubleBothEndpointsIncluded()
	    {
	        FilterParamIndexRange index = this.GetDoubleDataset(FilterOperator.RANGE_CLOSED);
	        VerifyDoublePrimitive(index, 1.49, 0);
	        VerifyDoublePrimitive(index, 1.5, 1);
	        VerifyDoublePrimitive(index, 2.5, 1);
	        VerifyDoublePrimitive(index, 2.51, 0);
	        VerifyDoublePrimitive(index, 3.5, 2);
	        VerifyDoublePrimitive(index, 4.4, 2);
	        VerifyDoublePrimitive(index, 4.5, 2);
	        VerifyDoublePrimitive(index, 4.5001, 1);
	        VerifyDoublePrimitive(index, 5.1, 1);
	        VerifyDoublePrimitive(index, 5.8, 2);
	        VerifyDoublePrimitive(index, 6.7, 2);
	        VerifyDoublePrimitive(index, 6.8, 1);
	        VerifyDoublePrimitive(index, 9.5, 1);
	        VerifyDoublePrimitive(index, 10.1, 0);
	    }

	    [Test]
	    public void testDoubleFixedRangeSize()
	    {
	        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_OPEN, testEventType);

	        for (int i = 0; i < 10000; i++)
	        {
	            DoubleRange range = new DoubleRange((double) i, (double) (i + 1));
	            index[range] = testEvaluator;
	        }

	        VerifyDoublePrimitive(index, 5000, 0);
	        VerifyDoublePrimitive(index, 5000.5, 1);
	        VerifyDoublePrimitive(index, 5001, 0);
	    }

	    [Test]
	    public void testDoubleVariableRangeSize()
	    {
	        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType);

	        for (int i = 0; i < 100; i++)
	        {
	            DoubleRange range = new DoubleRange((double) i, (double) (2*i));
	            index[range] = testEvaluator;
	        }

	        // 1 to 2
	        // 2 to 4
	        // 3 to 6
	        // and so on

	        VerifyDoublePrimitive(index, 1, 1);
	        VerifyDoublePrimitive(index, 2, 2);
	        VerifyDoublePrimitive(index, 2.001, 1);
	        VerifyDoublePrimitive(index, 3, 2);
	        VerifyDoublePrimitive(index, 4, 3);
	        VerifyDoublePrimitive(index, 4.5, 2);
	        VerifyDoublePrimitive(index, 50, 26);
	    }

	    private FilterParamIndexRange GetLongDataset(FilterOperator operatorType)
	    {
	        FilterParamIndexRange index = new FilterParamIndexRange("longPrimitive", operatorType, testEventType);

	        AddToIndex(index,0,5);
	        AddToIndex(index,0,6);
	        AddToIndex(index,1,3);
	        AddToIndex(index,1,5);
	        AddToIndex(index,1,7);
	        AddToIndex(index,3,5);
	        AddToIndex(index,3,7);
	        AddToIndex(index,6,9);
	        AddToIndex(index,6,10);
	        AddToIndex(index,6,Int32.MaxValue - 1);
	        AddToIndex(index,7,8);
	        AddToIndex(index,8,9);
	        AddToIndex(index,8,10);

	        return index;
	    }

	    private FilterParamIndexRange GetDoubleDataset(FilterOperator operatorType)
	    {
	        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", operatorType, testEventType);

	        AddToIndex(index, 1.5, 2.5);
	        AddToIndex(index, 3.5, 4.5 );
	        AddToIndex(index, 3.5, 9.5);
	        AddToIndex(index, 5.6, 6.7);

	        return index;
	    }

	    private void VerifyDoublePrimitive(FilterParamIndexBase index, double testValue, int numExpected)
	    {
	        testBean.SetDoublePrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void VerifyLongPrimitive(FilterParamIndexBase index, long testValue, int numExpected)
	    {
	        testBean.SetLongPrimitive(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        Assert.AreEqual(numExpected, testEvaluator.GetAndResetCountInvoked());
	    }

	    private void AddToIndex(FilterParamIndexRange index, double min, double max)
	    {
	        DoubleRange r = new DoubleRange(min,max);
	        index[r] = testEvaluator;
	    }
	}
} // End of namespace
