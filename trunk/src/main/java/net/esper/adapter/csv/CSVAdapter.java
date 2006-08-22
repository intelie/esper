package net.esper.adapter.csv;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

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
	public CSVAdapter(EPRuntime runtime, final EventAdapterService eventAdapterService, final SchedulingService schedulingService, final ScheduleBucket scheduleBucket)
	{
		this.runtime = runtime;
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		this.scheduleBucket = scheduleBucket;
	}
	
	public void play(String eventTypeAlias, String filename)
	{
		CSVPlayer player = createCSVPlayer(eventTypeAlias, filename);
		players.add(player);
		player.start();
	}
	
	public CSVPlayer createCSVPlayer(String eventTypeAlias, String filename)
	{
		CSVAdapterSpec adapterSpec = new CSVAdapterSpec(filename, false, -1);
		Map<String, Class> propertyTypes = constructPropertyTypes(eventTypeAlias);
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, runtime);
		return new CSVPlayer(schedulingService, scheduleBucket, adapterSpec, mapSpec);
	}
	
	private Map<String, Class> constructPropertyTypes(String eventTypeAlias)
	{
		Map<String, Class> propertyTypes = new LinkedHashMap<String, Class>();
		EventType eventType = eventAdapterService.getEventType(eventTypeAlias);
		for(String property : eventType.getPropertyNames())
		{
			Class type = eventType.getPropertyType(property);
			propertyTypes.put(property, type);
		}
		return propertyTypes;
	}
}
