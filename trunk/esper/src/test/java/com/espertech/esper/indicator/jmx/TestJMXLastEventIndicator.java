package com.espertech.esper.indicator.jmx;

import junit.framework.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestJMXLastEventIndicator extends TestCase
{
    private JMXLastEventIndicator indicator;

    public void setUp()
    {
        EventType eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        indicator = new JMXLastEventIndicator(eventType, "test", "");
    }

    public void testUpdate()
    {
        // Smoke test only
        EventBean[] events = new EventBean[] { makeEvent() };
        indicator.update(events, null);
    }

    private EventBean makeEvent()
    {
        return SupportEventBeanFactory.createObject(new SupportBean());
    }
}