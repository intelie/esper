package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.client.PropertyAccessException;

import java.util.*;

public class MyPlugInPropertiesEventType implements EventType
{
    private final String name;
    private final Set<String> properties;
    private final Map<String, EventPropertyDescriptor> descriptors;

    public MyPlugInPropertiesEventType(String name, Set<String> properties, Map<String, EventPropertyDescriptor> descriptors)
    {
        this.name = name;
        this.properties = properties;
        this.descriptors = descriptors;
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
                return null;
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
        Collection<EventPropertyDescriptor> descriptorColl = descriptors.values();
        return descriptorColl.toArray(new EventPropertyDescriptor[descriptors.size()]);
    }

    public EventPropertyDescriptor getPropertyDescriptor(String propertyName)
    {
        return descriptors.get(propertyName);
    }

    public FragmentEventType getFragmentType(String property)
    {
        return null;  // sample does not provide any fragments
    }

    public EventPropertyGetterMapped getGetterMapped(String mappedProperty) {
        return null;    // sample does not provide a getter for mapped properties
    }

    public EventPropertyGetterIndexed getGetterIndexed(String indexedProperty) {
        return null;    // sample does not provide a getter for indexed properties
    }
}
