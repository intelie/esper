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
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.event.EventAdapterService;

import java.util.Map;
import java.io.StringWriter;

/**
 * Represents a dynamic mapped property of a given name.
 * <p>
 * Dynamic properties always exist, have an Object type and are resolved to a method during runtime.
 */
public class DynamicMappedProperty extends PropertyBase implements DynamicProperty
{
    private final String key;

    /**
     * Ctor.
     * @param propertyName is the property name
     * @param key is the mapped access key
     */
    public DynamicMappedProperty(String propertyName, String key)
    {
        super(propertyName);
        this.key = key;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        return new DynamicMappedPropertyGetter(propertyNameAtomic, key);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        return Object.class;
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        return Object.class;
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        return new MapMappedPropertyGetter(this.getPropertyNameAtomic(), key);
    }

    public void toPropertyEPL(StringWriter writer)
    {
        writer.append(propertyNameAtomic);
        writer.append("('");
        writer.append(key);
        writer.append("')");
        writer.append('?');
    }
}
