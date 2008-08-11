package com.espertech.esper.event;

import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBean_C;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.collection.Pair;

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
        Map<String, Pair<EventType, String>> taggedEventTypes = new HashMap<String, Pair<EventType, String>>();
        taggedEventTypes.put("a", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("A", SupportBean.class), "AType"));
        taggedEventTypes.put("b", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("B", SupportBeanComplexProps.class), "BType"));

        Map<String, Pair<EventType, String>> arrayEventTypes = new HashMap<String, Pair<EventType, String>>();
        arrayEventTypes.put("c", new Pair<EventType, String>(SupportEventAdapterService.getService().addBeanType("C", SupportBean_C.class), "CType"));
        eventType = new CompositeEventType("alias", taggedEventTypes, arrayEventTypes);

        event = new SupportBean();
        event.setIntPrimitive(1);

        Map<String, Object> wrappedEvents = new HashMap<String, Object>();
        wrappedEvents.put("a", SupportEventAdapterService.getService().adapterForBean(event));
        wrappedEvents.put("b", SupportEventAdapterService.getService().adapterForBean(SupportBeanComplexProps.makeDefaultBean()));

        EventBean[] cArray = new EventBean[2];
        cArray[0] = SupportEventAdapterService.getService().adapterForBean(new SupportBean_C("C0"));
        cArray[1] = SupportEventAdapterService.getService().adapterForBean(new SupportBean_C("C1"));
        wrappedEvents.put("c", cArray);

        eventBeanComplete = new CompositeEventBean(wrappedEvents, eventType);

        wrappedEvents = new HashMap<String, Object>();
        wrappedEvents.put("a", SupportEventAdapterService.getService().adapterForBean(event));
        eventBeanInComplete = new CompositeEventBean(wrappedEvents, eventType);
    }

}
