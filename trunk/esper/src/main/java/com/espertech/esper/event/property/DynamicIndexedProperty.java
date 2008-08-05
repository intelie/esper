package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.BeanEventType;

import java.util.Map;
import java.io.StringWriter;

/**
 * Represents a dynamic indexed property of a given name.
 * <p>
 * Dynamic properties always exist, have an Object type and are resolved to a method during runtime.
 */
public class DynamicIndexedProperty extends PropertyBase implements DynamicProperty
{
    private final int index;

    /**
     * Ctor.
     * @param propertyName is the property name
     * @param index is the index of the array or indexed property
     */
    public DynamicIndexedProperty(String propertyName, int index)
    {
        super(propertyName);
        this.index = index;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        return new DynamicIndexedPropertyGetter(propertyNameAtomic, index);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        return Object.class;
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes)
    {
        return Object.class;
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes)
    {
        return new MapIndexedPropertyGetter(this.getPropertyNameAtomic(), index);
    }

    public void toPropertyEPL(StringWriter writer)
    {
        writer.append(propertyNameAtomic);
        writer.append('[');
        writer.append(Integer.toString(index));
        writer.append(']');
        writer.append('?');
    }
}