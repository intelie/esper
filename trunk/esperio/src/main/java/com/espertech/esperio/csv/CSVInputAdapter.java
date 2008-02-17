
package com.espertech.esperio.csv;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esperio.*;
import com.espertech.esper.util.JavaClassHelper;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.EOFException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * An event Adapter that uses a CSV file for a source.
 */
public class CSVInputAdapter extends AbstractCoordinatedAdapter implements InputAdapter
{
	private static final Log log = LogFactory.getLog(CSVInputAdapter.class);

	private Integer eventsPerSec;
	private CSVReader reader;
	private Map<String, FastConstructor>propertyConstructors;
	private String[] propertyOrder;
	private CSVInputAdapterSpec adapterSpec;
	private Map<String, Class> propertyTypes;
	private String eventTypeAlias;
	private long lastTimestamp = 0;
	private long totalDelay;
	boolean atEOF = false;
	String[] firstRow;

    /**
	 * Ctor.
	 * @param epService - provides the engine runtime and services
	 * @param spec - the parameters for this adapter
	 */
	public CSVInputAdapter(EPServiceProvider epService, CSVInputAdapterSpec spec)
	{
		super(epService, spec.isUsingEngineThread());

		adapterSpec = spec;
		eventTypeAlias = adapterSpec.getEventTypeAlias();
		eventsPerSec = spec.getEventsPerSec();

		if(epService != null)
		{
			finishInitialization(epService, spec);
		}
	}

	/**
	 * Ctor.
	 * @param epService - provides the engine runtime and services
	 * @param adapterInputSource - the source of the CSV file
	 * @param eventTypeAlias - the alias of the Map event to create from the CSV data
	 */
	public CSVInputAdapter(EPServiceProvider epService, AdapterInputSource adapterInputSource, String eventTypeAlias)
	{
		this(epService, new CSVInputAdapterSpec(adapterInputSource, eventTypeAlias));
	}

	/**
	 * Ctor for adapters that will be passed to an AdapterCoordinator.
	 * @param adapterSpec contains parameters that specify the behavior of the input adapter
	 */
	public CSVInputAdapter(CSVInputAdapterSpec adapterSpec)
	{
		this(null, adapterSpec);
	}

	/**
	 * Ctor for adapters that will be passed to an AdapterCoordinator.
	 * @param adapterInputSource - the parameters for this adapter
	 * @param eventTypeAlias - the event type alias name that the input adapter generates events for
	 */
	public CSVInputAdapter(AdapterInputSource adapterInputSource, String eventTypeAlias)
	{
		this(null, adapterInputSource, eventTypeAlias);
	}


	/* (non-Javadoc)
	 * @see com.espertech.esperio.ReadableAdapter#read()
	 */
	public SendableEvent read() throws EPException
	{
		if(stateManager.getState() == AdapterState.DESTROYED || atEOF)
		{
			return null;
		}

		try
		{
			if(eventsToSend.isEmpty())
			{
				return new SendableMapEvent(newMapEvent(), eventTypeAlias, totalDelay, scheduleSlot);
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
			if(stateManager.getState() == AdapterState.STARTED)
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



	/* (non-Javadoc)
	 * @see com.espertech.esperio.AbstractCoordinatedAdapter#setEPService(com.espertech.esper.client.EPServiceProvider)
	 */
	@Override
	public void setEPService(EPServiceProvider epService)
	{
		super.setEPService(epService);
		finishInitialization(epService, adapterSpec);
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
	 * Reset all the changeable state of this ReadableAdapter, as if it were just created.
	 */
	protected void reset()
	{
		lastTimestamp = 0;
		totalDelay = 0;
		atEOF = false;
		if(reader.isResettable())
		{
			reader.reset();
		}
	}

	private void finishInitialization(EPServiceProvider epService, CSVInputAdapterSpec spec)
	{
		assertValidParameters(epService, spec);

		EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;

		scheduleSlot = spi.getSchedulingService().allocateBucket().allocateSlot();

		reader = new CSVReader(spec.getAdapterInputSource());
		reader.setLooping(spec.isLooping());

		String[] firstRow = getFirstRow();

		Map<String, Class> givenPropertyTypes = constructPropertyTypes(spec.getEventTypeAlias(), spec.getPropertyTypes(), spi.getEventAdapterService());

		propertyOrder = spec.getPropertyOrder() != null ?
				spec.getPropertyOrder() :
					CSVPropertyOrderHelper.resolvePropertyOrder(firstRow, givenPropertyTypes);

		reader.setIsUsingTitleRow(isUsingTitleRow(firstRow, propertyOrder));
		if(!isUsingTitleRow(firstRow, propertyOrder))
		{
			this.firstRow = firstRow;
		}

		propertyTypes = resolvePropertyTypes(givenPropertyTypes);
		if(givenPropertyTypes == null)
		{
			spi.getEventAdapterService().addMapType(eventTypeAlias, propertyTypes);
		}

		this.propertyConstructors = createPropertyConstructors(propertyTypes);
	}

	private Map<String, Object> newMapEvent() throws EOFException
	{
		String[] row =  firstRow != null ? firstRow : reader.getNextRecord();
		firstRow = null;
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
			FastClass fastClass = FastClass.create(JavaClassHelper.getBoxedType(propertyTypes.get(property)));
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
				if(propertyTypes != null && !propertyTypes.containsKey(property))
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

	private Map<String, Class> constructPropertyTypes(String eventTypeAlias, Map<String, Class> propertyTypesGiven, EventAdapterService eventAdapterService)
	{
		Map<String, Class> propertyTypes = new HashMap<String, Class>();
		EventType eventType = eventAdapterService.getExistsTypeByAlias(eventTypeAlias);
		if(eventType == null)
		{
			if(propertyTypesGiven != null)
			{
				eventAdapterService.addMapType(eventTypeAlias, propertyTypesGiven);
			}
			return propertyTypesGiven;
		}
		if(!eventType.getUnderlyingType().equals(Map.class))
		{
			throw new EPException("Alias " + eventTypeAlias + " does not correspond to a map event");
		}
		if(propertyTypesGiven != null && eventType.getPropertyNames().length != propertyTypesGiven.size())
		{
			throw new EPException("Event type " + eventTypeAlias + " has already been declared with a different number of parameters");
		}
		for(String property : eventType.getPropertyNames())
		{
			Class type = eventType.getPropertyType(property);
			if(propertyTypesGiven != null && propertyTypesGiven.get(property) == null)
			{
				throw new EPException("Event type " + eventTypeAlias + "has already been declared with different parameters");
			}
			if(propertyTypesGiven != null && !propertyTypesGiven.get(property).equals(type))
			{
				throw new EPException("Event type " + eventTypeAlias + "has already been declared with a different type for property " + property);
			}
			propertyTypes.put(property, type);
		}
		return propertyTypes;
	}

	private void updateTotalDelay(String[] row, boolean isFirstRow)
	{
		if(eventsPerSec != null)
		{
			int msecPerEvent = 1000/eventsPerSec;
			totalDelay += msecPerEvent;
		}
		else if(adapterSpec.getTimestampColumn() != null)
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
		if(adapterSpec.getTimestampColumn() != null)
		{
			for(int i = 0; i < propertyOrder.length; i++)
			{
				if(propertyOrder[i].equals(adapterSpec.getTimestampColumn()))
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
		for(int i = 0; i < propertyOrder.length; i++)
		{
            String name = propertyOrder[i];
            Class type = String.class;
            if (name.contains(" ")) {
                String[] typeAndName = name.split("\\s");
                try {
                    name = typeAndName[1];
                    type = JavaClassHelper.getClassForName(JavaClassHelper.getBoxedClassName(typeAndName[0]));
                    propertyOrder[i] = name;
                } catch (Throwable e) {
                    log.warn("Unable to use given type for property, will default to String: " + propertyOrder[i], e);
                }
            }
            result.put(name, type);
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

	private String[] getFirstRow()
	{
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
		return firstRow;
	}

	private void assertValidEventsPerSec(Integer eventsPerSec)
	{
		if(eventsPerSec != null)
		{
			if(eventsPerSec < 1 || eventsPerSec > 1000)
			{
				throw new IllegalArgumentException("Illegal value of eventsPerSec:" + eventsPerSec);
			}
		}
	}

	private void assertValidParameters(EPServiceProvider epService, CSVInputAdapterSpec adapterSpec)
	{
		if(!(epService instanceof EPServiceProviderSPI))
		{
			throw new IllegalArgumentException("Invalid type of EPServiceProvider");
		}

		if(adapterSpec.getEventTypeAlias() == null)
		{
			throw new NullPointerException("eventTypeAlias cannot be null");
		}

		if(adapterSpec.getAdapterInputSource() == null)
		{
			throw new NullPointerException("adapterInputSource cannot be null");
		}

		assertValidEventsPerSec(adapterSpec.getEventsPerSec());

		if(adapterSpec.isLooping() && !adapterSpec.getAdapterInputSource().isResettable())
		{
			throw new EPException("Cannot loop on a non-resettable input source");
		}
	}
}
