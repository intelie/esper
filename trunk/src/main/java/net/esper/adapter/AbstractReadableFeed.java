package net.esper.adapter;

import java.util.SortedSet;
import java.util.TreeSet;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A skeleton implementation of the Player interface.
 */
public abstract class AbstractReadableFeed implements ReadableFeed
{
	private static final Log log = LogFactory.getLog(AbstractReadableFeed.class);
	
	protected final FeedStateManager stateManager = new FeedStateManager();
	protected final SortedSet<SendableEvent> eventsToSend = new TreeSet<SendableEvent>(new SendableEventComparator());
	private final EPRuntime runtime;

	private final SchedulingService schedulingService;
	private final boolean usingEngineThread;
	private long currentTime = 0;
	private long startTime;
	
	/**
	 * Ctor.
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for scheduling callbacks
	 * @param usingEngineThread - true if the Feed should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread
	 */
	public AbstractReadableFeed(EPRuntime runtime, SchedulingService schedulingService, Boolean usingEngineThread)
	{
		this.runtime = runtime;
		this.schedulingService = schedulingService;
		this.usingEngineThread = usingEngineThread != null ? usingEngineThread : true;
		log.debug(".ctor usingEngineThread==" + this.usingEngineThread);
	}
	
	public FeedState getState()
	{
		return stateManager.getState();
	}

	public void start() throws EPException
	{
		log.debug(".start");
		startTime = getCurrentTime();
		log.debug(".start startTime==" + startTime);
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
		log.debug(".stop");
		stateManager.stop();
		eventsToSend.clear();
		currentTime = 0;
		reset();
	}
	
	/**
	 * Perform any actions specific to this Feed that should
	 * be completed before the Feed is stopped.
	 */
	protected abstract void close();

	/**
	 * Remove the first member of eventsToSend and insert
	 * another event chosen in some fashion specific to this 
	 * Feed.
	 */
	protected abstract void replaceFirstEventToSend();

	/**
	 * Reset all the changeable state of this Feed, as if it were just created.
	 */
	protected abstract void reset();
	
	private void continueSendingEvents()
	{
		if(stateManager.getState() == FeedState.STARTED)
		{
			currentTime = getCurrentTime();
			log.debug(".continueSendingEvents currentTime==" + currentTime);
			fillEventsToSend();
			sendSoonestEvents();
			waitToSendEvents();
		}
	}
	
	private void waitToSendEvents()
	{
		if(usingEngineThread)
		{
			scheduleNextCallback();
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
			continueSendingEvents();
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
		while(!eventsToSend.isEmpty() && eventsToSend.first().getSendTime() <= currentTime - startTime)
		{
			log.debug(".sendSoonestEvents currentTime==" + currentTime);
			log.debug(".sendSoonestEvents sending event " + eventsToSend.first() + ", its sendTime==" + eventsToSend.first().getSendTime());
			eventsToSend.first().send(runtime);
			replaceFirstEventToSend();
		}
	}

	private void scheduleNextCallback()
	{
		ScheduleCallback nextScheduleCallback = new ScheduleCallback() { public void scheduledTrigger() { continueSendingEvents(); } };
		ScheduleSlot nextScheduleSlot;

		if(eventsToSend.isEmpty())
		{
			log.debug(".scheduleNextCallback no events to send, scheduling callback in 100 ms");
			nextScheduleSlot = new ScheduleSlot(0,0);
			schedulingService.add(100, nextScheduleCallback, nextScheduleSlot);
		}
		else
		{
			long afterMsec = eventsToSend.first().getSendTime() - currentTime;
			nextScheduleSlot = eventsToSend.first().getScheduleSlot();
			log.debug(".scheduleNextCallback schedulingCallback in " + afterMsec + " milliseconds");
			schedulingService.add(afterMsec, nextScheduleCallback, nextScheduleSlot);
		}
	}
}
