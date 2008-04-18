package com.espertech.esper.regression.event;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

import java.util.Properties;
import java.util.Iterator;
import java.util.Set;

public class MyPlugInPropertiesEventType implements EventType
{
    private final Set<String> properties;

    public MyPlugInPropertiesEventType(Set<String> properties)
    {
        this.properties = properties;
    }

    public Class getPropertyType(String property)
    {
        if (!isProperty(property))
        {
            return null;
        }
        return String.class;
    }

    public Class getUnderlyingType()
    {
        return Properties.class;
    }

    public EventPropertyGetter getGetter(String property)
    {
        final String propertyName = property;
        return new EventPropertyGetter() {

            public Object get(EventBean eventBean) throws PropertyAccessException
            {
                MyPlugInPropertiesEventBean propBean = (MyPlugInPropertiesEventBean) eventBean;
                return propBean.getProperties().getProperty(propertyName);
            }

            public boolean isExistsProperty(EventBean eventBean)
            {
                MyPlugInPropertiesEventBean propBean = (MyPlugInPropertiesEventBean) eventBean;
                return propBean.getProperties().getProperty(propertyName) != null;
            }
        };
    }

    public String[] getPropertyNames()
    {
        return properties.toArray(new String[properties.size()]);
    }

    public boolean isProperty(String property)
    {
        return properties.contains(property);
    }

    public EventType[] getSuperTypes()
    {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }
}
