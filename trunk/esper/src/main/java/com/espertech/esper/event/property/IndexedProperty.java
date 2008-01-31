package com.espertech.esper.event.property;

import com.espertech.esper.event.*;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

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

    /**
     * Returns index for indexed access.
     * @return index value
     */
    public int getIndex()
    {
        return index;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        FastClass fastClass = eventType.getFastClass();
        EventPropertyDescriptor propertyDesc = eventType.getIndexedProperty(propertyName);
        if (propertyDesc != null)
        {
            if (fastClass != null)
            {
                Method method = propertyDesc.getReadMethod();
                FastMethod fastMethod = fastClass.getMethod(method);
                return new KeyedFastPropertyGetter(fastMethod, index);
            }
            else
            {
                return new KeyedMethodPropertyGetter(propertyDesc.getReadMethod(), index);
            }
        }

        // Try the array as a simple property
        propertyDesc = eventType.getSimpleProperty(propertyName);
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
                    return new ArrayFastPropertyGetter(fastMethod, index);
                }
                else
                {
                    return new ArrayMethodPropertyGetter(method, index);   
                }
            }
            else
            {
                Field field = propertyDesc.getAccessorField();
                return new ArrayFieldPropertyGetter(field, index);
            }
        }

        return null;
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        EventPropertyDescriptor descriptor = eventType.getIndexedProperty(propertyName);
        if (descriptor != null)
        {
            return descriptor.getReturnType();
        }

        // Check if this is an method returning array which is a type of simple property
        descriptor = eventType.getSimpleProperty(propertyName);
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

    public Class getPropertyTypeMap()
    {
        return null;  // indexed properties are not allowed in non-dynamic form in a map
    }

    public EventPropertyGetter getGetterMap()
    {
        return null;  // indexed properties are not allowed in non-dynamic form in a map
    }
}
