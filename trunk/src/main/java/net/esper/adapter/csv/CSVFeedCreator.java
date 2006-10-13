package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.Feed;
import net.esper.adapter.FeedCreator;
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
public class CSVFeedCreator implements FeedCreator
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
	public CSVFeedCreator(EPRuntime runtime,
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

		String eventTypeAlias = (String)feedSpec.getParameter("eventTypeAlias");
		Map<String, Class> propertyTypes = constructPropertyTypes(eventTypeAlias);
		MapEventSpec mapSpec = new MapEventSpec(eventTypeAlias, propertyTypes, runtime);
		return new CSVFeed(feedSpec, mapSpec, schedulingService, scheduleBucket.allocateSlot());
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

	private boolean isDefault(int eventsPerSec)
	{
		return eventsPerSec == -1;
	}

	private boolean isValid(int eventsPerSec)
	{
		return 1 <= eventsPerSec && eventsPerSec <= 1000;
	}

	private Map<String, Class> constructPropertyTypes(String eventTypeAlias) 
	{
		Map<String, Class> propertyTypes = new HashMap<String, Class>();
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
