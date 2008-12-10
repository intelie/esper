package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.client.PropertyAccessException;

import java.util.Properties;
import java.util.Iterator;
import java.util.Set;

public class MyPlugInPropertiesEventType implements EventType
{
    private final String name;
    private final Set<String> properties;

    public MyPlugInPropertiesEventType(String name, Set<String> properties)
    {
        this.name = name;
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

            public Object getFragment(EventBean eventBean)
            {
                return null; // TODO
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

    public String getName()
    {
        return name;
    }

    public EventPropertyDescriptor[] getPropertyDescriptors()
    {
        return new EventPropertyDescriptor[0];  // TODO
    }

    public EventTypeFragment getFragmentType(String property)
    {
        return null;  // TODO
    }
}
