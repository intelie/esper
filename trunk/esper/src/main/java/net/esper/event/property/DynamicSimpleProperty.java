package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.BeanEventType;

/**
 * Represents a dynamic simple property of a given name.
 * <p>
 * Dynamic properties always exist, have an Object type and are resolved to a method during runtime.
 */
public class DynamicSimpleProperty extends PropertyBase
{
    /**
     * Ctor.
     * @param propertyName is the property name
     */
    public DynamicSimpleProperty(String propertyName)
    {
        super(propertyName);
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        return new DynamicSimplePropertyGetter(propertyName);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        return Object.class;
    }
}
