package net.esper.regression.support;

import java.util.Map;
import java.util.HashMap;

public class EventDescriptor
{
    private Map<String, Object> eventProperties;

    public EventDescriptor()
    {
        eventProperties = new HashMap<String, Object>();
    }

    public Map<String, Object> getEventProperties()
    {
        return eventProperties;
    }

    public void put(String propertyName, Object value)
    {
        eventProperties.put(propertyName, value);
    }
}