package net.esper.event;

import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.event.SupportEventAdapterService;

import java.util.Map;
import java.util.HashMap;

import junit.framework.TestCase;

public abstract class TestCompositeEventBase extends TestCase
{
    protected EventType eventType;
    protected EventBean eventBeanComplete;
    protected EventBean eventBeanInComplete;
    protected SupportBean event;

    public void setUp()
    {
        Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>();
        taggedEventTypes.put("a", SupportEventAdapterService.getService().addBeanType("A", SupportBean.class));
        taggedEventTypes.put("b", SupportEventAdapterService.getService().addBeanType("B", SupportBeanComplexProps.class));
        eventType = new CompositeEventType(taggedEventTypes);

        event = new SupportBean();
        event.setIntPrimitive(1);

        Map<String, EventBean> wrappedEvents = new HashMap<String, EventBean>();
        wrappedEvents.put("a", SupportEventAdapterService.getService().adapterForBean(event));
        wrappedEvents.put("b", SupportEventAdapterService.getService().adapterForBean(SupportBeanComplexProps.makeDefaultBean()));
        eventBeanComplete = new CompositeEventBean(wrappedEvents, eventType);

        wrappedEvents = new HashMap<String, EventBean>();
        wrappedEvents.put("a", SupportEventAdapterService.getService().adapterForBean(event));
        eventBeanInComplete = new CompositeEventBean(wrappedEvents, eventType);
    }

}
