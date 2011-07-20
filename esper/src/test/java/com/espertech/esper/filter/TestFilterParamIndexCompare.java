/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.filter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.filter.SupportEventEvaluator;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestFilterParamIndexCompare extends TestCase
{
    private SupportEventEvaluator testEvaluator;
    private SupportBean testBean;
    private EventBean testEventBean;
    private EventType testEventType;
    private List<FilterHandle> matchesList;

    public void setUp()
    {
        testEvaluator = new SupportEventEvaluator();
        testBean = new SupportBean();
        testEventBean = SupportEventBeanFactory.createObject(testBean);
        testEventType = testEventBean.getEventType();
        matchesList = new LinkedList<FilterHandle>();
    }

    public void testInvalid()
    {
        try
        {
            new FilterParamIndexCompare("doublePrimitive", FilterOperator.EQUAL, testEventType);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            new FilterParamIndexCompare("doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testMatchDoubleAndGreater()
    {
        FilterParamIndexCompare index = new FilterParamIndexCompare("doublePrimitive", FilterOperator.GREATER, testEventType);

        index.put(Double.valueOf(1.5), testEvaluator);
        index.put(Double.valueOf(2.1), testEvaluator);
        index.put(Double.valueOf(2.2), testEvaluator);

        verifyDoublePrimitive(index, 1.5, 0);
        verifyDoublePrimitive(index, 1.7, 1);
        verifyDoublePrimitive(index, 2.2, 2);
        verifyDoublePrimitive(index, 2.1999999, 2);
        verifyDoublePrimitive(index, -1, 0);
        verifyDoublePrimitive(index, 99, 3);

        assertEquals(testEvaluator, index.get(1.5d));
        assertTrue(index.getReadWriteLock() != null);
        assertTrue(index.remove(1.5d));
        assertFalse(index.remove(1.5d));
        assertEquals(null, index.get(1.5d));

        try
        {
            index.put("a", testEvaluator);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testMatchLongAndGreaterEquals()
    {
        FilterParamIndexCompare index = new FilterParamIndexCompare("longBoxed", FilterOperator.GREATER_OR_EQUAL, testEventType);

        index.put(Long.valueOf(1), testEvaluator);
        index.put(Long.valueOf(2), testEvaluator);
        index.put(Long.valueOf(4), testEvaluator);

        // Should not match with null
        verifyLongBoxed(index, null, 0);

        verifyLongBoxed(index, 0L, 0);
        verifyLongBoxed(index, 1L, 1);
        verifyLongBoxed(index, 2L, 2);
        verifyLongBoxed(index, 3L, 2);
        verifyLongBoxed(index, 4L, 3);
        verifyLongBoxed(index, 10L, 3);

        // Put a long primitive in - should work
        index.put(9l, testEvaluator);
        try
        {
            index.put(10, testEvaluator);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testMatchLongAndLessThan()
    {
        FilterParamIndexCompare index = new FilterParamIndexCompare("longPrimitive", FilterOperator.LESS, testEventType);

        index.put(Long.valueOf(1), testEvaluator);
        index.put(Long.valueOf(10), testEvaluator);
        index.put(Long.valueOf(100), testEvaluator);

        verifyLongPrimitive(index, 100, 0);
        verifyLongPrimitive(index, 101, 0);
        verifyLongPrimitive(index, 99, 1);
        verifyLongPrimitive(index, 11, 1);
        verifyLongPrimitive(index, 10, 1);
        verifyLongPrimitive(index, 9, 2);
        verifyLongPrimitive(index, 2, 2);
        verifyLongPrimitive(index, 1, 2);
        verifyLongPrimitive(index, 0, 3);
    }

    public void testMatchDoubleAndLessOrEqualThan()
    {
        FilterParamIndexCompare index = new FilterParamIndexCompare("doubleBoxed", FilterOperator.LESS_OR_EQUAL, testEventType);

        index.put(7.4D, testEvaluator);
        index.put(7.5D, testEvaluator);
        index.put(7.6D, testEvaluator);

        verifyDoubleBoxed(index, 7.39, 3);
        verifyDoubleBoxed(index, 7.4, 3);
        verifyDoubleBoxed(index, 7.41, 2);
        verifyDoubleBoxed(index, 7.5, 2);
        verifyDoubleBoxed(index, 7.51, 1);
        verifyDoubleBoxed(index, 7.6, 1);
        verifyDoubleBoxed(index, 7.61, 0);
    }

    private void verifyDoublePrimitive(FilterParamIndexBase index, double testValue, int numExpected)
    {
        testBean.setDoublePrimitive(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyDoubleBoxed(FilterParamIndexBase index, Double testValue, int numExpected)
    {
        testBean.setDoubleBoxed(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyLongBoxed(FilterParamIndexBase index, Long testValue, int numExpected)
    {
        testBean.setLongBoxed(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyLongPrimitive(FilterParamIndexBase index, long testValue, int numExpected)
    {
        testBean.setLongPrimitive(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }
}