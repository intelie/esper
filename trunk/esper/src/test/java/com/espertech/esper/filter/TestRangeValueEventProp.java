package com.espertech.esper.filter;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestRangeValueEventProp extends TestCase
{
    private FilterSpecParamRangeValue params[] = new FilterSpecParamRangeValue[5];

    public void setUp()
    {
        params[0] = new RangeValueEventProp("a", "b");
        params[1] = new RangeValueEventProp("asName", "b");
        params[2] = new RangeValueEventProp("asName", "boolPrimitive");
        params[3] = new RangeValueEventProp("asName", "intPrimitive");
        params[4] = new RangeValueEventProp("asName", "intPrimitive");
    }

    public void testGetFilterValue()
    {
        SupportBean eventBean = new SupportBean();
        eventBean.setIntPrimitive(1000);
        EventBean event = SupportEventBeanFactory.createObject(eventBean);
        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
        matchedEvents.add("asName", event);

        tryInvalidGetFilterValue(matchedEvents, params[0]);
        tryInvalidGetFilterValue(matchedEvents, params[1]);
        assertEquals(1000.0, params[3].getFilterValue(matchedEvents));
    }

    public void testEquals()
    {
        assertFalse(params[0].equals(params[1]));
        assertFalse(params[2].equals(params[3]));
        assertTrue(params[3].equals(params[4]));
    }

    private void tryInvalidGetFilterValue(MatchedEventMap matchedEvents, FilterSpecParamRangeValue value)
    {
        try
        {
            value.getFilterValue(matchedEvents);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }
}
