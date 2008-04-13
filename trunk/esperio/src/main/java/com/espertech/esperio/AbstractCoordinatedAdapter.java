package com.espertech.esperio;

import java.util.SortedSet;
import java.util.TreeSet;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.ExtensionServicesContext;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSlot;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.util.ManagedLockImpl;
import com.espertech.esper.util.ExecutionPathDebugLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A skeleton implementation for coordinated adapter reading, for adapters that
 * can do timestamp-coordinated input.
 */
public abstract class AbstractCoordinatedAdapter implements CoordinatedAdapter
{
	private static final Log log = LogFactory.getLog(AbstractCoordinatedAdapter.class);

    /**
     * Statement management.
     */
    protected final AdapterStateManager stateManager = new AdapterStateManager();

    /**
     * Sorted events to be sent.
     */
    protected final SortedSet<SendableEvent> eventsToSend = new TreeSet<SendableEvent>(new SendableEventComparator());

    /**
     * Slot for scheduling.
     */
    protected ScheduleSlot scheduleSlot;

	private EPRuntime runtime;
	private SchedulingService schedulingService;
	private boolean usingEngineThread, usingExternalTimer;
	private long currentTime = 0;
	private long lastEventTime = 0;
	private long startTime;

	/**
	 * Ctor.
	 * @param epService - the EPServiceProvider for the engine runtime and services
	 * @param usingEngineThread - true if the Adapter should set time by the scheduling service in the engine,
	 *                            false if it should set time externally through the calling thread
	 * @param usingExternalTimer - true to use esper's external timer mechanism instead of internal timing
	 */
	public AbstractCoordinatedAdapter(EPServiceProvider epService, boolean usingEngineThread, boolean usingExternalTimer)
	{
		this.usingEngineThread = usingEngineThread;
		this.usingExternalTimer = usingExternalTimer;

		if(epService == null)
		{
			return;
		}
		if(!(epService instanceof EPServiceProviderSPI))
		{
			throw new IllegalArgumentException("Invalid epService provided");
		}
		this.runtime = ((EPServiceProviderSPI)epService).getEPRuntime();
		this.schedulingService = ((EPServiceProviderSPI)epService).getSchedulingService();
	}

	public AdapterState getState()
	{
		return stateManager.getState();
	}

	public void start() throws EPException
	{
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".start");
        }
        if(runtime == null)
		{
			throw new EPException("Attempting to start an Adapter that hasn't had the epService provided");
		}
		startTime = getCurrentTime();
		if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".start startTime==" + startTime);
        }
        stateManager.start();
		continueSendingEvents();
	}

	public void pause() throws EPException
	{
		stateManager.pause();
	}

	public void resume() throws EPException
	{
		stateManager.resume();
		continueSendingEvents();
	}

	public void destroy() throws EPException
	{
		stateManager.destroy();
		close();
	}

	public void stop() throws EPException
	{
		if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".stop");
        }
		stateManager.stop();
		eventsToSend.clear();
		currentTime = 0;
		reset();
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.ReadableAdapter#disallowStateChanges()
	 */
	public void disallowStateTransitions()
	{
		stateManager.disallowStateTransitions();
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.ReadableAdapter#setUsingEngineThread(boolean)
	 */
	public void setUsingEngineThread(boolean usingEngineThread)
	{
		this.usingEngineThread = usingEngineThread;
	}


    /**
     * Set to true to use esper's external timer mechanism instead of internal timing
     * @param usingExternalTimer true for external timer
     */
    public void setUsingExternalTimer(boolean usingExternalTimer)
	{
		this.usingExternalTimer = usingExternalTimer;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.CoordinatedAdapter#setScheduleSlot(com.espertech.esper.schedule.ScheduleSlot)
	 */
	public void setScheduleSlot(ScheduleSlot scheduleSlot)
	{
		this.scheduleSlot = scheduleSlot;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.CoordinatedAdapter#setEPService(com.espertech.esper.client.EPServiceProvider)
	 */
	public void setEPService(EPServiceProvider epService)
	{
		if(epService == null)
		{
			throw new NullPointerException("epService cannot be null");
		}
		if(!(epService instanceof EPServiceProviderSPI))
		{
			throw new IllegalArgumentException("Invalid type of EPServiceProvider");
		}
		EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;
		runtime = spi.getEPRuntime();
		schedulingService = spi.getSchedulingService();
	}

	/**
	 * Perform any actions specific to this Adapter that should
	 * be completed before the Adapter is stopped.
	 */
	protected abstract void close();

	/**
	 * Remove the first member of eventsToSend and insert
	 * another event chosen in some fashion specific to this
	 * Adapter.
	 */
	protected abstract void replaceFirstEventToSend();

	/**
	 * Reset all the changeable state of this Adapter, as if it were just created.
	 */
	protected abstract void reset();

	private void continueSendingEvents()
	{
		boolean keepLooping = true;
		while(stateManager.getState() == AdapterState.STARTED && keepLooping)
		{
			currentTime = getCurrentTime();
            if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
            {
                log.debug(".continueSendingEvents currentTime==" + currentTime);
            }
            fillEventsToSend();
			sendSoonestEvents();
			keepLooping = waitToSendEvents();
		}
	}

	private boolean waitToSendEvents()
	{
		if(usingExternalTimer)
		{
			return false;
		}
		else if(usingEngineThread)
		{
			scheduleNextCallback();
			return false;
		}
		else
		{
			long sleepTime = 0;
			if(eventsToSend.isEmpty())
			{
				sleepTime = 100;
			}
			else
			{
				sleepTime = eventsToSend.first().getSendTime() - (currentTime - startTime);
			}

			try
			{
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException ex)
			{
				throw new EPException(ex);
			}
			return true;
		}
	}

	private long getCurrentTime()
	{
		return usingEngineThread ? schedulingService.getTime() : System.currentTimeMillis();
	}

	private void fillEventsToSend()
	{
		if(eventsToSend.isEmpty())
		{
			SendableEvent event = read();
			if(event != null)
			{
				eventsToSend.add(event);
			}
		}
	}

	private void sendSoonestEvents()
	{
		if (usingExternalTimer)
		{
			// send all events in order and when time clicks over send time event for previous time
			while (!eventsToSend.isEmpty())
			{
				long currentEventTime = eventsToSend.first().getSendTime();
				// check whether time has increased. Cannot go backwards due to checks elsewhere
				if (currentEventTime > lastEventTime)
				{
					this.runtime.sendEvent(new CurrentTimeEvent(lastEventTime));
					lastEventTime = currentEventTime;
				}
				sendFirstEvent();
			}
			// send final time tick
			this.runtime.sendEvent(new CurrentTimeEvent(lastEventTime));
		}
		else
		{
			// watch time and send events to catch up
			while(!eventsToSend.isEmpty() && eventsToSend.first().getSendTime() <= currentTime - startTime)
			{
	            sendFirstEvent();
			}
		}
	}

	private void sendFirstEvent()
	{
		if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
		{
		    log.debug(".sendFirstEvent currentTime==" + currentTime);
		    log.debug(".sendFirstEvent sending event " + eventsToSend.first() + ", its sendTime==" + eventsToSend.first().getSendTime());                
		}
		eventsToSend.first().send(runtime);
		replaceFirstEventToSend();
	}

	private void scheduleNextCallback()
	{
		ScheduleHandleCallback nextScheduleCallback = new ScheduleHandleCallback() { public void scheduledTrigger(ExtensionServicesContext extensionServicesContext) { continueSendingEvents(); } };
        EPStatementHandleCallback scheduleCSVHandle = new EPStatementHandleCallback(new EPStatementHandle("AbstractCoordinatedAdapter", new ManagedLockImpl("CSV"), "AbstractCoordinatedAdapter", false), nextScheduleCallback);
        ScheduleSlot nextScheduleSlot;

		if(eventsToSend.isEmpty())
		{
            if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
            {
			    log.debug(".scheduleNextCallback no events to send, scheduling callback in 100 ms");
            }
            nextScheduleSlot = new ScheduleSlot(0,0);
			schedulingService.add(100, scheduleCSVHandle, nextScheduleSlot);
		}
		else
		{
            // Offset is not a function of the currentTime alone.
            long baseMsec = currentTime - startTime;
            long afterMsec = eventsToSend.first().getSendTime() - baseMsec;

			nextScheduleSlot = eventsToSend.first().getScheduleSlot();
            if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
            {
			    log.debug(".scheduleNextCallback schedulingCallback in " + afterMsec + " milliseconds");
            }
            schedulingService.add(afterMsec, scheduleCSVHandle, nextScheduleSlot);
		}
	}
}
