package net.esper.event;

import java.util.Map;
import java.util.HashMap;

/**
 * Wrapper for events represented by a Map of key-value pairs that are the event properties.
 * MapEventBean instances are equal if they have the same {@link EventType} and all property names
 * and values are reference-equal. 
 */
class MapEventBean implements EventBean
{
    private EventType eventType;
    private Map<String, Object> properties;
    private Integer hashCode = null;

    /**
     * Constructor for initialization with existing values.
     * Makes a shallow copy of the supplied values to not be surprised by changing property values.
     * @param properties are the event property values
     * @param eventType is the type of the event, i.e. describes the map entries
     */
    protected MapEventBean(Map<String, Object> properties, EventType eventType)
    {
        this.properties = new HashMap<String, Object>();
        this.properties.putAll(properties);
        this.eventType = eventType;
    }

    /**
     * Constructor for initialization with existing values.
     * Makes a shallow copy of the supplied values to not be surprised by changing property values.
     * @param eventType is the type of the event, i.e. describes the map entries
     * @param events are the event property constisting of events
     */
    protected MapEventBean(EventType eventType, Map<String, EventBean> events)
    {
        this.properties = new HashMap<String, Object>();
        for (Map.Entry<String, EventBean> entry : events.entrySet())
        {
            properties.put(entry.getKey(), entry.getValue().getUnderlying());
        }
        this.eventType = eventType;
    }

    /**
     * Constructor for the mutable functions, e.g. only the type of values is known but not the actual values.
     * @param eventType is the type of the event, i.e. describes the map entries
     */
    public MapEventBean(EventType eventType)
    {
        this.properties = new HashMap<String, Object>();
        this.eventType = eventType;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Object get(String property) throws IllegalArgumentException, PropertyAccessException
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

        final MapEventBean other = (MapEventBean) otherObject;

        if (other.getEventType() != eventType)
        {
            return false;
        }

        if (properties.size() != other.properties.size())
        {
            return false;
        }

        // Compare entry by entry
        for (Map.Entry<String, Object> entry : properties.entrySet())
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

        return true;
    }

    public int hashCode()
    {
        if (hashCode == null)
        {
            int hashCodeVal = 0;
            for (Map.Entry<String, Object> entry : properties.entrySet())
            {
                final String name = entry.getKey();
                final Object value = entry.getValue();

                if (value != null)
                {
                    hashCodeVal = hashCodeVal ^ name.hashCode() ^ value.hashCode();
                }
            }
            hashCode = hashCodeVal;
        }
        return hashCode; 
    }

    public String toString()
    {
        return "MapEventBean " +
                "eventType=" + eventType;
    }
}
