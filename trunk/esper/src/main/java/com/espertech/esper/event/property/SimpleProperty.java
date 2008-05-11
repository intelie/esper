package com.espertech.esper.event.property;

import com.espertech.esper.event.*;

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

    public Class getPropertyTypeMap(Map optionalMapPropTypes)
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
        if (def instanceof Map)
        {
            return Map.class;
        }
        String message = "Nestable map type configuration encountered an unexpected value type of '"
            + def.getClass() + " for property '" + propertyNameAtomic + "', expected Map or Class";
        throw new PropertyAccessException(message);
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes)
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
