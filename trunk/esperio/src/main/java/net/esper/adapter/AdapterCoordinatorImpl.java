package net.esper.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.core.EPServiceProviderSPI;
import net.esper.schedule.ScheduleBucket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An implementation of AdapterCoordinator.
 */
public class AdapterCoordinatorImpl extends AbstractCoordinatedAdapter implements AdapterCoordinator
{
	private static final Log log = LogFactory.getLog(AdapterCoordinatorImpl.class);

    private final Map<SendableEvent, CoordinatedAdapter> eventsFromAdapters = new HashMap<SendableEvent, CoordinatedAdapter>();
	private final Set<CoordinatedAdapter> emptyAdapters = new HashSet<CoordinatedAdapter>();
	private final boolean usingEngineThread;
	private final ScheduleBucket scheduleBucket;
	private final EPServiceProvider epService;

	/**
	 * Ctor.
	 * @param epService - the EPServiceProvider for the engine services and runtime
	 * @param usingEngineThread - true if the coordinator should set time by the scheduling service in the engine,
	 *                            false if it should set time externally through the calling thread
	 */
	public AdapterCoordinatorImpl(EPServiceProvider epService, boolean usingEngineThread)
	{
		super(epService, usingEngineThread);
		if(epService == null)
		{
			throw new NullPointerException("epService cannot be null");
		}
		if(!(epService instanceof EPServiceProviderSPI))
		{
			throw new IllegalArgumentException("Illegal type of EPServiceProvider");
		}
		this.epService = epService;
		this.scheduleBucket = ((EPServiceProviderSPI)epService).getSchedulingService().allocateBucket();
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
	public void coordinate(InputAdapter inputAdapter)
	{
		if(inputAdapter == null)
		{
			throw new NullPointerException("AdapterSpec cannot be null");
		}

		if(!(inputAdapter instanceof CoordinatedAdapter))
		{
			throw new IllegalArgumentException("Cannot coordinate a Adapter of type " + inputAdapter.getClass());
		}
		CoordinatedAdapter adapter = (CoordinatedAdapter)inputAdapter;
		if(eventsFromAdapters.values().contains(adapter) || emptyAdapters.contains(adapter))
		{
			return;
		}
		adapter.disallowStateTransitions();
		adapter.setEPService(epService);
		adapter.setUsingEngineThread(usingEngineThread);
		adapter.setScheduleSlot(scheduleBucket.allocateSlot());
		addNewEvent(adapter);
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
	private void addNewEvent(CoordinatedAdapter adapter)
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
		for(Iterator<CoordinatedAdapter> iterator = emptyAdapters.iterator(); iterator.hasNext(); )
		{
			CoordinatedAdapter adapter = iterator.next();
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
