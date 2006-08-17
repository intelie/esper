package net.esper.adapter.csv;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A utility for scheduling callbacks. Callbacks delays are
 * relative to the time that the CSVTimer.start() method was 
 * called.
 */
public class CSVTimer
{
	private final Timer timer;
	private final Map<TimerTask, Long> initialTasks;
	private boolean started = false;
	private Date startTime;
	
	/**
	 * Ctor.
	 */
	protected CSVTimer()
	{
		timer = new Timer();
		initialTasks = new HashMap<TimerTask, Long>();
		started = false;
	}
	
	/**
	 * Schedule a callback to the timerTask.run() method.
	 * @param timerTask - the callback to make
	 * @param timestamp - the delay from the time that the timer has been started that the callback should be made
	 */
	protected void schedule(TimerTask timerTask, long timestamp)
	{
		if(started)
		{
			scheduleTask(timerTask, timestamp);
		}
		else
		{
			initialTasks.put(timerTask, timestamp);
		}
	}
	
	/**
	 * Start the timer.
	 */
	protected void start()
	{
		if(started == true)
		{
			throw new IllegalStateException("Calling start() on a CSVTimer that has already been started");
		}
		
		startTime = new Date(System.currentTimeMillis());
		started = true;
		scheduleInitialTasks();
	}

	/**
	 * Cancel all scheduled tasks and close the timer.
	 */
	protected void cancel()
	{
		timer.cancel();
	}
	
	private void scheduleInitialTasks()
	{
		for(TimerTask timerTask : initialTasks.keySet())
		{
			scheduleTask(timerTask, initialTasks.get(timerTask));
		}
	}

	private void scheduleTask(TimerTask timerTask, long timestamp)
	{
		Date time = new Date(startTime.getTime() + timestamp);
		timer.schedule(timerTask, time);
	}
}
