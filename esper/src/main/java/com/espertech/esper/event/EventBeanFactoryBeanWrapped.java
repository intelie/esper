package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;

import java.util.Collections;

public class EventBeanFactoryBeanWrapped implements EventBeanFactory {

    private final EventType beanEventType;
    private final EventType wrapperEventType;
    private final EventAdapterService eventAdapterService;

    public EventBeanFactoryBeanWrapped(EventType beanEventType, EventType wrapperEventType, EventAdapterService eventAdapterService) {
        this.beanEventType = beanEventType;
        this.wrapperEventType = wrapperEventType;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean wrap(Object underlying) {
        EventBean bean = eventAdapterService.adapterForTypedBean(underlying, beanEventType);
        return eventAdapterService.adaptorForTypedWrapper(bean, Collections.<String, Object>emptyMap(), wrapperEventType);
    }
}
