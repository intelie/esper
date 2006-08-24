package net.esper.adapter.csv;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Player;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * A class for sending records from a CSV file into 
 * the EPRuntime. It has two settable parameters:
 * isLooping (false by default) which is true if processing
 * of the CSV file should begin over after the end of the file
 * is reached; 
 * eventsPerSec, which indicates how many events per second
 * should be sent into the runtime. The value must be between
 * 1 and 1000, inclusive. If this value isn't set, the CSVPlayer
 * will attempt to send events according to a timestamp column in
 * the CSV file.
 */
public class CSVPlayer implements Player
{
	private static final Log log = LogFactory.getLog(CSVPlayer.class);

	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";

	private final SchedulingService schedulingService;
	private final ScheduleSlot scheduleSlot;
	private final MapEventSpec mapEventSpec;	
	private int eventsPerSec = -1;
	private final CSVReader reader;
	private final Map<String, FastConstructor>propertyConstructors;
	private final String[] propertyOrder;
	private final boolean propertyOrderContainsTimestamp;
	private final Map<String, Object> mapToSend = new HashMap<String, Object>();
	private boolean isStarted;
	private boolean isStopped;
	private boolean isPaused;
	private long lastTimestamp = 0;
	private String[] currentRow = null;
	
	/**
	 * Ctor.
	 * @param adapterInputSource - the source of the CSV file
	 * @param mapSpec - describes the format of the events to create and send into the EPRuntime
	 * @param schedulingService - used for making callbacks
	 * @param scheduleSlot - this player's unique schedule slot
	 * @throws EPException in case of errors opening the CSV file
	 */
	public CSVPlayer(AdapterInputSource adapterInputSource, MapEventSpec mapSpec, SchedulingService schedulingService, ScheduleSlot scheduleSlot) throws EPException
	{
		this.schedulingService = schedulingService;
		this.scheduleSlot = scheduleSlot;
		this.mapEventSpec = mapSpec;

		this.propertyConstructors = createPropertyConstructors(mapEventSpec.getPropertyTypes());
		reader = new CSVReader(adapterInputSource);
		
		// Resolve the order of properties in the CSV file
		String[] firstRow;
		try
		{
			firstRow = reader.getNextRecord();
			this.propertyOrder = resolvePropertyOrder(firstRow, mapSpec.getPropertyTypes());
			this.propertyOrderContainsTimestamp = propertyOrderContainsTimestamp();
		
			boolean isUsingTitleRow = (firstRow == propertyOrder);
			reader.setIsUsingTitleRow(isUsingTitleRow);
			reader.reset();
		} 
		catch (EOFException e)
		{
			reader.close();
			throw new EPException("The CSV file is empty");
		}		
	}
	
	/**
	 * Start the sending of events into the EPRuntime.
	 * @throws EPException in case of errors reading the file or sending the events
	 */
	public void start() throws EPException
	{
		if(isStopped)
		{
			throw new EPException("CSVAdapter is already stopped");
		}
		if(!isStarted)
		{
			isStarted = true;
			try
			{
				scheduleNewCallback();
			} 
			catch (EOFException e)
			{
				stop();
			}
		}
	}
	
	/**
	 * Stop the sending of events and release all resources.
	 * @throws EPException in case of errors in closing the CSV file or associated resources
	 */
	public synchronized void stop() throws EPException
	{
		if(!isStopped)
		{
			isStopped = true;
			reader.close();
		}
	}
	
	/**
	 * Pause the sending of events.
	 * @throws EPException if this player has already been stoppped
	 */
	public synchronized void pause() throws EPException
	{
		if(isStopped)
		{
			throw new EPException("CSVAdapter is already stopped");
		}
		isPaused = true;
	}
	
	/**
	 * Resume sending events after the player has been paused.
	 * @throws EPException in case of errors in reading the CSV file or sending events
	 */
	public synchronized void resume() throws EPException
	{
		if(isStopped)
		{
			throw new EPException("CSVAdapter is already stopped");
		}
		if(isPaused)
		{
			try
			{
				if(currentRow == null)
				{
					scheduleNewCallback();
				}
				else
				{
					scheduleNewCallback(currentRow);
				}
			}
			catch (EOFException e)
			{
				stop();
			}
			isPaused = false;
		}
	}
	
	/**
	 * Set the eventsPerSec value.
	 * @param eventsPerSec - the value to use, must be greater than 0 and at most 1000
	 */
	public void setEventsPerSec(int eventsPerSec)
	{
		if(eventsPerSec < 1 || eventsPerSec > 1000)
		{
			throw new IllegalArgumentException("Illegal value for eventsPerSec: " + eventsPerSec);
		}
		this.eventsPerSec = eventsPerSec;
	}
	
	/**
	 * Set the isLooping value.
	 * @param isLooping - true if processing should start over from the beginning after the end of the CSV file is reached
	 */
	public void setIsLooping(boolean isLooping)
	{
		reader.setIsLooping(isLooping);
	}
	
	/**
	 * Resolve the order of the properties that appear in the CSV file.
	 * If the first row of the file is a valid title row, use that;
	 * otherwise, use the order given in the propertyTypes map.
	 * @param firstRow - the first record of the CSV file
	 * @param propertyTypes - describes the event to send into the EPRuntime
	 * @return the property names in the order in which they occur in the file
	 */
	protected static String[] resolvePropertyOrder(String[] firstRow, Map<String, Class> propertyTypes)
	{
		String[] result = null;
		
		if(isValidTitleRow(firstRow, propertyTypes))
		{
			result = firstRow;
			log.debug(".resolvePropertyOrder using valid title row, propertyOrder==" + Arrays.asList(result));
		}
		else 
		{
			result = propertyTypes.keySet().toArray(new String[0]);
			if(!columnNamesAreValid(result, propertyTypes))
			{
				throw new EPException("Cannot resolve the order of properties in the CSV file");	
			}
			log.debug(".resolvePropertyOrder using propertyTypes, propertyOrder==" + Arrays.asList(result));
		}
		
		return result;
	}
	
	private static boolean isValidTitleRow(String[] row, Map<String, Class> propertyTypes)
	{
		return isValidRowLength(row, propertyTypes) && columnNamesAreValid(row, propertyTypes);
	}

	private static boolean columnNamesAreValid(String[] row, Map<String, Class> propertyTypes)
	{
		Set<String> properties = new HashSet<String>();
		for(String property : row)
		{
			if(!isValidColumnName(property, propertyTypes) || !properties.add(property))
			{
				return false;
			}
		}
		return true;
	}
	
	private static boolean isValidColumnName(String columnName, Map<String, Class> propertyTypes)
	{
		return propertyTypes.containsKey(columnName) || columnName.equals(TIMESTAMP_COLUMN_NAME);
	}
	
	private static boolean isValidRowLength(String[] row, Map<String, Class> propertyTypes)
	{
		return ( row.length == propertyTypes.size() ) || ( row.length == propertyTypes.size() + 1 );
	}
	
	private static Map<String, FastConstructor> createPropertyConstructors(Map<String, Class> propertyTypes)
	{
		Map<String, FastConstructor> constructors = new HashMap<String, FastConstructor>();

		Class[] parameterTypes = new Class[] { String.class };
		for(String property : propertyTypes.keySet())
		{
			FastClass fastClass = FastClass.create(propertyTypes.get(property));
			FastConstructor constructor = fastClass.getConstructor(parameterTypes);
			constructors.put(property, constructor);
		}
		return constructors;
	}
	
	private void sendMap()
	{
		EPRuntime epRuntime = mapEventSpec.getEpRuntime();
		epRuntime.sendEvent(mapToSend, mapEventSpec.getEventTypeAlias());
	}
	
	private void populateMapToSend(String[] row)
	{
		mapToSend.clear();
		
		int count = 0;
		if(expectingUndeclaredTimestamp(row))
		{
			count++;
		}
		
		try
		{
			for(String property : propertyOrder)
			{
				// Skip properties that are in the title row but not
				// part of the map to send
				if(!mapEventSpec.getPropertyTypes().containsKey(property))
				{
					count++;
					continue;
				}
				Object[] parameters = new Object[] { row[count++] };
				Object value = propertyConstructors.get(property).newInstance(parameters);
				mapToSend.put(property, value);
			}
		} 
		catch (InvocationTargetException e)
		{
			throw new EPException(e);
		}
	}
	
	private boolean expectingUndeclaredTimestamp(String[] row)
	{
		return !propertyOrderContainsTimestamp && propertyOrder.length + 1 == row.length;
	}
	
	private boolean propertyOrderContainsTimestamp()
	{
		boolean result = false;
		for(String property : propertyOrder)
		{
			if(property.equals(TIMESTAMP_COLUMN_NAME))
			{
				result = true;
				break;
			}
		}
		return result;
	}
	
	private void scheduleNewCallback() throws EOFException
	{
		currentRow = reader.getNextRecord();
		populateMapToSend(currentRow);
		scheduleNewCallback(currentRow);
	}

	private void scheduleNewCallback(String[] row)
	{
		if(eventsPerSec != -1)
		{
			scheduleEventsPerSecCallback(row);
		}
		else
		{
			scheduleTimestampCallback(row);
		}
	}
	
	
	private void scheduleEventsPerSecCallback(String[] row)
	{
		int delay = 1000 / eventsPerSec;
		schedulingService.add(delay, new CSVScheduleCallback(), scheduleSlot);
	}

	private void scheduleTimestampCallback(String[] row)
	{
		long timestamp = resolveTimestamp(row);
		if(timestamp != -1)
		{
			long delay = (timestamp <= lastTimestamp) ? timestamp : timestamp - lastTimestamp;
			schedulingService.add(delay, new CSVScheduleCallback(), scheduleSlot);
			lastTimestamp = timestamp;
		}
		else
		{
			throw new EPException("Couldn't resolve the timestamp for record " + Arrays.asList(row));
		}
	}
	
	private long resolveTimestamp(String[] row)
	{
		int timestampIndex = getTimestampIndex(row);
		if(timestampIndex != -1)
		{
			return Long.parseLong(row[timestampIndex]);
		}
		else
		{
			return -1;
		}
	}
	
	private int getTimestampIndex(String[] row)
	{
		if(expectingUndeclaredTimestamp(row))
		{
			return 0;
		}
		else if(propertyOrderContainsTimestamp)
		{
			for(int i = 0; i < propertyOrder.length; i++)
			{
				if(propertyOrder[i].equals(TIMESTAMP_COLUMN_NAME))
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	private class CSVScheduleCallback implements ScheduleCallback
	{
		public void scheduledTrigger()
		{
			if (!isPaused && !isStopped)
			{
				sendMap();
				try
				{
					scheduleNewCallback();
				} catch (EOFException e)
				{
					stop();
				}
			}			
		}
	}
}
