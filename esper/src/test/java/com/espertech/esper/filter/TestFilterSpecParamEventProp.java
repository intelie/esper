package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.MatchedEventMapImpl;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.util.SimpleNumberCoercer;
import com.espertech.esper.util.SimpleNumberCoercerFactory;
import junit.framework.TestCase;

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
    }

    private FilterSpecParamEventProp makeParam(String eventAsName, String property)
    {
        SimpleNumberCoercer numberCoercer = SimpleNumberCoercerFactory.getCoercer(int.class, int.class);
        return new FilterSpecParamEventProp("intPrimitive", FilterOperator.EQUAL, eventAsName, property, false, numberCoercer, int.class, "Test");
    }
}
