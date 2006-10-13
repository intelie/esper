
package net.esper.adapter.csv;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AbstractReadableFeed;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedSpec;
import net.esper.adapter.FeedState;
import net.esper.adapter.MapEventSpec;
import net.esper.adapter.SendableEvent;
import net.esper.client.EPException;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * An event Feed that uses a CSV file for a source.
 */
public class CSVFeed extends AbstractReadableFeed implements Feed
{
	private static final Log log = LogFactory.getLog(CSVFeed.class);

	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";

	private final SchedulingService schedulingService;
	private final ScheduleSlot scheduleSlot;
	private final MapEventSpec mapEventSpec;	
	private int eventsPerSec = -1;
	private final CSVReader reader;
	private final Map<String, FastConstructor>propertyConstructors;
	private final String[] propertyOrder;
	private final boolean propertyOrderContainsTimestamp;
	private long lastTimestamp = 0;
	private long totalDelay;
	boolean atEOF = false;
	
	/**
	 * Ctor.
	 * @param feedSpec - the parameters for this feed
	 * @param mapSpec - the specifications for the map event type to create
	 * @param schedulingService - used for scheduling callbacks to send events
	 * @param scheduleSlot - used for ordering this Feeds place for callbacks
	 */
	protected CSVFeed(FeedSpec feedSpec, 
					  MapEventSpec mapSpec, 
					  SchedulingService schedulingService, 
					  ScheduleSlot scheduleSlot)
	{
		super(mapSpec.getEpRuntime(), schedulingService);
		this.scheduleSlot = scheduleSlot;
		this.mapEventSpec = mapSpec;
		this.schedulingService = schedulingService;

		this.propertyConstructors = createPropertyConstructors(mapEventSpec.getPropertyTypes());
		reader = new CSVReader((AdapterInputSource)feedSpec.getParameter("adapterInputSource"));
		
		// Resolve the order of properties in the CSV file
		String[] firstRow;
		try
		{
			firstRow = reader.getNextRecord();
		} 
		catch (EOFException e)
		{
			atEOF = true;
			firstRow = null;
		}		
		
		this.propertyOrder = feedSpec.getParameter("propertyOrder") == null ?
				PropertyOrderHelper.resolvePropertyOrder(firstRow, mapSpec.getPropertyTypes()) :
					(String[])feedSpec.getParameter("propertyOrder");
		this.propertyOrderContainsTimestamp = PropertyOrderHelper.propertyOrderContainsTimestamp(propertyOrder);
	
		boolean isUsingTitleRow = (firstRow == propertyOrder);
		reader.setIsUsingTitleRow(isUsingTitleRow);
		reader.reset();
		
		eventsPerSec = feedSpec.getParameter("eventsPerSec") != null ?
				(Integer)feedSpec.getParameter("eventsPerSec") :
					-1 ;
				
		boolean isLooping = feedSpec.getParameter("isLooping") != null ?
				(Boolean) feedSpec.getParameter("isLooping") :
					false;
		reader.setIsLooping(isLooping);
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.ReadableFeed#read()
	 */
	public SendableEvent read() throws EPException
	{
		if(stateManager.getState() == FeedState.DESTROYED || atEOF)
		{
			return null;
		}
		
		try
		{
			if(eventsToSend.isEmpty())
			{
				return new SendableMapEvent(newMapEvent(), mapEventSpec.getEventTypeAlias(), totalDelay + startTime, scheduleSlot);
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
			atEOF = true;
			return null;
		}
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
	
	/**
	 * Reset all the changeable state of this ReadableFeed, as if it were just created.
	 */
	protected void reset()
	{
		lastTimestamp = 0;
		totalDelay = 0;
		atEOF = false;
		reader.reset();
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
