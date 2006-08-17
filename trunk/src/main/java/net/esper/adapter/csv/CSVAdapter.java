package net.esper.adapter.csv;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import net.esper.client.EPRuntime;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * An input adapter for sending records from a CSV file into 
 * the EPRuntime.
 */
public class CSVAdapter
{
	private static final Log log = LogFactory.getLog(CSVAdapter.class);

	public static final String TIMESTAMP_COLUMN_NAME = "timestamp";
	private final MapEventSpec mapEventSpec;	
	private final int eventsPerSec;
	private final CSVTimer timer;
	
	private final CSVReader reader;
	private final Map<String, FastConstructor>propertyConstructors;
	private final String[] propertyOrder;
	private final boolean propertyOrderContainsTimestamp;
	private final Map<String, Object> mapToSend = new HashMap<String, Object>();
	private long totalDelay = 0;
	private boolean cancelled = false;
	
	/**
	 * Ctor.
	 * @param adapterSpec - describes the parameters for this adapter
	 * @param mapSpec - describes the format of the events to create and send into the EPRuntime
	 * @throws CSVAdapterException in case of errors opening the CSV file
	 */
	protected CSVAdapter(CSVAdapterSpec adapterSpec, MapEventSpec mapSpec) throws CSVAdapterException
	{
		this(adapterSpec, mapSpec, new CSVTimer());
	}
	
	/**
	 * Ctor.
	 * @param adapterSpec - describes the parameters for this adapter
	 * @param mapSpec - describes the format of the events to create and send into the EPRuntime
	 * @param timer - the timer to use for scheduling times to send events into the EPRuntime
	 * @throws CSVAdapterException in case of errors opening the CSV file
	 */
	protected CSVAdapter(CSVAdapterSpec adapterSpec, MapEventSpec mapSpec, CSVTimer timer)
	{
		if(!legalEventsPerSecValue(adapterSpec.getEventsPerSec()))
		{
			throw new IllegalArgumentException("Illegal value for events per second to send into the runtime: " + adapterSpec.getEventsPerSec());
		}
		
		reader = new CSVReader(adapterSpec.getPath(), adapterSpec.isLooping());
		
		this.mapEventSpec = mapSpec;
		this.timer = timer;
		
		// Resolve the order of properties in the CSV file
		String[] firstRow;
		try
		{
			firstRow = reader.getNextRecord();
			this.propertyOrder = resolvePropertyOrder(firstRow, mapSpec.getPropertyTypes());
			this.propertyOrderContainsTimestamp = propertyOrderContainsTimestamp();
		
			if(firstRow == propertyOrder)
			{
				firstRow  = reader.getNextRecord();
			}
		} 
		catch (EOFException e)
		{
			reader.close();
			throw new CSVAdapterException("The CSV file is empty");
		}		
		
		this.propertyConstructors = createPropertyConstructors(mapEventSpec.getPropertyTypes());
		
		this.eventsPerSec = adapterSpec.getEventsPerSec();
		scheduleNewCallback(firstRow);
	}
	
	/**
	 * Start the sending of events into the EPRuntime.
	 * @throws CSVAdapterException in case of errors reading the file or sending the events
	 */
	protected void start() throws CSVAdapterException
	{
		if(cancelled)
		{
			throw new CSVAdapterException("CSVAdapter already cancelled");
		}
		timer.start();
	}
	
	/**
	 * Cancel the sending of events and release all resources.
	 * @throws CSVAdapterException in case of errors in closing the CSV file or associated resources
	 */
	protected void cancel() throws CSVAdapterException
	{
		if(cancelled)
		{
			throw new CSVAdapterException("CSVAdapter already cancelled");
		}
		timer.cancel();
		close();
	}
	
	/**
	 * Close the file reader and associated resources.
	 */
	protected void close()
	{
		if(!cancelled)
		{
			cancelled = true;
			reader.close();
		}
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
				throw new CSVAdapterException("Cannot resolve the order of properties in the CSV file");	
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
			throw new CSVAdapterException(e);
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
		String[] row = reader.getNextRecord();
		scheduleNewCallback(row);
	}
	
	private void scheduleNewCallback(String[] row)
	{
		populateMapToSend(row);
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
		totalDelay += delay;
		timer.schedule(new CSVTimerTask(), totalDelay);
	}

	private void scheduleTimestampCallback(String[] row)
	{
		long timestamp = resolveTimestamp(row);
		if(timestamp != -1)
		{
			timer.schedule(new CSVTimerTask(), timestamp);
		}
		else
		{
			throw new CSVAdapterException("Couldn't resolve the timestamp for record " + Arrays.asList(row));
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
	
	// The eventsPerSec value can be either -1 or any positive
	// integer no greater than 1000 
	private boolean legalEventsPerSecValue(int eventsPerSec)
	{
		return eventsPerSec == -1 || (0 < eventsPerSec && eventsPerSec < 1001);
	}
	
	private class CSVTimerTask extends TimerTask
	{
		public void run()
		{
			sendMap();
			try
			{
				scheduleNewCallback();
			} 
			catch (EOFException e)
			{
				log.debug("timer task calling close()");
				close();
			}
		}
	}
}
