/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.event.MapEventBean;

import java.util.Map;

/**
 * A getter that interrogates a given property in a map which may itself contain nested maps or indexed entries.
 */
public class MapPropertyGetter implements EventPropertyGetter
{
    private final String propertyMap;
    private final EventPropertyGetter getter;

    /**
     * Ctor.
     * @param propertyMap is the property returning the map to interrogate
     * @param getter is the getter to use to interrogate the property in the map
     */
    public MapPropertyGetter(String propertyMap, EventPropertyGetter getter)
    {
        if (getter == null)
        {
            throw new IllegalArgumentException("Getter is a required parameter");
        }
        this.propertyMap = propertyMap;
        this.getter = getter;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        // The map contains a map-type property, that we are querying, named valueTop.
        // (A map could also contain an object-type property handled as a Java object).
        Object result = eventBean.getUnderlying();
        if (!(result instanceof Map))
        {
            return null;
        }
        Map map = (Map) result;

        Object valueTopObj = map.get(propertyMap);
        if (!(valueTopObj instanceof Map))
        {
            return null;
        }

        Map valueTop = (Map) valueTopObj;
        // Obtains for the inner map the property value
        EventBean event = new MapEventBean(valueTop, null);
        return getter.get(event);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        Object result = eventBean.getUnderlying();
        if (!(result instanceof Map))
        {
            return false;
        }
        Map map = (Map) result;

        Object valueTopObj = map.get(propertyMap);
        if (!(valueTopObj instanceof Map))
        {
            return false;
        }

        Map valueTop = (Map) valueTopObj;
        // Obtains for the inner map the property value
        EventBean event = new MapEventBean(valueTop, null);
        return getter.isExistsProperty(event);
    }
}
