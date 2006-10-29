package net.esper.adapter;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.csv.CSVAdapterManager;
import net.esper.adapter.csv.CSVAdapterManagerImpl;
import net.esper.adapter.csv.CSVAdapterFactory;
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
	private final Map<AdapterType, AdapterFactory> adapterFactories = new HashMap<AdapterType, AdapterFactory>();
	private final CSVAdapterManager csvAdapter;
	
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
		adapterFactories.put(AdapterType.CSV, new CSVAdapterFactory(runtime, eventAdapterService, schedulingService, scheduleBucket));
		csvAdapter = new CSVAdapterManagerImpl(adapterFactories.get(AdapterType.CSV));
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createFeed(net.esper.adapter.AdapterSpec)
	 */
	public Adapter createAdapter(AdapterSpec adapterSpec)
	{
		if(adapterFactories.get(adapterSpec.getAdapterType()) == null)
		{
			throw new IllegalArgumentException("Unknown AdapterType: " + adapterSpec.getAdapterType());
		}
		return adapterFactories.get(adapterSpec.getAdapterType()).createAdapter(adapterSpec);
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#createConductor()
	 */
	public AdapterCoordinatorImpl createAdapterCoordinator(boolean usingEngineThread)
	{
		return new AdapterCoordinatorImpl(this, runtime, schedulingService, usingEngineThread);
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.EPAdapterManager#getCSVAdapter()
	 */
	public CSVAdapterManager getCSVAdapter()
	{
		return csvAdapter;
	}
}
