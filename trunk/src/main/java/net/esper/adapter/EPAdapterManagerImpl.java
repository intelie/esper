package net.esper.adapter;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVAdapterImpl;
import net.esper.adapter.csv.CSVFeedFactory;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * An implementation of EPAdapterManager
 */
public class EPAdapterManagerImpl implements EPAdapterManager
{
	private final EPRuntime runtime;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	private final Map<FeedType, FeedFactory> feedFactories = new HashMap<FeedType, FeedFactory>();
	private final CSVAdapter csvAdapter;
	
	/**
	 * Ctor.
	 * @param runtime - the EPRuntime to send events into
	 * @param eventAdapterService - for resolving event type aliases
	 * @param schedulingService - for scheduling callbacks
	 */
	public EPAdapterManagerImpl(EPRuntime runtime, final EventAdapterService eventAdapterService, final SchedulingService schedulingService)
	{
		this.runtime = runtime;
		this.schedulingService = schedulingService;
		scheduleBucket = schedulingService.allocateBucket();
		feedFactories.put(FeedType.CSV, new CSVFeedFactory(runtime, eventAdapterService, schedulingService, scheduleBucket));
		csvAdapter = new CSVAdapterImpl(feedFactories.get(FeedType.CSV));
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createFeed(net.esper.adapter.FeedSpec)
	 */
	public Feed createFeed(FeedSpec feedSpec)
	{
		if(feedFactories.get(feedSpec.getFeedType()) == null)
		{
			throw new IllegalArgumentException("Unknown FeedType: " + feedSpec.getFeedType());
		}
		return feedFactories.get(feedSpec.getFeedType()).createFeed(feedSpec);
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createConductor()
	 */
	public FeedCoordinatorImpl createFeedCoordinator(boolean usingEngineThread)
	{
		return new FeedCoordinatorImpl(this, runtime, schedulingService, usingEngineThread);
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#getCSVAdapter()
	 */
	public CSVAdapter getCSVAdapter()
	{
		return csvAdapter;
	}
}
