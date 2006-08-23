package net.esper.adapter.csv;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * A class that manages running instances of input adapters for
 * CSV files.
 */
public class CSVAdapter
{
	private final EPRuntime runtime;
	private final EventAdapterService eventAdapterService;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	private final Set<CSVPlayer> players = new HashSet<CSVPlayer>();
	
	
	/**
	 * Ctor.
	 * @param runtime - the EPRuntime to send events into
	 * @param eventAdapterService - used for resolving event type aliases
	 * @param schedulingService - used for making callbacks
	 * @param scheduleBucket - the scheduling bucket that all adapters use
	 */
	public CSVAdapter(EPRuntime runtime,
					  EventAdapterService eventAdapterService, 
					  SchedulingService schedulingService, 
					  ScheduleBucket scheduleBucket)
	{
		this.runtime = runtime;
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		this.scheduleBucket = scheduleBucket;
	}
	
	/**
	 * Create and start a CSVPlayer.
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @param filename - the path to the CSV file
	 * @throws EPException if eventTypeAlias does not correspond to a map event
	 */
	public void play(String eventTypeAlias, String filename) throws EPException
	{
		CSVPlayer player = createCSVPlayer(eventTypeAlias, filename);
		players.add(player);
		player.start();
	}
	
	/**
	 * Create a CSVPlayer.
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @param filename - the path to the CSV file
	 * @return the created CSVPlayer
	 * 	 * @throws EPException if the eventTypeAlias does not correspond to a map event
	 */
	public CSVPlayer createCSVPlayer(String eventTypeAlias, String filename) throws EPException
	{
		Map<String, Class> propertyTypes = constructPropertyTypes(eventTypeAlias);
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, runtime);
		return new CSVPlayer(filename, mapSpec, schedulingService, scheduleBucket.allocateSlot());
	}
	
	private Map<String, Class> constructPropertyTypes(String eventTypeAlias) 
	{
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		EventType eventType = eventAdapterService.getEventType(eventTypeAlias);
		if(!eventType.getUnderlyingType().equals(Map.class))
		{
			throw new EPException("Alias " + eventTypeAlias + " does not correspond to a map event");
		}
		for(String property : eventType.getPropertyNames())
		{
			Class type = eventType.getPropertyType(property);
			propertyTypes.put(property, type);
		}
		return propertyTypes;
	}
}
