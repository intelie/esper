package net.esper.adapter;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.CSVAdapterImpl;
import net.esper.adapter.csv.CSVFeedCreator;
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
	private final Map<FeedType, FeedCreator> feedCreators = new HashMap<FeedType, FeedCreator>();
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
		feedCreators.put(FeedType.CSV, new CSVFeedCreator(runtime, eventAdapterService, schedulingService, scheduleBucket));
		csvAdapter = new CSVAdapterImpl(feedCreators.get(FeedType.CSV));
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createFeed(net.esper.adapter.FeedSpec)
	 */
	public Feed createFeed(FeedSpec feedSpec)
	{
		if(feedCreators.get(feedSpec.getFeedType()) == null)
		{
			throw new IllegalArgumentException("Unknown FeedType: " + feedSpec.getFeedType());
		}
		return feedCreators.get(feedSpec.getFeedType()).createFeed(feedSpec);
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createConductor()
	 */
	public FeedCoordinatorImpl createFeedCoordinator()
	{
		return new FeedCoordinatorImpl(this, runtime, schedulingService);
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#getCSVAdapter()
	 */
	public CSVAdapter getCSVAdapter()
	{
		return csvAdapter;
	}
}
