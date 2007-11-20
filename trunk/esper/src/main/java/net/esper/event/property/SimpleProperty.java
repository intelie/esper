package net.esper.event.property;

import net.esper.event.*;

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
        EventPropertyDescriptor propertyDesc = eventType.getSimpleProperty(propertyName);
        if (propertyDesc == null)
        {
            return null;
        }
        if (!propertyDesc.getPropertyType().equals(EventPropertyType.SIMPLE))
        {
            return null;
        }
        return eventType.getGetter(propertyName);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getSimpleProperty(propertyName);
        if (propertyDesc == null)
        {
            return null;
        }
        return eventType.getPropertyType(propertyName);
    }

    public Class getPropertyTypeMap()
    {
        return null;
    }

    public EventPropertyGetter getGetterMap()
    {
        return null;
    }
}
