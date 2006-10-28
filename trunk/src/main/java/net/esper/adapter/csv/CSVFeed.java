
package net.esper.adapter.csv;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.esper.adapter.AbstractReadableFeed;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedSpec;
import net.esper.adapter.FeedState;
import net.esper.adapter.MapEventSpec;
import net.esper.adapter.SendableEvent;
import net.esper.client.EPException;
import net.esper.event.EventAdapterService;
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

	private final String timestampColumn;
	private final SchedulingService schedulingService;
	private final ScheduleSlot scheduleSlot;
	private final MapEventSpec mapEventSpec;	
	private int eventsPerSec = -1;
	private final CSVReader reader;
	private final Map<String, FastConstructor>propertyConstructors;
	private final String[] propertyOrder;
	private long lastTimestamp = 0;
	private long totalDelay;
	boolean atEOF = false;
	
	/**
	 * Ctor.
	 * @param feedSpec - the parameters for this feed
	 * @param mapSpec - the specifications for the map event type to create
	 * @param eventAdapterService - used for declaring new map types
	 * @param schedulingService - used for scheduling callbacks to send events
	 * @param scheduleSlot - used for ordering this Feeds place for callbacks
	 */
	protected CSVFeed(FeedSpec feedSpec, 
					  MapEventSpec mapSpec, 
					  EventAdapterService eventAdapterService, 
					  SchedulingService schedulingService, 
					  ScheduleSlot scheduleSlot)
	{
		super(mapSpec.getEpRuntime(), schedulingService, (Boolean)feedSpec.getParameter("usingEngineThread"));
		
		this.scheduleSlot = scheduleSlot;
		this.mapEventSpec = mapSpec;
		this.schedulingService = schedulingService;
		this.timestampColumn = (String)feedSpec.getParameter("timestampColumn");

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
		

		log.debug(".ctor propertyTypes==" + mapSpec.getPropertyTypes());
		this.propertyOrder = feedSpec.getParameter("propertyOrder") == null ?
				PropertyOrderHelper.resolvePropertyOrder(firstRow, mapSpec.getPropertyTypes()) :
					(String[])feedSpec.getParameter("propertyOrder");		
		log.debug(".ctor propertyOrder==" + Arrays.asList(propertyOrder));		
				
		Map<String, Class> propertyTypes = resolvePropertyTypes(mapEventSpec.getPropertyTypes());
		if(mapEventSpec.getPropertyTypes() == null)
		{
			eventAdapterService.addMapType(mapEventSpec.getEventTypeAlias(), propertyTypes);
		}
		this.propertyConstructors = createPropertyConstructors(propertyTypes);
		
		log.debug(".ctor isUsingTitleRow==" + isUsingTitleRow(firstRow, propertyOrder));
		reader.setIsUsingTitleRow(isUsingTitleRow(firstRow, propertyOrder));
		reader.reset();
		
		eventsPerSec = feedSpec.getParameter("eventsPerSec") != null ?
				(Integer)feedSpec.getParameter("eventsPerSec") :
					-1 ;
				
		boolean looping = feedSpec.getParameter("looping") != null ?
				(Boolean) feedSpec.getParameter("looping") :
					false;
		reader.setLooping(looping);
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
			atEOF = true;
			if(stateManager.getState() == FeedState.STARTED)
			{
				stop();
			}
			else
			{
				destroy();
			}
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
			log.debug(".createPropertyConstructors property==" + property + ", type==" + propertyTypes.get(property	));
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
		
		try
		{
			for(String property : propertyOrder)
			{
				// Skip properties that are in the title row but not
				// part of the map to send
				if(mapEventSpec.getPropertyTypes() != null &&
				   !mapEventSpec.getPropertyTypes().containsKey(property))
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
	
	private void updateTotalDelay(String[] row, boolean isFirstRow)
	{
		if(eventsPerSec != -1)
		{
			int msecPerEvent = 1000/eventsPerSec;
			totalDelay += msecPerEvent;
		}
		else if(timestampColumn != null)
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
		if(timestampColumn != null)
		{
			for(int i = 0; i < propertyOrder.length; i++)
			{
				if(propertyOrder[i].equals(timestampColumn))
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	private Map<String, Class> resolvePropertyTypes(Map<String, Class> propertyTypes)
	{
		if(propertyTypes != null)
		{
			return propertyTypes;
		}
		
		Map<String, Class> result = new HashMap<String, Class>();
		for(String property : propertyOrder)
		{
			result.put(property, String.class);
		}
		return result;
	}
	
	private boolean isUsingTitleRow(String[] firstRow, String[] propertyOrder)
	{
		if(firstRow == null)
		{
			return false;
		}
		Set<String> firstRowSet = new HashSet<String>(Arrays.asList(firstRow));
		Set<String> propertyOrderSet = new HashSet<String>(Arrays.asList(propertyOrder));
		return firstRowSet.equals(propertyOrderSet);
	}
}
