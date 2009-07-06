package com.espertech.esper.event.map;

import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.client.EventBean;

import java.util.Map;

public class MapEventBeanWriter implements EventBeanWriter
{
    private final String[] properties;

    public MapEventBeanWriter(String[] properties)
    {
        this.properties = properties;
    }

    public void write(Object[] values, EventBean event)
    {
        MappedEventBean mappedEvent = (MappedEventBean) event;
        Map<String, Object> map = mappedEvent.getProperties();

        for (int i = 0; i < properties.length; i++)
        {
            map.put(properties[i], values[i]);
        }
    }
}
