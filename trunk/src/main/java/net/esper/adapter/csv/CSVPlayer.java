
package net.esper.adapter.csv;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AbstractPlayer;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.SendableEvent;
import net.esper.client.EPException;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * A Player for sending records from a CSV file into 
 * the EPRuntime. The settable parameters are:
 * 1. isLooping (false by default) which is true if processing
 * of the CSV file should begin over after the end of the file
 * is reached.
 * 2. eventsPerSec, which indicates how many events per second
 * should be sent into the runtime. The value must be between
 * 1 and 1000, inclusive. If this value isn't set, the CSVPlayer
 * will attempt to send events according to a timestamp column in
 * the CSV file.
 */
public class CSVPlayer extends AbstractPlayer
{
	private static final Log log = LogFactory.getLog(CSVPlayer.class);

	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";

	private final ScheduleSlot scheduleSlot;
	private final MapEventSpec mapEventSpec;	
	private int eventsPerSec = -1;
	private final CSVReader reader;
	private final Map<String, FastConstructor>propertyConstructors;
	private final String[] propertyOrder;
	private final boolean propertyOrderContainsTimestamp;
	private long lastTimestamp = 0;
	private long totalDelay = 0;
	/**
	 * Ctor.
	 * @param adapterInputSource - the source of the CSV file
	 * @param mapSpec - describes the format of the events to create and send into the EPRuntime
	 * @param schedulingService - used for making callbacks
	 * @param scheduleSlot - this player's schedule slot
	 * @throws EPException in case of errors opening the CSV file
	 */
	public CSVPlayer(AdapterInputSource adapterInputSource, 
					 MapEventSpec mapSpec, 
					 SchedulingService schedulingService, 
					 ScheduleSlot scheduleSlot) 
	throws EPException
	{
		super(mapSpec.getEpRuntime(), schedulingService);
		this.scheduleSlot = scheduleSlot;
		this.mapEventSpec = mapSpec;

		this.propertyConstructors = createPropertyConstructors(mapEventSpec.getPropertyTypes());
		reader = new CSVReader(adapterInputSource);
		
		// Resolve the order of properties in the CSV file
		String[] firstRow;
		try
		{
			firstRow = reader.getNextRecord();
		} 
		catch (EOFException e)
		{
			stop();
			firstRow = null;
		}		
		
		this.propertyOrder = PropertyOrderHelper.resolvePropertyOrder(firstRow, mapSpec.getPropertyTypes());
		this.propertyOrderContainsTimestamp = PropertyOrderHelper.propertyOrderContainsTimestamp(propertyOrder);
	
		boolean isUsingTitleRow = (firstRow == propertyOrder);
		reader.setIsUsingTitleRow(isUsingTitleRow);
		reader.reset();
	}
	
	public synchronized SendableEvent read() throws EPException
	{
		if(state == State.STOPPED)
		{
			return null;
		}
		
		try
		{
			if(eventsToSend.isEmpty())
			{
				return new SendableMapEvent(newMapEvent(), mapEventSpec.getEventTypeAlias(), totalDelay, scheduleSlot);
			}
			else
			{
				SendableEvent event = eventsToSend.first();
				eventsToSend.remove(event);
				return event;
			}
		}
		catch (EOFException e)
		{
			log.debug(".read reached end of CSV file");
			stop();
			return null;
		}
	}

	/**
	 * Set the eventsPerSec value.
	 * @param eventsPerSec - the value to use, must be between 1 and 1000, inclusive
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
	 * Close the CSVReader. 
	 */
	protected void close()
	{
		reader.close();
	}
	
	/**
	 * Remove the first member of eventsToSend. If there is
	 * another record in the CSV file, insert the event created
	 * from it into eventsToSend.
	 */
	protected void replaceFirstEventToSend()
	{
		eventsToSend.remove(eventsToSend.first());
		SendableEvent event = read();
		if(event != null)
		{
			eventsToSend.add(event);
		}
	}

	private Map<String, Object> newMapEvent() throws EOFException
	{
		String[] row =  reader.getNextRecord();
		updateTotalDelay(row, reader.getAndClearIsReset());
		return createMapFromRow(row);
	}

	private Map<String, FastConstructor> createPropertyConstructors(Map<String, Class> propertyTypes)
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
	
	private Map<String, Object> createMapFromRow(String[] row)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
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
				map.put(property, value);
			}
		} 
		catch (InvocationTargetException e)
		{
			throw new EPException(e);
		}
		return map;
	}
	
	private boolean expectingUndeclaredTimestamp(String[] row)
	{
		return !propertyOrderContainsTimestamp && propertyOrder.length + 1 == row.length;
	}
	
	private void updateTotalDelay(String[] row, boolean isFirstRow)
	{
		if(eventsPerSec != -1)
		{
			int msecPerEvent = 1000/eventsPerSec;
			totalDelay += msecPerEvent;
		}
		else
		{
			Long timestamp = resolveTimestamp(row);
			if(timestamp == null)
			{
				throw new EPException("Couldn't resolve the timestamp for record " + Arrays.asList(row));
			}
			else if(timestamp < 0)
			{
				throw new EPException("Encountered negative timestamp for CSV record : " + Arrays.asList(row));
			}
			else
			{
				long timestampDifference = 0;
				if(timestamp < lastTimestamp)
				{
					if(!isFirstRow)
					{
						throw new EPException("Subsequent timestamp " + timestamp + " is smaller than previous timestamp " + lastTimestamp);
					}
					else
					{
						timestampDifference = timestamp;
					}
				}
				else
				{
					timestampDifference = timestamp - lastTimestamp;
				}
				lastTimestamp = timestamp;
				totalDelay += timestampDifference;
			}
		}
	}
	
	
	private Long resolveTimestamp(String[] row)
	{
		int timestampIndex = getTimestampIndex(row);
		if(timestampIndex != -1)
		{
			return Long.parseLong(row[timestampIndex]);
		}
		else
		{
			return null;
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
}
