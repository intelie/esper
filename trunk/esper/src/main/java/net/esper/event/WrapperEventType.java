package net.esper.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.client.EPException;
import net.esper.collection.Pair;

public class WrapperEventType implements EventType 
{
	private final Log log = LogFactory.getLog(WrapperEventType.class);
	
	private final EventType underlyingEventType;
	private final MapEventType underlyingMapType;
	private final String[] propertyNames;
    private final int hashCode;
    private final boolean isNoMapProperties;

    public WrapperEventType(EventType eventType, Map<String, Class> properties, EventAdapterService eventAdapterService)
	{
		checkForRepeatedPropertyNames(eventType, properties);
		
		this.underlyingEventType = eventType;
		this.underlyingMapType = new MapEventType(properties, eventAdapterService);
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
	}
	
	public Iterator<EventType> getDeepSuperTypes() 
	{
		return null;
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
                        throw new PropertyAccessException("Mismathched property getter to EventBean type");
                    }
                    WrapperEventBean wrapperEvent = (WrapperEventBean) event;
                    EventBean wrappedEvent = wrapperEvent.getUnderlyingEvent();
                    return underlyingEventType.getGetter(property).get(wrappedEvent);
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
                        throw new PropertyAccessException("Mismathched property getter to EventBean type");
                    }
                    WrapperEventBean wrapperEvent = (WrapperEventBean) event;
                    Map map = wrapperEvent.getUnderlyingMap();
                    return underlyingMapType.getValue(property, map);
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

    private void checkForRepeatedPropertyNames(EventType eventType, Map<String, Class> properties)
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
