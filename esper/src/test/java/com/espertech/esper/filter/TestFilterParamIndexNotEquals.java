package com.espertech.esper.filter;

import junit.framework.TestCase;
import com.espertech.esper.support.filter.SupportEventEvaluator;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

import java.util.List;
import java.util.LinkedList;

public class TestFilterParamIndexNotEquals extends TestCase
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

    public void testBoolean()
    {
        FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("boolPrimitive", testEventType);
        assertEquals(FilterOperator.NOT_EQUAL, index.getFilterOperator());
        assertEquals("boolPrimitive", index.getPropertyName());

        index.put(false, testEvaluator);

        verifyBooleanPrimitive(index, true, 1);
        verifyBooleanPrimitive(index, false, 0);
    }

    public void testString()
    {
        FilterParamIndexNotEquals index = new FilterParamIndexNotEquals("string", testEventType);

        index.put("hello", testEvaluator);
        index.put("test", testEvaluator);

        verifyString(index, null, 0);
        verifyString(index, "dudu", 2);
        verifyString(index, "hello", 1);
        verifyString(index, "test", 1);

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

    private void verifyBooleanPrimitive(FilterParamIndexBase index, boolean testValue, int numExpected)
    {
        testBean.setBoolPrimitive(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyString(FilterParamIndexBase index, String testValue, int numExpected)
    {
        testBean.setString(testValue);
        index.matchEvent(testEventBean, matchesList, null);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }
}
