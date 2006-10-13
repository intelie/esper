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
	private long currentTime = 0;
	protected long startTime;
	
	/**
	 * Ctor.
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for scheduling callbacks
	 */
	public AbstractReadableFeed(EPRuntime runtime, SchedulingService schedulingService)
	{
		this.runtime = runtime;
		this.schedulingService = schedulingService;
	}
	
	public FeedState getState()
	{
		return stateManager.getState();
	}

	public void start() throws EPException
	{
		log.debug(".start");
		startTime = schedulingService.getTime();
		stateManager.start();
		continuePlaying();
	}

	public void pause() throws EPException
	{
		stateManager.pause();
	}

	public void resume() throws EPException
	{
		stateManager.resume();
		continuePlaying();
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
	 * Perform any actions specific to this Player that should
	 * be completed before the Player is stopped.
	 */
	protected abstract void close();

	/**
	 * Remove the first member of eventsToSend and insert
	 * another event chosen in some fashion specific to this 
	 * Player.
	 */
	protected abstract void replaceFirstEventToSend();

	/**
	 * Reset all the changeable state of this ReadableFeed, as if it were just created.
	 */
	protected abstract void reset();
	
	private void continuePlaying()
	{
		updateCurrentTime();
		fillEventsToSend();
		sendSoonestEvents();
		scheduleNextCallback();
	}

	private void updateCurrentTime()
	{
		currentTime = schedulingService.getTime();
		log.debug(".updateCurrentTime currentTime==" + currentTime);
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
		while(!eventsToSend.isEmpty() && eventsToSend.first().getSendTime() <= currentTime)
		{
			log.debug(".sendSoonestEvents sending event " + eventsToSend.first() + ", its sendTime==" + eventsToSend.first().getSendTime());
			eventsToSend.first().send(runtime);
			replaceFirstEventToSend();
		}
	}

	private void reactToCallback()
	{
		log.debug(".reactToCallback");
		if(stateManager.getState() == FeedState.STARTED)
		{
			log.debug(".reactToCallback executing");
			continuePlaying();
		}
	}
	
	private void scheduleNextCallback()
	{
		ScheduleCallback nextScheduleCallback = new ScheduleCallback() { public void scheduledTrigger() { reactToCallback(); } };
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
