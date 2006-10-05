package net.esper.indicator.jmx;

import junit.framework.*;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;

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