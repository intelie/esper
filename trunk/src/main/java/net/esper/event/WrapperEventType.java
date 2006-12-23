package net.esper.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.client.EPException;

public class WrapperEventType implements EventType 
{
	private final Log log = LogFactory.getLog(WrapperEventType.class);
	
	private final EventType underlyingEventType;
	private final EventType underlyingMapType;
	private final String[] propertyNames;
	
	public WrapperEventType(EventType eventType, Map<String, Class> properties, EventAdapterService eventAdapterService)
	{
		checkForRepeatedPropertyNames(eventType, properties);
		
		this.underlyingEventType = eventType;
		this.underlyingMapType = new MapEventType(properties, eventAdapterService);
		
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
		final EventType underlyingType;
		if(underlyingEventType.isProperty(property))
		{
			underlyingType = underlyingEventType;
		}
		else if (underlyingMapType.isProperty(property))
		{
			underlyingType = underlyingMapType;
		}
		else
		{
			return null;
		}

		return new EventPropertyGetter()
		{
			public Object get(EventBean event)
			{
				// This is expected to be a WrapperEventBean
				if(!(event instanceof WrapperEventBean))
				{
					throw new PropertyAccessException("Mismathched property getter to EventBean type");
				}
				WrapperEventBean wrapper = (WrapperEventBean) event;
				
				// Force the event to return the appropriate underlying object
				if(underlyingType == underlyingMapType)
				{
					wrapper.setUnderlyingIsMap(true);
				}
				else
				{
					wrapper.setUnderlyingIsMap(false);
				}
				// Invoke the appropriate underlying getter
				return underlyingType.getGetter(property).get(event);
			}
		};
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
		return underlyingEventType.getUnderlyingType();
	}

	public boolean isProperty(String property) 
	{
		return underlyingEventType.isProperty(property) || 
			underlyingMapType.isProperty(property);
	}
	
	public boolean equals(Object obj)
	{
		log.debug(".equals");
		
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof WrapperEventType))
        {
        	log.debug(".equals other isn't a WrapperEventType");
            return false;
        }

        WrapperEventType other = (WrapperEventType) obj;
        
        // The underlying event should be the same
        if (!other.underlyingEventType.getUnderlyingType().getName().equals(underlyingEventType.getUnderlyingType().getName()))
        {
        	log.debug(".equals underlying event isn't the same");
        	return false;
        }
        
        // Should have the same number of properties
        if (other.underlyingMapType.getPropertyNames().length != underlyingMapType.getPropertyNames().length)
        {
        	log.debug(".equals number of properties is different");
            return false;
        }

        // Compare property by property
        for (String property : underlyingMapType.getPropertyNames())
        {
        	Class thisType = underlyingMapType.getPropertyType(property);
        	if(!(other.isProperty(property) && other.getPropertyType(property).equals(thisType)))
        	{
        		log.debug(".equals property " + property + "either doesn't exist in the other or is declared with a different type");
        		return false;
        	}
        }

        return true;
	}
	
	public String toString()
	{
		return "WrapperEventType " + 
		"underlyingEventType=" + underlyingEventType + " " +
		"underlyingMapType=" + underlyingMapType;
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
