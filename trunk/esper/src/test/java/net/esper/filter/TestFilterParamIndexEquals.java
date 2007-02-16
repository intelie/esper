package net.esper.filter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.filter.SupportEventEvaluator;
import net.esper.support.event.SupportEventBeanFactory;

public class TestFilterParamIndexEquals extends TestCase
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

    public void testLong()
    {
        FilterParamIndexEquals index = new FilterParamIndexEquals("shortBoxed", testEventType);

        index.put(Short.valueOf((short) 1), testEvaluator);
        index.put(Short.valueOf((short) 20), testEvaluator);

        verifyShortBoxed(index, (short) 10, 0);
        verifyShortBoxed(index, (short) 1, 1);
        verifyShortBoxed(index, (short) 20, 1);
        verifyShortBoxed(index, null, 0);

        assertEquals(testEvaluator, index.get((short) 1));
        assertTrue(index.getReadWriteLock() != null);
        assertTrue(index.remove((short) 1));
        assertFalse(index.remove((short) 1));
        assertEquals(null, index.get((short) 1));

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

    public void testBoolean()
    {
        FilterParamIndexEquals index = new FilterParamIndexEquals("boolPrimitive", testEventType);

        index.put(false, testEvaluator);

        verifyBooleanPrimitive(index, false, 1);
        verifyBooleanPrimitive(index, true, 0);
    }

    public void testString()
    {
        FilterParamIndexEquals index = new FilterParamIndexEquals("string", testEventType);

        index.put("hello", testEvaluator);
        index.put("test", testEvaluator);

        verifyString(index, null, 0);
        verifyString(index, "dudu", 0);
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

    public void testFloatPrimitive()
    {
        FilterParamIndexEquals index = new FilterParamIndexEquals("floatPrimitive", testEventType);

        index.put(1.5f, testEvaluator);

        verifyFloatPrimitive(index, 1.5f, 1);
        verifyFloatPrimitive(index, 2.2f, 0);
        verifyFloatPrimitive(index, 0, 0);

        try
        {
            index.put(new Double(20), testEvaluator);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    private void verifyShortBoxed(FilterParamIndex index, Short testValue, int numExpected)
    {
        testBean.setShortBoxed(testValue);
        index.matchEvent(testEventBean, matchesList);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyBooleanPrimitive(FilterParamIndex index, boolean testValue, int numExpected)
    {
        testBean.setBoolPrimitive(testValue);
        index.matchEvent(testEventBean, matchesList);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyString(FilterParamIndex index, String testValue, int numExpected)
    {
        testBean.setString(testValue);
        index.matchEvent(testEventBean, matchesList);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }

    private void verifyFloatPrimitive(FilterParamIndex index, float testValue, int numExpected)
    {
        testBean.setFloatPrimitive(testValue);
        index.matchEvent(testEventBean, matchesList);
        assertEquals(numExpected, testEvaluator.getAndResetCountInvoked());
    }
}