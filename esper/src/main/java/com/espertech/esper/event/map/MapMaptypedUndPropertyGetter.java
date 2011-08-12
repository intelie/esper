/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event.map;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;

import java.util.Map;

/**
 * Getter for retrieving a value from a map.
 */
public class MapMaptypedUndPropertyGetter implements MapEventPropertyGetter
{
    private final String propertyName;
    private final EventAdapterService eventAdapterService;
    private final EventType fragmentType;

    /**
     * Ctor.
     * @param propertyNameAtomic property name
     * @param eventAdapterService factory for event beans and event types
     * @param fragmentType type of the entry returned
     */
    public MapMaptypedUndPropertyGetter(String propertyNameAtomic, EventAdapterService eventAdapterService, EventType fragmentType)
    {
        propertyName = propertyNameAtomic;
        this.fragmentType = fragmentType;
        this.eventAdapterService = eventAdapterService;
    }

    public Object getMap(Map<String, Object> map) throws PropertyAccessException
    {
        return map.get(propertyName);
    }

    public boolean isMapExistsProperty(Map<String, Object> map)
    {
        return true;
    }

    public Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        // The underlying is expected to be a map
        if (!(underlying instanceof Map))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type java.lang.Map");
        }

        Map map = (Map) underlying;

        return map.get(propertyName);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }

    public Object getFragment(EventBean obj) throws PropertyAccessException
    {
        Map<String, Object> value = (Map<String, Object>) get(obj);

        if (value == null)
        {
            return null;
        }

        return eventAdapterService.adapterForTypedMap(value, fragmentType);
    }
}
