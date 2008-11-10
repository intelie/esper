package com.espertech.esper.regression.event;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

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
        return getter.get(this);
    }

    public Object getUnderlying()
    {
        return properties;
    }

    protected Properties getProperties()
    {
        return properties;
    }    
}
