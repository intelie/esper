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
	private boolean isStarted;
	private Date startTime;
	
	/**
	 * Ctor.
	 */
	protected CSVTimer()
	{
		timer = new Timer();
		initialTasks = new HashMap<TimerTask, Long>();
		isStarted = false;
	}
	
	/**
	 * Schedule a callback to the timerTask.run() method.
	 * @param timerTask - the callback to make
	 * @param timestamp - the delay from the time that the timer has been started that the callback should be made
	 */
	protected void schedule(TimerTask timerTask, long timestamp)
	{
		if(isStarted)
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
		if(isStarted == true)
		{
			throw new IllegalStateException("The timer has already been started");
		}
		
		startTime = new Date(System.currentTimeMillis());
		isStarted = true;
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
