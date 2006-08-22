package net.esper.adapter.csv;

import java.util.ArrayList;
import java.util.List;

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
	 * @throws CSVAdapterException in case of errors reading the file or sending the events
	 */
	protected void start() throws CSVAdapterException
	{
		if(isStarted)
		{
			throw new CSVAdapterException("CSVAdapterGroup already started");
		}
		if(isCancelled)
		{
			throw new CSVAdapterException("CSVAdapterGroup already cancelled");
		}
		timer.start();
	}
	
	/**
	 * Cancel the sending of events by all adapters in the group and release any resources.
	 * @throws CSVAdapterException in case of errors in closing the CSV file or associated resources
	 */
	protected void cancel() throws CSVAdapterException
	{
		if(isCancelled)
		{
			throw new CSVAdapterException("CSVAdapterGroup already cancelled");
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
			throw new CSVAdapterException("CSVAdapterGroup is already cancelled");
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
			throw new CSVAdapterException("CSVAdapterGroup isn't paused");
		}
		if(isCancelled)
		{
			throw new CSVAdapterException("CSVAdapterGroup is already cancelled");
		}
		for(CSVPlayer adapter : adapters)
		{
			adapter.resume();
		}
		isPaused = false;
	}
}
