package com.espertech.esper.event.map;

import com.espertech.esper.event.EventBeanCopyMethod;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.MappedEventBean;
import com.espertech.esper.client.EventBean;

import java.util.Map;
import java.util.HashMap;

public class MapEventBeanCopyMethod implements EventBeanCopyMethod
{
    private final MapEventType mapEventType;
    private final EventAdapterService eventAdapterService;

    public MapEventBeanCopyMethod(MapEventType mapEventType, EventAdapterService eventAdapterService)
    {
        this.mapEventType = mapEventType;
        this.eventAdapterService = eventAdapterService;
    }

    public EventBean copy(EventBean event)
    {
        MappedEventBean mapped = (MappedEventBean) event;
        Map<String, Object> props = mapped.getProperties();
        return eventAdapterService.adaptorForTypedMap(new HashMap<String, Object>(props), mapEventType);
    }
}
