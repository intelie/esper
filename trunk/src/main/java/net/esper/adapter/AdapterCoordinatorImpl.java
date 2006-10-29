package net.esper.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.SchedulingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An implementation of AdapterCoordinator.
 */
public class AdapterCoordinatorImpl extends AbstractReadableAdapter implements AdapterCoordinator
{
	private static final Log log = LogFactory.getLog(AdapterCoordinatorImpl.class);
	
	private final Map<SendableEvent, ReadableAdapter> eventsFromAdapters = new HashMap<SendableEvent, ReadableAdapter>();
	private final Set<ReadableAdapter> emptyAdapters = new HashSet<ReadableAdapter>();
	private final EPAdapterManager manager;
	private final boolean usingEngineThread;
	
	/**
	 * Ctor.
	 * @param manager - the adapter manager that created this AdapterCoordinator
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for making callbacks
	 * @param usingEngineThread - true if the coordinator should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread
	 */
	protected AdapterCoordinatorImpl(EPAdapterManager manager, EPRuntime runtime, SchedulingService schedulingService, boolean usingEngineThread)
	{
		super(runtime, schedulingService, usingEngineThread);
		this.manager = manager;
		this.usingEngineThread = usingEngineThread;
	}

	
	/* (non-Javadoc)
	 * @see net.esper.adapter.ReadableAdapter#read()
	 */
	public SendableEvent read() throws EPException
	{		
		log.debug(".read");
		pollEmptyAdapters();
		
		log.debug(".read eventsToSend.isEmpty==" + eventsToSend.isEmpty());
		log.debug(".read eventsFromAdapters.isEmpty==" + eventsFromAdapters.isEmpty());
		log.debug(".read emptyAdapters.isEmpty==" + emptyAdapters.isEmpty());
		
		if(eventsToSend.isEmpty() && eventsFromAdapters.isEmpty() && emptyAdapters.isEmpty())
		{
			stop();
		}
		
		if(stateManager.getState() == AdapterState.DESTROYED || eventsToSend.isEmpty())
		{
			return null;
		}

		SendableEvent result = eventsToSend.first();

		replaceFirstEventToSend();
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.AdapterCoordinator#add(net.esper.adapter.Adapter)
	 */
	public void coordinate(AdapterSpec adapterSpec)
	{
		if(adapterSpec == null)
		{
			throw new NullPointerException("AdapterSpec cannot be null");
		}
		
		adapterSpec.setUsingEngineThread(usingEngineThread);
		Adapter adapter = manager.createAdapter(adapterSpec);
		
		if(!(adapter instanceof ReadableAdapter))
		{
			throw new IllegalArgumentException("Cannot coordinate a Adapter of type " + adapter.getClass());
		}
		if(eventsFromAdapters.values().contains(adapter) || emptyAdapters.contains(adapter))
		{
			return;
		}
		addNewEvent((ReadableAdapter)adapter);
	}

	/**
	 * Does nothing.
	 */
	protected void close()
	{
		// Do nothing
	}

	/**
	 * Replace the first member of eventsToSend with the next 
	 * event returned by the read() method of the same Adapter that
	 * provided the first event.
	 */
	protected void replaceFirstEventToSend()
	{
		log.debug(".replaceFirstEventToSend");
		SendableEvent event = eventsToSend.first();
		eventsToSend.remove(event);
		addNewEvent(eventsFromAdapters.get(event));
		pollEmptyAdapters();
	}
	
	/**
	 * Reset all the changeable state of this ReadableAdapter, as if it were just created.
	 */
	protected void reset()
	{
		eventsFromAdapters.clear();
		emptyAdapters.clear();
	}
	private void addNewEvent(ReadableAdapter adapter)
	{
		log.debug(".addNewEvent eventsFromAdapters==" + eventsFromAdapters);
		SendableEvent event = adapter.read();
		if(event != null)
		{
			log.debug(".addNewEvent event==" + event);			
			eventsToSend.add(event);
			eventsFromAdapters.put(event, adapter);
		}
		else	
		{
			if(adapter.getState() == AdapterState.DESTROYED)
			{
				eventsFromAdapters.values().removeAll(Collections.singleton(adapter));
			}
			else
			{
				emptyAdapters.add(adapter);
			}
		}
	}
	
	private void pollEmptyAdapters()
	{
		log.debug(".pollEmptyAdapters emptyAdapters.size==" + emptyAdapters.size());
		for(Iterator<ReadableAdapter> iterator = emptyAdapters.iterator(); iterator.hasNext(); )
		{
			ReadableAdapter adapter = iterator.next();
			if(adapter.getState() == AdapterState.DESTROYED)
			{
				iterator.remove();
				continue;
			}
			
			SendableEvent event = adapter.read();
			if(event != null)
			{
				eventsToSend.add(event);
				eventsFromAdapters.put(event, adapter);
				iterator.remove();
			}
		}
	}
}
