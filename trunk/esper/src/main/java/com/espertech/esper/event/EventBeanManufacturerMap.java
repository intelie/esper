package com.espertech.esper.event;

import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.client.EventBean;

import java.util.Map;
import java.util.HashMap;

public class EventBeanManufacturerMap implements EventBeanManufacturer
{
    private final MapEventType mapEventType;
    private final EventAdapterServiceImpl eventAdapterService;
    private final WriteablePropertyDescriptor[] writables;

    public EventBeanManufacturerMap(MapEventType mapEventType, EventAdapterServiceImpl eventAdapterService, WriteablePropertyDescriptor[] properties)
    {
        this.eventAdapterService = eventAdapterService;
        this.mapEventType = mapEventType;
        this.writables = properties;
    }

    public EventBean make(Object[] properties)
    {
        Map<String, Object> values = new HashMap<String, Object>();
        for (int i = 0; i < properties.length; i++)
        {
            values.put(writables[i].getPropertyName(), properties[i]);
        }
        return eventAdapterService.adaptorForTypedMap(values, mapEventType);
    }
}
