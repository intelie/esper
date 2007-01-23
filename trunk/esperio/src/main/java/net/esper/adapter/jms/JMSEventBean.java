package net.esper.adapter.jms;

import net.esper.event.EventType;
import net.esper.event.PropertyAccessException;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.client.EPRuntime;
import net.esper.schedule.ScheduleSlot;
import net.esper.adapter.SendableEvent;

import java.util.Map;
import java.util.HashMap;

/**
 * Created for ESPER.
 */

public class JMSEventBean implements EventBean, SendableEvent 
{
    private EventType eventType;
    private Map<String, Object> properties;
    private final long timestamp;
    private ScheduleSlot scheduleSlot;
  

    protected JMSEventBean(Map<String, Object> properties, EventType eventType, long timestamp, ScheduleSlot scheduleSlot )
    {
        if(scheduleSlot == null)
        {
            throw new NullPointerException("ScheduleSlot cannot be null");
        }

        this.properties = new HashMap<String, Object>();
        this.properties.putAll(properties);
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.scheduleSlot = scheduleSlot;
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

    /* (non-Javadoc)
     * @see net.esper.adapter.SendableEvent#send(net.esper.client.EPRuntime)
     */
    public void send(EPRuntime runtime)
    {
        runtime.sendEvent(this);
    }

    /* (non-Javadoc)
     * @see net.esper.adapter.SendableEvent#getScheduleSlot()
     */
    public ScheduleSlot getScheduleSlot()
    {
        return scheduleSlot;
    }

    /* (non-Javadoc)
     * @see net.esper.adapter.SendableEvent#getSendTime()
     */
    public long getSendTime()
    {
        return timestamp;
    }

    public String toString()
    {
        return properties.toString();
    }

}

