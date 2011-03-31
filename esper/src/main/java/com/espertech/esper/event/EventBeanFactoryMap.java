package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventBeanFactory;
import com.espertech.esper.client.EventType;

import java.util.Map;

public class EventBeanFactoryMap implements EventBeanFactory {
    private final EventType type;
    private final EventAdapterService eventAdapterService;

    public EventBeanFactoryMap(EventType type, EventAdapterService eventAdapterService) {
        this.type = type;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean wrap(Object underlying) {
        return eventAdapterService.adaptorForTypedMap((Map)underlying, type);
    }
}
