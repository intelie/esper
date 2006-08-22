package net.esper.adapter.csv;

import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

public class CSVAdapter
{
	private final EventAdapterService eventAdapterService;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	
	
	/**
	 * Ctor.
	 * @param eventAdapterService - used for resolving event type aliases
	 * @param schedulingService - used for making callbacks
	 * @param scheduleBucket - the scheduling bucket that all adapters use
	 */
	public CSVAdapter(final EventAdapterService eventAdapterService, final SchedulingService schedulingService, final ScheduleBucket scheduleBucket)
	{
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		this.scheduleBucket = scheduleBucket;
	}
	
	
	
}
