package net.esper.adapter;

import net.esper.adapter.csv.CSVAdapter;
import net.esper.adapter.csv.Conductor;
import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * A utility that manages and allows access to input and output adapters.
 */
public class EPAdapterManager
{
	private final EventAdapterService eventAdapterService;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	private final CSVAdapter csvAdapter;
	
	/**
	 * Ctor.
	 * @param eventAdapterService - used for resolving event type aliases
	 * @param schedulingService - used for scheduling callbacks
	 */
	public EPAdapterManager(final EventAdapterService eventAdapterService, final SchedulingService schedulingService)
	{
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		scheduleBucket = schedulingService.allocateBucket();
		csvAdapter = new CSVAdapter(eventAdapterService, schedulingService, scheduleBucket);
	}
	
	public Conductor createConductor()
	{
		return new Conductor();
	}
	
	public CSVAdapter getCSVAdapter()
	{
		return csvAdapter;
	}
}
