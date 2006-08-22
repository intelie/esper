package net.esper.adapter.csv;

import java.util.Map;

import net.esper.client.EPRuntime;

/** 
 * A wrapper for the information needed to send a Map object
 * into an EPRuntime.
 */
public class MapEventSpec
{
	private final String eventTypeAlias;
	private final Map<String, Class> propertyTypes;
	private final EPRuntime epRuntime;
	
	/**
	 * Ctor.
	 * @param eventTypeAlias - the event type generated by the Map object in the EPRuntime
	 * @param propertyTypeNames - for each key-value pair in the map, the name of the key and the type of the value
	 * @param epRuntime - the EPRuntime to send the Map objects into
	 * @throws ClassNotFoundException 
	 */
	protected MapEventSpec(String eventTypeAlias, Map<String, Class> propertyTypes, EPRuntime epRuntime) 
	{
		this.eventTypeAlias = eventTypeAlias;
		this.propertyTypes = propertyTypes;
		this.epRuntime = epRuntime;
	}

	/**
	 * @return the epRuntime
	 */
	protected EPRuntime getEpRuntime()
	{
		return epRuntime;
	}

	/**
	 * @return the eventTypeAlias
	 */
	protected String getEventTypeAlias()
	{
		return eventTypeAlias;
	}

	/**
	 * @return the propertyTypes
	 */
	protected Map<String, Class> getPropertyTypes()
	{
		return propertyTypes;
	}

}
