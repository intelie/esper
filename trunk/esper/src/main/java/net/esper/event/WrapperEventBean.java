package net.esper.event;

import net.esper.collection.Pair;

import java.util.Map;

/**
 * Event bean that wraps another event bean adding additional properties.
 * <p>
 * This can be useful for classes for which the statement adds derived values retaining the original class.
 * <p>
 * The event type of such events is always {@link WrapperEventType}. Additional properties are stored in a
 * Map.
 */
public class WrapperEventBean implements EventBean {

	private final EventBean event;
	private final Map<String, Object> map;
	private final EventType eventType;
    private Integer hashCode;

    /**
     * Ctor.
     * @param event is the wrapped event
     * @param properties is zero or more property values that embellish the wrapped event
     * @param eventType is the {@link WrapperEventType}.
     */
    public WrapperEventBean(EventBean event, Map<String, Object> properties, EventType eventType)
	{
		this.event = event;
		this.map = properties;		
		this.eventType = eventType;
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

	public EventType getEventType() 
	{
		return eventType;
	}

	public Object getUnderlying() 
	{
        // If wrapper is simply for the underlyingg with no additional properties, then return the underlying type 
        if (map.isEmpty())
        {
            return event.getUnderlying();
        }
        else
        {
            return new Pair<Object, Map>(event.getUnderlying(), map);
        }
    }

    /**
     * Returns the underlying map storing the additional properties, if any.
     * @return event property map
     */
    public Map getUnderlyingMap()
    {
        return map;
    }

    /**
     * Returns the wrapped event.
     * @return wrapped event
     */
    public EventBean getUnderlyingEvent()
    {
        return event;
    }

    public String toString()
	{
        return "WrapperEventBean " +
        "[event=" + event + "] " + 
        "[properties=" + map + "]";
	}

    public boolean equals(final Object otherObject)
    {
        if (otherObject == this)
        {
            return true;
        }

        if (otherObject == null)
        {
            return false;
        }

        if (getClass() != otherObject.getClass())
        {
            return false;
        }

        final WrapperEventBean other = (WrapperEventBean) otherObject;

        if (other.eventType != eventType)
        {
            return false;
        }

        if (map.size() != other.map.size())
        {
            return false;
        }

        // Compare entry by entry
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            final String name = entry.getKey();
            final Object value = entry.getValue();
            final Object otherValue = other.get(name);

            if ((otherValue == null) && (value == null))
            {
                continue;
            }

            if ((otherValue == null) && (value != null))
            {
                return false;
            }

            if (!otherValue.equals(value))
            {
                return false;
            }
        }

        return other.event.equals(this.event);
    }

    public int hashCode()
    {
        if (hashCode == null)
        {
            int hashCodeVal = 0;
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                final String name = entry.getKey();
                final Object value = entry.getValue();

                if (value != null)
                {
                    hashCodeVal = hashCodeVal ^ name.hashCode() ^ value.hashCode();
                }
            }
            hashCode = hashCodeVal ^ event.hashCode();
        }
        return hashCode;
    }    
}
