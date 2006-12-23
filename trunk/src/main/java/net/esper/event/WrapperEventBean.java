package net.esper.event;

import java.util.Map;

public class WrapperEventBean implements EventBean {

	private final Object event;
	private final Map map;
	private final EventType eventType;
	private boolean underlyingIsMap = false;
	
	public WrapperEventBean(Object event, Map<String, Object> properties, EventType eventType)
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
		if(underlyingIsMap)
		{
			return map;
		}
		return event;
	}
	
	/**
	 * Determine whether getUnderlying returns the event or
	 * the map of additional properties.
	 * @param underlyingIsMap - true if the next invocation of 
	 * getUnderlying should return the map of additional properties
	 * instead of the EventBean itself.
	 */
	public void setUnderlyingIsMap(boolean underlyingIsMap)
	{
		this.underlyingIsMap = underlyingIsMap;
	}
	
	public String toString()
	{
        return "WrapperEventBean " +
        "[event=" + event + "] " + 
        "[properties=" + map + "]";
	}
	

}
