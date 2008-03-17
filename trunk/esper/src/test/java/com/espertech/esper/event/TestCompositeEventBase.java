package com.espertech.esper.event;

import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventAdapterService;

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
        eventType = new CompositeEventType("alias", taggedEventTypes);

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
