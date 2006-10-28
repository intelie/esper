package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedFactory;
import net.esper.adapter.FeedSpec;
import net.esper.adapter.FeedType;
import net.esper.adapter.MapEventSpec;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * A utility for creating CSVFeeds.
 */
public class CSVFeedFactory implements FeedFactory
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
	public CSVFeedFactory(EPRuntime runtime,
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
	 * @see net.esper.adapter.csv.FeedCreator#createFeed(net.esper.adapter.csv.CSVFeedSpec)
	 */
	public Feed createFeed(FeedSpec feedSpec) throws EPException
	{
		checkFeedType(feedSpec);
		checkAdapterInputSource(feedSpec);
		checkEventTypeAlias(feedSpec);
		checkEventsPerSec(feedSpec);
		checkInputSource(feedSpec);

		String eventTypeAlias = (String)feedSpec.getParameter("eventTypeAlias");
		Map<String, Class> propertyTypes = constructPropertyTypes(eventTypeAlias, (Map)feedSpec.getParameter("propertyTypes"));
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, runtime);
		return new CSVFeed(feedSpec, mapSpec, eventAdapterService, schedulingService, scheduleBucket.allocateSlot());
	}
	
	private void checkFeedType(FeedSpec feedSpec)
	{
		if(feedSpec.getFeedType() != FeedType.CSV)
		{
			throw new IllegalArgumentException("Invalid FeedType: " + feedSpec.getFeedType());
		}
	}
	
	private void checkEventTypeAlias(FeedSpec feedSpec)
	{
		if(feedSpec.getParameter("eventTypeAlias") == null)
		{
			throw new NullPointerException("eventTypeAlias cannot be null");
		}
	}
	
	private void checkAdapterInputSource(FeedSpec feedSpec)
	{
		if(feedSpec.getParameter("adapterInputSource") == null)
		{
			throw new NullPointerException("adapterInputSource cannot be null");
		}
	}
	
	private void checkEventsPerSec(FeedSpec feedSpec)
	{
		if(feedSpec.getParameter("eventsPerSec") == null)
		{
			return;
		}
		int eventsPerSec = (Integer)feedSpec.getParameter("eventsPerSec");
		if(!(isDefault(eventsPerSec) || isValid(eventsPerSec)))
		{
			throw new IllegalArgumentException("Illegal value for eventsPerSec: " + eventsPerSec);
		}
	}
	
	private void checkInputSource(FeedSpec feedSpec)
	{
		AdapterInputSource source = (AdapterInputSource)feedSpec.getParameter("adapterInputSource");
		if(source == null)
		{
			throw new NullPointerException("adapterInputSource cannot be null");
		}
		boolean looping = feedSpec.getParameter("looping") != null ? (Boolean)feedSpec.getParameter("looping") : false;
		if(looping && !source.isRenewable())
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
