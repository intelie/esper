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
import com.espertech.esper.event.EventPropertyDescriptor;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.io.StringWriter;

/**
 * Represents a mapped property or array property, ie. an 'value' property with read method getValue(int index)
 * or a 'array' property via read method getArray() returning an array.
 */
public class MappedProperty extends PropertyBase
{
    private String key;

    /**
     * Ctor.
     * @param propertyName is the property name of the mapped property
     * @param key is the key value to access the mapped property
     */
    public MappedProperty(String propertyName, String key)
    {
        super(propertyName);
        this.key = key;
    }

    /**
     * Returns the key value for mapped access.
     * @return key value
     */
    public String getKey()
    {
        return key;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getMappedProperty(propertyNameAtomic);
        if (propertyDesc == null)
        {
            // property not found, is not a property
            return null;
        }

        Method method = propertyDesc.getReadMethod();
        FastClass fastClass = eventType.getFastClass();
        if (fastClass != null)
        {
            FastMethod fastMethod = fastClass.getMethod(method);
            return new KeyedFastPropertyGetter(fastMethod, key);
        }
        else
        {
            return new KeyedMethodPropertyGetter(method, key);
        }
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getMappedProperty(propertyNameAtomic);
        if (propertyDesc == null)
        {
            // property not found, is not a property
            return null;
        }
        return propertyDesc.getReadMethod().getReturnType();
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes)
    {
        return null;  // Mapped properties are not allowed in non-dynamic form in a map
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes)
    {
        return null;  // Mapped properties are not allowed in non-dynamic form in a map
    }

    public void toPropertyEPL(StringWriter writer)
    {
        writer.append(propertyNameAtomic);
        writer.append("('");
        writer.append(key);
        writer.append("')");
    }
}
