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

    public EventBean getFragment(String property)
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter != null)
        {
            return getter.getFragment(this);
        }
        return null;
    }

    public EventBean[] getFragmentArray(String property)
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter != null)
        {
            return getter.getFragmentArray(this);
        }
        return null;
    }

    public Integer getIndexSize(String property)
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter != null)
        {
            return getter.getIndexSize(this);
        }
        return null;
    }
}
