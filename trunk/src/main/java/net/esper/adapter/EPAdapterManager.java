package net.esper.adapter;

import net.esper.adapter.csv.CSVAdapter;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * A utility that manages and allows access to input and output adapters.
 */
public class EPAdapterManager
{
	private final EPRuntime runtime;
	private final EventAdapterService eventAdapterService;
	private final SchedulingService schedulingService;
	private final ScheduleBucket scheduleBucket;
	private final CSVAdapter csvAdapter;
	
	/**
	 * Ctor.
	 * @param runtime - the EPRuntime to send events into
	 * @param eventAdapterService - for resolving event type aliases
	 * @param schedulingService - for scheduling callbacks
	 */
	public EPAdapterManager(EPRuntime runtime, final EventAdapterService eventAdapterService, final SchedulingService schedulingService)
	{
		this.runtime = runtime;
		this.eventAdapterService = eventAdapterService;
		this.schedulingService = schedulingService;
		scheduleBucket = schedulingService.allocateBucket();
		csvAdapter = new CSVAdapter(runtime, eventAdapterService, schedulingService, scheduleBucket);
	}
	
	/**
	 * Create an instance of Conductor.
	 * @return a new instance of Conductor
	 */
	public Conductor createConductor()
	{
		return new Conductor();
	}
	
	/**
	 * Get the adapter for CSV files.
	 * @return the CSVAdapter
	 */
	public CSVAdapter getCSVAdapter()
	{
		return csvAdapter;
	}
}
