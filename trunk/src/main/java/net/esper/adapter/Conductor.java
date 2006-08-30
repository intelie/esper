package net.esper.adapter;

import java.util.HashMap;
import java.util.Map;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.SchedulingService;

/**
 * A utility that allows several Players to be synchronized so 
 * that they can send events into the runtime according to some
 * pre-defined ordering. 
 * <p> 
 * The Conductor relies on the underlying Players' read() method, 
 * and calls to the Conductor will not affect the underlying 
 * Players' state except for changes that occur by calling an 
 * individual Player's read() method.
 * <p>
 * The Conductor will keep trying to pull events from a Player as
 * long as the Player is not stopped.
 */
public class Conductor extends AbstractPlayer
{
	private final Map<SendableEvent, Player> eventsFromPlayers = new HashMap<SendableEvent, Player>();
	/**
	 * Ctor.
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for making callbacks
	 */
	public Conductor(EPRuntime runtime, SchedulingService schedulingService)
	{
		super(runtime, schedulingService);
	}

	public SendableEvent read() throws EPException
	{
		if(state == State.STOPPED || eventsToSend.isEmpty())
		{
			return null;
		}
		SendableEvent result = eventsToSend.first();
		replaceFirstEventToSend();
		return result;
	}
	
	/**
	 * Add a Player to the Conductor.
	 * @param player - the Player to add
	 */
	public void add(Player player)
	{
		if(eventsFromPlayers.values().contains(player))
		{
			return;
		}
		addNewEvent(player);
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
	 * event returned by the read() method of the same Player that
	 * provided the first event.
	 */
	protected void replaceFirstEventToSend()
	{
		SendableEvent event = eventsToSend.first();
		eventsToSend.remove(event);
		Player player = eventsFromPlayers.get(event);
		addNewEvent(player);
	}

	private void addNewEvent(Player player)
	{
		SendableEvent event = player.read();
		if(event != null)
		{
			eventsToSend.add(event);
			eventsFromPlayers.put(event, player);
		}
	}

}
