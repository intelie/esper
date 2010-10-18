package com.espertech.esper.filter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.filter.SupportEventEvaluator;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestFilterParamIndexRange extends TestCase
{
    private SupportEventEvaluator testEvaluator;
    private SupportBean testBean;
    private EventBean testEventBean;
    private EventType testEventType;
    private List<FilterHandle> matchesList;
    private DoubleRange testRange;

    public void setUp()
    {
        testEvaluator = new SupportEventEvaluator();
        testBean = new SupportBean();
        testEventBean = SupportEventBeanFactory.createObject(testBean);
        testEventType = testEventBean.getEventType();
        matchesList = new LinkedList<FilterHandle>();

        testRange = new DoubleRange(10d, 20d);
    }

    public void testInvalid()
    {
        try
        {
            new FilterParamIndexRange("doublePrimitive", FilterOperator.EQUAL, testEventType);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            new FilterParamIndexCompare("string", FilterOperator.RANGE_CLOSED, testEventType);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testLongBothEndpointsIncluded()
    {
        FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_CLOSED);
        verifyLongPrimitive(index, -1, 0);
        verifyLongPrimitive(index, 0, 2);
        verifyLongPrimitive(index, 1, 5);
        verifyLongPrimitive(index, 2, 5);
        verifyLongPrimitive(index, 3, 7);
        verifyLongPrimitive(index, 4, 6);
        verifyLongPrimitive(index, 5, 6);
        verifyLongPrimitive(index, 6, 6);
        verifyLongPrimitive(index, 7, 6);
        verifyLongPrimitive(index, 8, 6);
        verifyLongPrimitive(index, 9, 5);
        verifyLongPrimitive(index, 10, 3);
        verifyLongPrimitive(index, 11, 1);

        index.put(testRange, testEvaluator);
        assertEquals(testEvaluator, index.get(testRange));
        assertTrue(index.getReadWriteLock() != null);
        assertTrue(index.remove(testRange));
        assertFalse(index.remove(testRange));
        assertEquals(null, index.get(testRange));

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

    public void testLongLowEndpointIncluded()
    {
        FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_HALF_OPEN);
        verifyLongPrimitive(index, -1, 0);
        verifyLongPrimitive(index, 0, 2);
        verifyLongPrimitive(index, 1, 5);
        verifyLongPrimitive(index, 2, 5);
        verifyLongPrimitive(index, 3, 6);
        verifyLongPrimitive(index, 4, 6);
        verifyLongPrimitive(index, 5, 3);
        verifyLongPrimitive(index, 6, 5);
        verifyLongPrimitive(index, 7, 4);
        verifyLongPrimitive(index, 8, 5);
        verifyLongPrimitive(index, 9, 3);
        verifyLongPrimitive(index, 10, 1);
        verifyLongPrimitive(index, 11, 1);
    }

    public void testLongHighEndpointIncluded()
    {
        FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_HALF_CLOSED);
        verifyLongPrimitive(index, -1, 0);
        verifyLongPrimitive(index, 0, 0);
        verifyLongPrimitive(index, 1, 2);
        verifyLongPrimitive(index, 2, 5);
        verifyLongPrimitive(index, 3, 5);
        verifyLongPrimitive(index, 4, 6);
        verifyLongPrimitive(index, 5, 6);
        verifyLongPrimitive(index, 6, 3);
        verifyLongPrimitive(index, 7, 5);
        verifyLongPrimitive(index, 8, 4);
        verifyLongPrimitive(index, 9, 5);
        verifyLongPrimitive(index, 10, 3);
        verifyLongPrimitive(index, 11, 1);
    }

    public void testLongNeitherEndpointIncluded()
    {
        FilterParamIndexRange index = this.getLongDataset(FilterOperator.RANGE_OPEN);
        verifyLongPrimitive(index, -1, 0);
        verifyLongPrimitive(index, 0, 0);
        verifyLongPrimitive(index, 1, 2);
        verifyLongPrimitive(index, 2, 5);
        verifyLongPrimitive(index, 3, 4);
        verifyLongPrimitive(index, 4, 6);
        verifyLongPrimitive(index, 5, 3);
        verifyLongPrimitive(index, 6, 2);
        verifyLongPrimitive(index, 7, 3);
        verifyLongPrimitive(index, 8, 3);
        verifyLongPrimitive(index, 9, 3);
        verifyLongPrimitive(index, 10, 1);
        verifyLongPrimitive(index, 11, 1);
    }

    public void testDoubleBothEndpointsIncluded()
    {
        FilterParamIndexRange index = this.getDoubleDataset(FilterOperator.RANGE_CLOSED);
        verifyDoublePrimitive(index, 1.49, 0);
        verifyDoublePrimitive(index, 1.5, 1);
        verifyDoublePrimitive(index, 2.5, 1);
        verifyDoublePrimitive(index, 2.51, 0);
        verifyDoublePrimitive(index, 3.5, 2);
        verifyDoublePrimitive(index, 4.4, 2);
        verifyDoublePrimitive(index, 4.5, 2);
        verifyDoublePrimitive(index, 4.5001, 1);
        verifyDoublePrimitive(index, 5.1, 1);
        verifyDoublePrimitive(index, 5.8, 2);
        verifyDoublePrimitive(index, 6.7, 2);
        verifyDoublePrimitive(index, 6.8, 1);
        verifyDoublePrimitive(index, 9.5, 1);
        verifyDoublePrimitive(index, 10.1, 0);
    }

    public void testDoubleFixedRangeSize()
    {
        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_OPEN, testEventType);

        for (int i = 0; i < 10000; i++)
        {
            DoubleRange range = new DoubleRange(new Double(i), new Double(i+1));
            index.put(range, testEvaluator);
        }

        verifyDoublePrimitive(index, 5000, 0);
        verifyDoublePrimitive(index, 5000.5, 1);
        verifyDoublePrimitive(index, 5001, 0);
    }

    public void testDoubleVariableRangeSize()
    {
        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType);

        for (int i = 0; i < 100; i++)
        {
            DoubleRange range = new DoubleRange(new Double(i), new Double(2 * i));
            index.put(range, testEvaluator);
        }

        // 1 to 2
        // 2 to 4
        // 3 to 6
        // and so on

        verifyDoublePrimitive(index, 1, 1);
        verifyDoublePrimitive(index, 2, 2);
        verifyDoublePrimitive(index, 2.001, 1);
        verifyDoublePrimitive(index, 3, 2);
        verifyDoublePrimitive(index, 4, 3);
        verifyDoublePrimitive(index, 4.5, 2);
        verifyDoublePrimitive(index, 50, 26);
    }

    private FilterParamIndexRange getLongDataset(FilterOperator operatorType)
    {
        FilterParamIndexRange index = new FilterParamIndexRange("longPrimitive", operatorType, testEventType);

        addToIndex(index,0,5);
        addToIndex(index,0,6);
        addToIndex(index,1,3);
        addToIndex(index,1,5);
        addToIndex(index,1,7);
        addToIndex(index,3,5);
        addToIndex(index,3,7);
        addToIndex(index,6,9);
        addToIndex(index,6,10);
        addToIndex(index,6,Integer.MAX_VALUE - 1);
        addToIndex(index,7,8);
        addToIndex(index,8,9);
        addToIndex(index,8,10);

        return index;
    }

    private FilterParamIndexRange getDoubleDataset(FilterOperator operatorType)
    {
        FilterParamIndexRange index = new FilterParamIndexRange("doublePrimitive", operatorType, testEventType);

        addToIndex(index, 1.5, 2.5);
        addToIndex(index, 3.5, 4.5 );
        addToIndex(index, 3.5, 9.5);
        addToIndex(index, 5.6, 6.7);

        return index;
    }

    private void verifyDoublePrimitive(FilterParamIndexBase index, double testValue, int numExpected)
    {
        testBean.setDoublePrimitive(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyLongPrimitive(FilterParamIndexBase index, long testValue, int numExpected)
    {
        testBean.setLongPrimitive(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void addToIndex(FilterParamIndexRange index, double min, double max)
    {
        DoubleRange r = new DoubleRange(min,max);
        index.put(r, testEvaluator);
    }
}
