package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.collection.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An event type that adds zero or more fields to an existing event type.
 * <p>
 * The additional fields are represented as a Map. Any queries to event properties are first
 * held against the additional fields, and secondly are handed through to the underlying event.
 * <p>
 * If this event type is to add information to another wrapper event type (wrapper to wrapper), then it is the
 * responsibility of the creating logic to use the existing event type and add to it.
 * <p>
 * Uses a the map event type {@link com.espertech.esper.event.MapEventType} to represent the mapped properties. This is because the additional properties
 * can also be beans or complex types and the Map event type handles these nicely.
 */
public class WrapperEventType implements EventType
{
    /**
     * The underlying wrapped event type.
     */
    protected final EventType underlyingEventType;

    /**
     * The map event type that provides the additional properties.
     */
    protected final MapEventType underlyingMapType;
    
    private final String[] propertyNames;
    private final int hashCode;
    private final boolean isNoMapProperties;
    private final String typeName;

    /**
     * Ctor.
     * @param typeName is the event type alias name 
     * @param eventType is the event type of the wrapped events
     * @param properties is the additional properties this wrapper adds
     * @param eventAdapterService is the ser
     */
    public WrapperEventType(String typeName, EventType eventType, Map<String, Object> properties, EventAdapterService eventAdapterService)
	{
		checkForRepeatedPropertyNames(eventType, properties);
		
		this.underlyingEventType = eventType;
		this.underlyingMapType = new MapEventType(typeName, eventAdapterService, properties);
        this.hashCode = underlyingMapType.hashCode() ^ underlyingEventType.hashCode();
        this.isNoMapProperties = properties.isEmpty();

        List<String> propertyNames = new ArrayList<String>();
		for(String eventProperty : underlyingEventType.getPropertyNames())
		{
			propertyNames.add(eventProperty);
		}
		for(String mapProperty : underlyingMapType.getPropertyNames())
		{
			propertyNames.add(mapProperty);
		}
		this.propertyNames = propertyNames.toArray(new String[0]);
        this.typeName = typeName;
    }
	
	public Iterator<EventType> getDeepSuperTypes() 
	{
		return null;
	}

    /**
     * Returns the event type alias.
     * @return event type alias
     */
    public String getAlias()
    {
        return typeName;
    }

    public EventPropertyGetter getGetter(final String property)
	{
		if(underlyingEventType.isProperty(property))
		{
            return new EventPropertyGetter()
            {
                public Object get(EventBean event)
                {
                    if(!(event instanceof WrapperEventBean))
                    {
                        throw new PropertyAccessException("Mismatched property getter to EventBean type");
                    }
                    WrapperEventBean wrapperEvent = (WrapperEventBean) event;
                    EventBean wrappedEvent = wrapperEvent.getUnderlyingEvent();
                    EventPropertyGetter underlyingGetter = underlyingEventType.getGetter(property);
                    return underlyingGetter.get(wrappedEvent);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true; // Property exists as the property is not dynamic (unchecked)
                }
            };
		}
		else if (underlyingMapType.isProperty(property))
		{
            return new EventPropertyGetter()
            {
                public Object get(EventBean event)
                {
                    if(!(event instanceof WrapperEventBean))
                    {
                        throw new PropertyAccessException("Mismatched property getter to EventBean type");
                    }
                    WrapperEventBean wrapperEvent = (WrapperEventBean) event;
                    Map map = wrapperEvent.getUnderlyingMap();
                    return underlyingMapType.getValue(property, map);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true; // Property exists as the property is not dynamic (unchecked)
                }
            };
		}
		else
		{
			return null;
		}
	}

	public String[] getPropertyNames() 
	{
		return propertyNames;
	}

	public Class getPropertyType(String property) 
	{
		if(underlyingEventType.isProperty(property))
		{
			return underlyingEventType.getPropertyType(property);
		}
		else if (underlyingMapType.isProperty(property))
		{
			return underlyingMapType.getPropertyType(property);
		}
		else
		{
			return null;
		}
	}

	public EventType[] getSuperTypes() 
	{
		return null;
	}

	public Class getUnderlyingType() 
	{
        // If the additional properties are empty, such as when wrapping a native event by means of wildcard-only select
        // then the underlying type is simply the wrapped type.
        if (isNoMapProperties)
        {
            return underlyingEventType.getUnderlyingType();
        }
        else
        {
            return Pair.class;
        }
    }

    /**
     * Returns the wrapped event type.
     * @return wrapped type
     */
    public EventType getUnderlyingEventType()
    {
        return underlyingEventType;
    }

    /**
     * Returns the map type.
     * @return map type providing additional properties.
     */
    public MapEventType getUnderlyingMapType()
    {
        return underlyingMapType;
    }

    public boolean isProperty(String property)
	{
		return underlyingEventType.isProperty(property) || 
			underlyingMapType.isProperty(property);
	}
		
	public String toString()
	{
		return "WrapperEventType " + 
		"underlyingEventType=" + underlyingEventType + " " +
		"underlyingMapType=" + underlyingMapType;
	}

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof WrapperEventType))
        {
            return false;
        }

        WrapperEventType other = (WrapperEventType) obj;

        if ((other.underlyingEventType.equals(this.underlyingEventType)) &&
            (other.underlyingMapType.equals(this.underlyingMapType)))                
        {
            return true;
        }
        return false;
    }

    public int hashCode()
    {
        return hashCode;
    }

    private void checkForRepeatedPropertyNames(EventType eventType, Map<String, Object> properties)
	{
		for(String property : eventType.getPropertyNames())
		{
			if(properties.keySet().contains(property))
			{
				throw new EPException("Property " + property + " occurs in both the underlying event and in the additional properties");
			}
		}
	}    
}
