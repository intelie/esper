package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

import java.util.Map;
import java.io.StringWriter;

/**
 * Represents a dynamic simple property of a given name.
 * <p>
 * Dynamic properties always exist, have an Object type and are resolved to a method during runtime.
 */
public class DynamicSimpleProperty extends PropertyBase implements DynamicProperty
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
        return new DynamicSimplePropertyGetter(propertyNameAtomic);
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