package com.espertech.esper.regression.event;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;

import java.util.Properties;

public class MyPlugInPropertiesEventBean implements EventBean
{
    private final MyPlugInPropertiesEventType eventType;
    private final Properties properties;

    public MyPlugInPropertiesEventBean(MyPlugInPropertiesEventType eventType, Properties properties)
    {
        this.eventType = eventType;
        this.properties = properties;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter != null)
        {
            return getter.get(this);
        }
        return null;
    }

    public Object getUnderlying()
    {
        return properties;
    }

    protected Properties getProperties()
    {
        return properties;
    }

    public Object getFragment(String property)
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter != null)
        {
            return getter.getFragment(this);
        }
        return null;
    }
}
