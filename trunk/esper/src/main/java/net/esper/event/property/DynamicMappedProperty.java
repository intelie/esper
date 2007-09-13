package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.BeanEventType;

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
        return new DynamicMappedPropertyGetter(propertyName, key);
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
        return new MapMappedPropertyGetter(this.getPropertyName(), key);
    }
}
