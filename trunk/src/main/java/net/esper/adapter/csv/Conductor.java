package net.esper.adapter.csv;

import java.util.ArrayList;
import java.util.List;

import net.esper.client.EPException;

/**
 * A utility for synchronizing the sending of several CSVAdapters.
 */
public class Conductor
{
	private final List<CSVPlayer> adapters;
	private final CSVTimer timer;
	private boolean isStarted;
	private boolean isCancelled;
	private boolean isPaused;
	
	public Conductor()
	{
		adapters = new ArrayList<CSVPlayer>();
		timer = new CSVTimer();
		isStarted = false;
		isCancelled = false;
	}
	
	/**
	 * Add a CSVAdapter to the group.
	 * @param adapter - the adapter to add
	 */
	protected void add(CSVPlayer adapter)
	{
		adapter.setTimer(timer);
		adapters.add(adapter);
	}
	
	/**
	 * Start the event sending of all the adapters in the group.
	 * @throws EPException in case of errors reading the file or sending the events
	 */
	protected void start() throws EPException
	{
		if(isStarted)
		{
			throw new EPException("CSVAdapterGroup already started");
		}
		if(isCancelled)
		{
			throw new EPException("CSVAdapterGroup already cancelled");
		}
		timer.start();
	}
	
	/**
	 * Cancel the sending of events by all adapters in the group and release any resources.
	 * @throws EPException in case of errors in closing the CSV file or associated resources
	 */
	protected void cancel() throws EPException
	{
		if(isCancelled)
		{
			throw new EPException("CSVAdapterGroup already cancelled");
		}
		isCancelled = true;
		for(CSVPlayer adapter : adapters)
		{
			adapter.close();
		}
	}
	
	protected void pause()
	{
		if(isCancelled)
		{
			throw new EPException("CSVAdapterGroup is already cancelled");
		}
		isPaused = true;
		for(CSVPlayer adapter : adapters)
		{
			adapter.pause();
		}
	}
	
	protected void resume()
	{
		if(!isPaused)
		{
			throw new EPException("CSVAdapterGroup isn't paused");
		}
		if(isCancelled)
		{
			throw new EPException("CSVAdapterGroup is already cancelled");
		}
		for(CSVPlayer adapter : adapters)
		{
			adapter.resume();
		}
		isPaused = false;
	}
}
