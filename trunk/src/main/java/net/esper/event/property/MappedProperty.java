package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.BeanEventType;
import net.esper.event.EventPropertyDescriptor;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;

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
        FastClass fastClass = eventType.getFastClass();
        EventPropertyDescriptor propertyDesc = eventType.getMappedProperty(propertyName);
        if (propertyDesc == null)
        {
            // property not found, is not a property
            return null;
        }
        Method method = propertyDesc.getReadMethod();
        FastMethod fastMethod = fastClass.getMethod(method);

        return new EventKeyedPropertyGetter(fastMethod, key);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        EventPropertyDescriptor propertyDesc = eventType.getMappedProperty(propertyName);
        if (propertyDesc == null)
        {
            // property not found, is not a property
            return null;
        }
        return propertyDesc.getReadMethod().getReturnType();
    }
}
