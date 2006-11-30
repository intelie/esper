package net.esper.adapter.jms;

import net.esper.event.EventType;
import net.esper.event.PropertyAccessException;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 26, 2006
 * Time: 1:44:56 PM
 * To change this template use File | Settings | File Templates.
 */

public class JMSEventBean implements EventBean
{
    private EventType eventType;
    private Map<String, Object> properties;


    protected JMSEventBean(Map<String, Object> properties, EventType eventType)
    {
        this.properties = new HashMap<String, Object>();
        this.properties.putAll(properties);
        this.eventType = eventType;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object get(String property) throws PropertyAccessException
    {
        EventPropertyGetter getter = eventType.getGetter(property);
        if (getter == null)
        {
            throw new IllegalArgumentException("Property named '" + property + "' is not a valid property name for this type");
        }
        return eventType.getGetter(property).get(this);
    }

    public Object getUnderlying()
    {
        return properties;
    }

}

