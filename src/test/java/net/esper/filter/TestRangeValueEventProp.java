package net.esper.filter;

import net.esper.event.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;
import java.util.HashMap;

import junit.framework.TestCase;

public class TestRangeValueEventProp extends TestCase
{
    private FilterSpecParamRangeValue params[] = new FilterSpecParamRangeValue[5];

    public void setUp()
    {
        params[0] = new RangeValueEventProp("a", "b");
        params[1] = new RangeValueEventProp("asName", "b");
        params[2] = new RangeValueEventProp("asName", "boolBoxed");
        params[3] = new RangeValueEventProp("asName", "intPrimitive");
        params[4] = new RangeValueEventProp("asName", "intPrimitive");
    }

    public void testCheckType()
    {
        Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>();
        taggedEventTypes.put("asName", SupportEventTypeFactory.createBeanType(SupportBean.class));

        tryInvalidCheckType(taggedEventTypes, params[0]);
        tryInvalidCheckType(taggedEventTypes, params[1]);
        tryInvalidCheckType(taggedEventTypes, params[2]);
        params[3].checkType(taggedEventTypes);
    }

    public void testGetFilterValue()
    {
        SupportBean eventBean = new SupportBean();
        eventBean.setIntPrimitive(1000);
        EventBean event = SupportEventBeanFactory.createObject(eventBean);
        MatchedEventMap matchedEvents = new MatchedEventMap();
        matchedEvents.add("asName", event);

        tryInvalidGetFilterValue(matchedEvents, params[0]);
        tryInvalidGetFilterValue(matchedEvents, params[1]);
        tryInvalidGetFilterValue(matchedEvents, params[2]);
        assertEquals(1000.0, params[3].getFilterValue(matchedEvents));
    }

    public void testEquals()
    {
        assertFalse(params[0].equals(params[1]));
        assertFalse(params[2].equals(params[3]));
        assertTrue(params[3].equals(params[4]));
    }

    private void tryInvalidCheckType(Map<String, EventType> taggedEventTypes, FilterSpecParamRangeValue value)
    {
        try
        {
            value.checkType(taggedEventTypes);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
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
