/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import java.util.Map;
import java.util.HashMap;

/**
 * Wrapper for events represented by a Map of key-value pairs that are the event properties.
 * MapEventBean instances are equal if they have the same {@link EventType} and all property names
 * and values are reference-equal.
 */
public class MapEventBean implements EventBean
{
    private EventType eventType;
    private Map<String, Object> properties;

    /**
     * Constructor for initialization with existing values.
     * Makes a shallow copy of the supplied values to not be surprised by changing property values.
     * @param properties are the event property values
     * @param eventType is the type of the event, i.e. describes the map entries
     */
    public MapEventBean(Map<String, Object> properties, EventType eventType)
    {
        this.properties = new HashMap<String, Object>();
        this.properties.putAll(properties);
        this.eventType = eventType;
    }

    /**
     * Constructor for initialization with existing values.
     * Makes a shallow copy of the supplied values to not be surprised by changing property values.
     * @param eventType is the type of the event, i.e. describes the map entries
     * @param events are the event property constisting of events
     */
    public MapEventBean(EventType eventType, Map<String, EventBean> events)
    {
        this.properties = new HashMap<String, Object>();
        for (Map.Entry<String, EventBean> entry : events.entrySet())
        {
            properties.put(entry.getKey(), entry.getValue().getUnderlying());
        }
        this.eventType = eventType;
    }

    /**
     * Constructor for the mutable functions, e.g. only the type of values is known but not the actual values.
     * @param eventType is the type of the event, i.e. describes the map entries
     */
    public MapEventBean(EventType eventType)
    {
        this.properties = new HashMap<String, Object>();
        this.eventType = eventType;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns the properties.
     * @return properties
     */
    public Map<String, Object> getProperties()
    {
        return properties;
    }

    public Object get(String property) throws IllegalArgumentException, PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter == null)
        {
            throw new IllegalArgumentException("Property named '" + property + "' is not a valid property name for this type");
        }
        return eventType.getGetter(property).get(this);
    }

    public Object getUnderlying()
    {
        return properties;
    }

    public String toString()
    {
        return "MapEventBean " +
                "eventType=" + eventType;
    }
}
