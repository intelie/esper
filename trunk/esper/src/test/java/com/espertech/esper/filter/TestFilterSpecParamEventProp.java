package com.espertech.esper.filter;

import junit.framework.TestCase;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.Map;
import java.util.HashMap;

public class TestFilterSpecParamEventProp extends TestCase
{
    public void testEquals()
    {
        FilterSpecParamEventProp params[] = new FilterSpecParamEventProp[5];
        params[0] = makeParam("a", "intBoxed");
        params[1] = makeParam("b", "intBoxed");
        params[2] = makeParam("a", "intPrimitive");
        params[3] = makeParam("c", "intPrimitive");
        params[4] = makeParam("a", "intBoxed");

        assertEquals(params[0], params[4]);
        assertEquals(params[4], params[0]);
        assertFalse(params[0].equals(params[1]));
        assertFalse(params[0].equals(params[2]));
        assertFalse(params[0].equals(params[3]));
    }

    public void testGetFilterValue()
    {
        FilterSpecParamEventProp params = makeParam("asName", "intBoxed");

        SupportBean eventBean = new SupportBean();
        eventBean.setIntBoxed(1000);
        EventBean event = SupportEventBeanFactory.createObject(eventBean);

        MatchedEventMap matchedEvents = new MatchedEventMapImpl();
        matchedEvents.add("asName", event);

        assertEquals(1000, params.getFilterValue(matchedEvents));

        try
        {
            params.getFilterValue(new MatchedEventMapImpl());
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            params.getFilterValue(null);
            fail();
        }
        catch (NullPointerException ex)
        {
            // Expected
        }
    }

    private FilterSpecParamEventProp makeParam(String eventAsName, String property)
    {
        return new FilterSpecParamEventProp("intPrimitive", FilterOperator.EQUAL, eventAsName, property, false, int.class);
    }
}
