/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.event.*;
import com.espertech.esper.client.EPException;

import java.util.Map;
import java.io.StringWriter;

/**
 * Represents a simple property of a given name.
 */
public class SimpleProperty extends PropertyBase
{
    /**
     * Ctor.
     * @param propertyName is the property name
     */
    public SimpleProperty(String propertyName)
    {
        super(propertyName);
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getSimpleProperty(propertyNameAtomic);
        if (propertyDesc == null)
        {
            return null;
        }
        if (!propertyDesc.getPropertyType().equals(EventPropertyType.SIMPLE))
        {
            return null;
        }
        return eventType.getGetter(propertyNameAtomic);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getSimpleProperty(propertyNameAtomic);
        if (propertyDesc == null)
        {
            return null;
        }
        return eventType.getPropertyType(propertyNameAtomic);
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        // The simple, none-dynamic property needs a definition of the map contents else no property
        if (optionalMapPropTypes == null)
        {
            return null;
        }
        Object def = optionalMapPropTypes.get(propertyNameAtomic);
        if (def == null)
        {
            return null;
        }
        if (def instanceof Class)
        {
            return (Class) def;
        }
        else if (def instanceof Map)
        {
            return Map.class;
        }
        else if (def instanceof String)
        {
            String propertyName = def.toString();
            boolean isArray = MapEventType.isPropertyArray(propertyName);
            if (isArray) {
                propertyName = MapEventType.getPropertyRemoveArray(propertyName);
            }

            EventType eventType = eventAdapterService.getExistsTypeByAlias(propertyName);
            if (eventType instanceof MapEventType)
            {
                if (isArray)
                {
                    return Map[].class;
                }
                else
                {
                    return Map.class;
                }
            }
        }
        String message = "Nestable map type configuration encountered an unexpected value type of '"
            + def.getClass() + " for property '" + propertyNameAtomic + "', expected Map or Class";
        throw new PropertyAccessException(message);
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        // The simple, none-dynamic property needs a definition of the map contents else no property
        if (optionalMapPropTypes == null)
        {
            return null;
        }
        Object def = optionalMapPropTypes.get(propertyNameAtomic);
        if (def == null)
        {
            return null;
        }

        final String propertyName = this.getPropertyNameAtomic();
        return new EventPropertyGetter()
        {
            public Object get(EventBean eventBean) throws PropertyAccessException
            {
                Map map = (Map) eventBean.getUnderlying();
                return map.get(propertyName);
            }

            public boolean isExistsProperty(EventBean eventBean)
            {
                Map map = (Map) eventBean.getUnderlying();
                return map.containsKey(propertyName);
            }
        };
    }

    public void toPropertyEPL(StringWriter writer)
    {
        writer.append(propertyNameAtomic);
    }
}
