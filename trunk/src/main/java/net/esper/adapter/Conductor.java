package net.esper.adapter;

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
	private static final Log log = LogFactory.getLog(Conductor.class);
	
	private final Map<SendableEvent, Player> eventsFromPlayers = new HashMap<SendableEvent, Player>();
	private final Set<Player> emptyPlayers = new HashSet<Player>();
	
	/**
	 * Ctor.
	 * @param runtime - the runtime to send events into
	 * @param schedulingService - used for making callbacks
	 */
	public Conductor(EPRuntime runtime, SchedulingService schedulingService)
	{
		super(runtime, schedulingService);
	}

	public synchronized SendableEvent read() throws EPException
	{
		if(eventsToSend.isEmpty() && eventsFromPlayers.isEmpty() && emptyPlayers.isEmpty())
		{
			state = State.STOPPED;
		}
		
		pollEmptyPlayers();
		
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
	public synchronized void add(Player player)
	{
		if(player == null)
		{
			throw new NullPointerException("Player cannot be null");
		}
		if(eventsFromPlayers.values().contains(player) || emptyPlayers.contains(player))
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
		addNewEvent(eventsFromPlayers.get(event));
		pollEmptyPlayers();
	}
	
	private void addNewEvent(Player player)
	{
		if(player.getState() == State.STOPPED)
		{
			eventsFromPlayers.values().remove(player);
			return;
		}
		
		SendableEvent event = player.read();
		if(event != null)
		{
			log.debug(".addNewEvent event==" + event);
			eventsToSend.add(event);
			eventsFromPlayers.put(event, player);
		}
		else
		{
			log.debug(".addNewEvent player is emtpy");
			emptyPlayers.add(player);
		}
	}
	
	private void pollEmptyPlayers()
	{
		log.debug(".pollEmptyPlayers");
		for(Iterator<Player> iterator = emptyPlayers.iterator(); iterator.hasNext(); )
		{
			Player player = iterator.next();
			if(player.getState() == State.STOPPED)
			{
				iterator.remove();
				continue;
			}
			
			SendableEvent event = player.read();
			if(event != null)
			{
				log.debug(".pollEmptyPlayers once empty now has event with send time==" + event.getSendTime());
				eventsToSend.add(event);
				eventsFromPlayers.put(event, player);
				iterator.remove();
			}
		}
	}

}
