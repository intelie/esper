package net.esper.adapter;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.adapter.Player.State;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;

/**
 * A skeleton implementation of the Player interface.
 */
public abstract class AbstractPlayer implements Player
{
	private static final Log log = LogFactory.getLog(AbstractPlayer.class);
	
	protected State state = State.NEW;
	protected final SortedSet<SendableEvent> eventsToSend = new TreeSet<SendableEvent>(new SendableEventComparator());
	private long currentTime = 0;
	
	private final SchedulingService schedulingService;
	private final EPRuntime runtime;
	
	
	/**
	 * Ctor.
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for scheduling callbacks
	 */
	public AbstractPlayer(EPRuntime runtime, SchedulingService schedulingService)
	{
		this.runtime = runtime;
		this.schedulingService = schedulingService;
	}
	
	public synchronized State getState()
	{
		return state;
	}

	public synchronized void start() throws EPException
	{
		if(state == State.NEW)
		{
			continuePlaying();
		}
	}

	public synchronized void pause() throws EPException
	{
		if(state == State.RUNNING)
		{
			log.debug(".pause");
			state = State.PAUSED;
		}
	}

	public synchronized void resume() throws EPException
	{
		if(state == State.PAUSED)
		{
			log.debug(".resume");
			continuePlaying();
		}
	}

	public synchronized void stop() throws EPException
	{
		if(state != State.STOPPED)
		{
			state = State.STOPPED;
			close();
		}
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
	
	private void continuePlaying()
	{
		state = State.RUNNING;
		updateCurrentTime();
		fillEventsToSend();
		if(eventsToSend.isEmpty())
		{
			return;
		}
		sendSoonestEvents();
		scheduleNextCallback();
	}

	private void updateCurrentTime()
	{
		currentTime = schedulingService.getTime();
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
			eventsToSend.first().send(runtime);
			replaceFirstEventToSend();
		}
	}

	private void reactToCallback()
	{
		log.debug(".reactToCallback");
		if(state != State.PAUSED && state != State.STOPPED)
		{
			log.debug(".reactToCallback executing");
			continuePlaying();
		}
	}
	
	private void scheduleNextCallback()
	{
		if(eventsToSend.isEmpty())
		{
			return;
		}
		long afterMsec = eventsToSend.first().getSendTime() - currentTime;
		ScheduleSlot scheduleSlot = eventsToSend.first().getScheduleSlot();
		log.debug(".scheduleNextCallback schedulingCallback in " + afterMsec + " milliseconds");
		schedulingService.add(afterMsec, new ScheduleCallback() { public void scheduledTrigger() { reactToCallback(); } }, scheduleSlot);
	}
}
