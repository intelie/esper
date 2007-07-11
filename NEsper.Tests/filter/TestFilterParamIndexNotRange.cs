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

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterParamIndexNotRange
	{
		private SupportEventEvaluator[] testEvaluators;
	    private SupportBean testBean;
	    private EventBean testEventBean;
	    private EventType testEventType;
	    private IList<FilterHandle> matchesList;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvaluators = new SupportEventEvaluator[4];
	        for (int i = 0; i < testEvaluators.Length; i++)
	        {
	            testEvaluators[i] = new SupportEventEvaluator();
	        }

	        testBean = new SupportBean();
	        testEventBean = SupportEventBeanFactory.CreateObject(testBean);
	        testEventType = testEventBean.EventType;
            matchesList = new List<FilterHandle>();
	    }

	    [Test]
	    public void testClosedRange()
	    {
	        FilterParamIndexNotRange index = new FilterParamIndexNotRange("longBoxed", FilterOperator.NOT_RANGE_CLOSED, testEventType);
	        Assert.AreEqual(FilterOperator.NOT_RANGE_CLOSED, index.FilterOperator);

	        index[new DoubleRange(2d, 4d)] = testEvaluators[0];
	        index[new DoubleRange(2d, 5d)] = testEvaluators[1];
	        index[new DoubleRange(1d, 3d)] = testEvaluators[2];
	        index[new DoubleRange(1d, 1d)] = testEvaluators[3];

	        Verify(index, 0L, new bool[] {true, true, true, true});
	        Verify(index, 1L, new bool[] {true, true, false, false});
	        Verify(index, 2L, new bool[] {false, false, false, true});
	        Verify(index, 3L, new bool[] {false, false, false, true});
	        Verify(index, 4L, new bool[] {false, false, true, true});
	        Verify(index, 5L, new bool[] {true, false, true, true});
	        Verify(index, 6L, new bool[] {true, true, true, true});
	    }

	    [Test]
	    public void testOpenRange()
	    {
	        FilterParamIndexNotRange index = new FilterParamIndexNotRange("longBoxed", FilterOperator.NOT_RANGE_OPEN, testEventType);

	        index[new DoubleRange(2d, 4d)] = testEvaluators[0];
	        index[new DoubleRange(2d, 5d)] = testEvaluators[1];
	        index[new DoubleRange(1d, 3d)] = testEvaluators[2];
	        index[new DoubleRange(1d, 1d)] = testEvaluators[3];

	        Verify(index, 0L, new bool[] {true, true, true, true});
	        Verify(index, 1L, new bool[] {true, true, true, true});
	        Verify(index, 2L, new bool[] {true, true, false, true});
	        Verify(index, 3L, new bool[] {false, false, true, true});
	        Verify(index, 4L, new bool[] {true, false, true, true});
	        Verify(index, 5L, new bool[] {true, true, true, true});
	        Verify(index, 6L, new bool[] {true, true, true, true});
	    }

	    [Test]
	    public void testHalfOpenRange()
	    {
	        FilterParamIndexNotRange index = new FilterParamIndexNotRange("longBoxed", FilterOperator.NOT_RANGE_HALF_OPEN, testEventType);

	        index[new DoubleRange(2d, 4d)] = testEvaluators[0];
	        index[new DoubleRange(2d, 5d)] = testEvaluators[1];
	        index[new DoubleRange(1d, 3d)] = testEvaluators[2];
	        index[new DoubleRange(1d, 1d)] = testEvaluators[3];

	        Verify(index, 0L, new bool[] {true, true, true, true});
	        Verify(index, 1L, new bool[] {true, true, false, true});
	        Verify(index, 2L, new bool[] {false, false, false, true});
	        Verify(index, 3L, new bool[] {false, false, true, true});
	        Verify(index, 4L, new bool[] {true, false, true, true});
	        Verify(index, 5L, new bool[] {true, true, true, true});
	        Verify(index, 6L, new bool[] {true, true, true, true});
	    }

	    [Test]
	    public void testHalfClosedRange()
	    {
	        FilterParamIndexNotRange index = new FilterParamIndexNotRange("longBoxed", FilterOperator.NOT_RANGE_HALF_CLOSED, testEventType);

	        index[new DoubleRange(2d, 4d)] = testEvaluators[0];
	        index[new DoubleRange(2d, 5d)] = testEvaluators[1];
	        index[new DoubleRange(1d, 3d)] = testEvaluators[2];
	        index[new DoubleRange(1d, 1d)] = testEvaluators[3];

	        Verify(index, 0L, new bool[] {true, true, true, true});
	        Verify(index, 1L, new bool[] {true, true, true, true});
	        Verify(index, 2L, new bool[] {true, true, false, true});
	        Verify(index, 3L, new bool[] {false, false, false, true});
	        Verify(index, 4L, new bool[] {false, false, true, true});
	        Verify(index, 5L, new bool[] {true, false, true, true});
	        Verify(index, 6L, new bool[] {true, true, true, true});
	    }

	    private void Verify(FilterParamIndexBase index, long? testValue, bool[] expected)
	    {
	        testBean.SetLongBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        for (int i = 0; i < expected.Length; i++)
	        {
	            Assert.AreEqual(expected[i], testEvaluators[i].GetAndResetCountInvoked() == 1, "Unexpected result for eval " + i);
	        }
	    }

	    [Test]
	    public void testInvalid()
	    {
	        try
	        {
	            new FilterParamIndexNotRange("doublePrimitive", FilterOperator.EQUAL, testEventType);
	            Assert.IsTrue(false);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }
	}
} // End of namespace
