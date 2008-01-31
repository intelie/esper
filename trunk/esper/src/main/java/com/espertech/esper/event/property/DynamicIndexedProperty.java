package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.BeanEventType;

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
        return new DynamicIndexedPropertyGetter(propertyName, index);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        return Object.class;
    }

    public Class getPropertyTypeMap()
    {
        return Object.class;
    }

    public EventPropertyGetter getGetterMap()
    {
        return new MapIndexedPropertyGetter(this.getPropertyName(), index);
    }
}
