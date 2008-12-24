/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.*;
import com.espertech.esper.event.xml.SchemaElementComplex;
import com.espertech.esper.event.xml.SchemaElementSimple;
import com.espertech.esper.event.xml.SchemaItem;
import com.espertech.esper.event.xml.getter.DOMIndexedGetter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Represents an indexed property or array property, ie. an 'value' property with read method getValue(int index)
 * or a 'array' property via read method getArray() returning an array.
 */
public class IndexedProperty extends PropertyBase
{
    private int index;

    /**
     * Ctor.
     * @param propertyName is the property name
     * @param index is the index to use to access the property value
     */
    public IndexedProperty(String propertyName, int index)
    {
        super(propertyName);
        this.index = index;
    }

    public String[] toPropertyArray()
    {
        return new String[] {this.getPropertyNameAtomic()};
    }

    /**
     * Returns index for indexed access.
     * @return index value
     */
    public int getIndex()
    {
        return index;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType, EventAdapterService eventAdapterService)
    {
        FastClass fastClass = eventType.getFastClass();
        InternalEventPropDescriptor propertyDesc = eventType.getIndexedProperty(propertyNameAtomic);
        if (propertyDesc != null)
        {
            if (fastClass != null)
            {
                Method method = propertyDesc.getReadMethod();
                FastMethod fastMethod = fastClass.getMethod(method);
                return new KeyedFastPropertyGetter(fastMethod, index, eventAdapterService);
            }
            else
            {
                return new KeyedMethodPropertyGetter(propertyDesc.getReadMethod(), index, eventAdapterService);
            }
        }

        // Try the array as a simple property
        propertyDesc = eventType.getSimpleProperty(propertyNameAtomic);
        if (propertyDesc == null)
        {
            return null;
        }

        Class returnType = propertyDesc.getReturnType();
        if (returnType.isArray())
        {
            if (propertyDesc.getReadMethod() != null)
            {
                Method method = propertyDesc.getReadMethod();
                if (fastClass != null)
                {
                    FastMethod fastMethod = fastClass.getMethod(method);
                    return new ArrayFastPropertyGetter(fastMethod, index, eventAdapterService);
                }
                else
                {
                    return new ArrayMethodPropertyGetter(method, index, eventAdapterService);
                }
            }
            else
            {
                Field field = propertyDesc.getAccessorField();
                return new ArrayFieldPropertyGetter(field, index, eventAdapterService);
            }
        }

        return null;
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        InternalEventPropDescriptor descriptor = eventType.getIndexedProperty(propertyNameAtomic);
        if (descriptor != null)
        {
            return descriptor.getReturnType();
        }

        // Check if this is an method returning array which is a type of simple property
        descriptor = eventType.getSimpleProperty(propertyNameAtomic);
        if (descriptor == null)
        {
            return null;
        }

        Class returnType = descriptor.getReturnType();
        if (returnType.isArray())
        {
            return returnType.getComponentType();
        }
        return null;
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        Object type = optionalMapPropTypes.get(propertyNameAtomic);
        if (type == null)
        {
            return null;
        }
        if (type instanceof String) // resolve a property that is a map event type
        {
            String nestedName = type.toString();
            boolean isArray = MapEventType.isPropertyArray(nestedName);
            if (isArray) {
                nestedName = MapEventType.getPropertyRemoveArray(nestedName);
            }
            EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
            if (!(innerType instanceof MapEventType))
            {
                return null;
            }
            if (!isArray)
            {
                return null; // must be declared as an index to use array notation
            }
            else
            {
                return Map[].class;
            }
        }
        else {
            if (!(type instanceof Class))
            {
                return null;
            }
            if (!((Class) type).isArray())
            {
                return null;
            }
            Class componentType = ((Class) type).getComponentType();
            return componentType;            
        }
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        Object type = optionalMapPropTypes.get(propertyNameAtomic);
        if (type == null)
        {
            return null;
        }
        if (type instanceof String) // resolve a property that is a map event type
        {
            String nestedName = type.toString();
            boolean isArray = MapEventType.isPropertyArray(nestedName);
            if (isArray) {
                nestedName = MapEventType.getPropertyRemoveArray(nestedName);
            }
            EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
            if (!(innerType instanceof MapEventType))
            {
                return null;
            }
            if (!isArray)
            {
                return null; // must be declared as an array to use an indexed notation
            }
            else
            {
                return new MapArrayMaptypedUndPropertyGetter(this.propertyNameAtomic, index, eventAdapterService, innerType);
            }
        }
        else {
            if (!(type instanceof Class))
            {
                return null;
            }
            if (!((Class) type).isArray())
            {
                return null;
            }
            Class componentType = ((Class)type).getComponentType();
            // its an array
            return new MapArrayPOJOEntryIndexedPropertyGetter(propertyNameAtomic, index, eventAdapterService, componentType);
        }
    }

    public void toPropertyEPL(StringWriter writer)
    {
        writer.append(propertyNameAtomic);
        writer.append("[");
        writer.append(Integer.toString(index));
        writer.append("]");
    }

    public EventPropertyGetter getGetterDOM(SchemaElementComplex complexProperty, EventAdapterService eventAdapterService)
    {
        for (SchemaElementSimple simple : complexProperty.getSimpleElements())
        {
            if (!simple.isArray())
            {
                continue;
            }
            if (!simple.getName().equals(propertyNameAtomic))
            {
                continue;
            }
            return new DOMIndexedGetter(propertyNameAtomic, index);
        }

        for (SchemaElementComplex complex : complexProperty.getChildren())
        {
            if (!complex.isArray())
            {
                continue;
            }
            if (!complex.getName().equals(propertyNameAtomic))
            {
                continue;
            }
            return new DOMIndexedGetter(propertyNameAtomic, index);
        }

        return null;
    }

    public SchemaItem getPropertyTypeSchema(SchemaElementComplex complexProperty, EventAdapterService eventAdapterService)
    {
        for (SchemaElementSimple simple : complexProperty.getSimpleElements())
        {
            if (!simple.isArray())
            {
                continue;
            }
            if (!simple.getName().equals(propertyNameAtomic))
            {
                continue;
            }
            return simple;
        }

        for (SchemaElementComplex complex : complexProperty.getChildren())
        {
            if (!complex.isArray())
            {
                continue;
            }
            if (!complex.getName().equals(propertyNameAtomic))
            {
                continue;
            }
            return complex;
        }

        return null;
    }
}
