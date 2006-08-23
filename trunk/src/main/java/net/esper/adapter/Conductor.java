package net.esper.adapter;

import java.util.ArrayList;
import java.util.List;

import net.esper.client.EPException;

/**
 * A utility for synchronizing several Players.
 */
public class Conductor implements Player
{
	private final List<Player> players = new ArrayList<Player>();
	private boolean isStarted = false;
	private boolean isStopped = false;
	private boolean isPaused = false;
	
	/**
	 * Add a Player.
	 * @param player - the adapter to add
	 */
	public void add(Player player)
	{
		players.add(player);
	}
	
	/**
	 * Start all the Players in the group.
	 * @throws EPException in case of errors starting Players
	 */
	public void start() throws EPException
	{
		if(isStopped)
		{
			throw new EPException("CSVAdapterGroup already cancelled");
		}
		if(!isStarted)
		{
			for(Player player : players)
			{
				player.start();
			}
		}
	}
	
	/**
	 * Stop all the Players in the group.
	 * @throws EPException in case of errors stopping Players
	 */
	public void stop() throws EPException
	{
		if(!isStopped)
		{
			isStopped = true;
			for(Player adapter : players)
			{
				adapter.stop();
			}
		}
	}
	
	/**
	 * Pause all the players in the group.
	 * @throws EPException in case of errors pausing Players
	 */
	public void pause() throws EPException
	{
		if(isStopped)
		{
			throw new EPException("CSVAdapterGroup is already cancelled");
		}
		isPaused = true;
		for(Player adapter : players)
		{
			adapter.pause();
		}
	}
	
	/**
	 * Resume all the Players in the group.
	 * @throws EPExceptions in case of errors resuming Players
	 */
	public void resume()
	{
		if(!isPaused)
		{
			throw new EPException("CSVAdapterGroup isn't paused");
		}
		if(isStopped)
		{
			throw new EPException("CSVAdapterGroup is already cancelled");
		}
		for(Player adapter : players)
		{
			adapter.resume();
		}
		isPaused = false;
	}
}
