package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.InputAdapter;
import net.esper.adapter.AdapterFactory;
import net.esper.adapter.AdapterSpec;
import net.esper.adapter.AdapterType;
import net.esper.adapter.MapEventSpec;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * A factory for creating CSVAdapters.
 */
public class CSVAdapterFactory implements AdapterFactory
{
	private final EPRuntime runtime;
	private final EventAdapterService eventAdapterService;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	
	/**
	 * Ctor.
	 * @param runtime - the EPRuntime to send events into
	 * @param eventAdapterService - used for resolving event type aliases
	 * @param schedulingService - used for making callbacks
	 * @param scheduleBucket - the scheduling bucket that all adapters use
	 */
	public CSVAdapterFactory(EPRuntime runtime,
					  	  EventAdapterService eventAdapterService, 
					  	  SchedulingService schedulingService, 
					  	  ScheduleBucket scheduleBucket)
	{
		this.runtime = runtime;
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		this.scheduleBucket = scheduleBucket;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.csv.AdapterFactory#createAdapter(net.esper.adapter.csv.CSVAdapterSpec)
	 */
	public InputAdapter createAdapter(AdapterSpec adapterSpec) throws EPException
	{
		checkAdapterType(adapterSpec);
		checkAdapterInputSource(adapterSpec);
		checkEventTypeAlias(adapterSpec);
		checkEventsPerSec(adapterSpec);
		checkInputSource(adapterSpec);

		String eventTypeAlias = (String)adapterSpec.getParameter("eventTypeAlias");
		Map<String, Class> propertyTypes = constructPropertyTypes(eventTypeAlias, (Map)adapterSpec.getParameter("propertyTypes"));
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, runtime);
		return new CSVInputAdapter(adapterSpec, mapSpec, eventAdapterService, schedulingService, scheduleBucket.allocateSlot());
	}
	
	private void checkAdapterType(AdapterSpec adapterSpec)
	{
		if(adapterSpec.getAdapterType() != AdapterType.CSV)
		{
			throw new IllegalArgumentException("Invalid AdapterType: " + adapterSpec.getAdapterType());
		}
	}
	
	private void checkEventTypeAlias(AdapterSpec adapterSpec)
	{
		if(adapterSpec.getParameter("eventTypeAlias") == null)
		{
			throw new NullPointerException("eventTypeAlias cannot be null");
		}
	}
	
	private void checkAdapterInputSource(AdapterSpec adapterSpec)
	{
		if(adapterSpec.getParameter("adapterInputSource") == null)
		{
			throw new NullPointerException("adapterInputSource cannot be null");
		}
	}
	
	private void checkEventsPerSec(AdapterSpec adapterSpec)
	{
		if(adapterSpec.getParameter("eventsPerSec") == null)
		{
			return;
		}
		int eventsPerSec = (Integer)adapterSpec.getParameter("eventsPerSec");
		if(!(isDefault(eventsPerSec) || isValid(eventsPerSec)))
		{
			throw new IllegalArgumentException("Illegal value for eventsPerSec: " + eventsPerSec);
		}
	}
	
	private void checkInputSource(AdapterSpec adapterSpec)
	{
		AdapterInputSource source = (AdapterInputSource)adapterSpec.getParameter("adapterInputSource");
		if(source == null)
		{
			throw new NullPointerException("adapterInputSource cannot be null");
		}
		boolean looping = adapterSpec.getParameter("looping") != null ? (Boolean)adapterSpec.getParameter("looping") : false;
		if(looping && !source.isResettable())
		{
			throw new EPException("Cannot create a CSV adapter that loops from an input source that is not renewable");
		}
	}

	private boolean isDefault(int eventsPerSec)
	{
		return eventsPerSec == -1;
	}

	private boolean isValid(int eventsPerSec)
	{
		return 1 <= eventsPerSec && eventsPerSec <= 1000;
	}

	private Map<String, Class> constructPropertyTypes(String eventTypeAlias, Map<String, Class> propertyTypesGiven) 
	{
		Map<String, Class> propertyTypes = new HashMap<String, Class>();
		EventType eventType = eventAdapterService.getEventType(eventTypeAlias);
		if(eventType == null)
		{
			if(propertyTypesGiven != null)
			{
				eventAdapterService.addMapType(eventTypeAlias, propertyTypesGiven);
			}
			return propertyTypesGiven;
		}
		if(!eventType.getUnderlyingType().equals(Map.class))
		{
			throw new EPException("Alias " + eventTypeAlias + " does not correspond to a map event");
		}
		if(propertyTypesGiven != null && eventType.getPropertyNames().length != propertyTypesGiven.size())
		{
			throw new EPException("Event type " + eventTypeAlias + " has already been declared with a different number of parameters");
		}
		for(String property : eventType.getPropertyNames())
		{
			Class type = eventType.getPropertyType(property);
			if(propertyTypesGiven != null && propertyTypesGiven.get(property) == null)
			{
				throw new EPException("Event type " + eventTypeAlias + "has already been declared with different parameters");
			}
			if(propertyTypesGiven != null && !propertyTypesGiven.get(property).equals(type))
			{
				throw new EPException("Event type " + eventTypeAlias + "has already been declared with a different type for property " + property);
			}
			propertyTypes.put(property, type);
		}
		return propertyTypes;
	}
}
