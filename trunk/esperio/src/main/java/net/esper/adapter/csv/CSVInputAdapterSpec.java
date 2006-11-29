package net.esper.adapter.csv;

import java.util.Map;

import net.esper.adapter.AdapterInputSource;

/**
 * A spec for CSVAdapters.
 */
public class CSVInputAdapterSpec
{
	private boolean usingEngineThread;
	private String timestampColumn;
	private String eventTypeAlias;
	private AdapterInputSource adapterInputSource;
	private Integer eventsPerSec;
	private String[] propertyOrder;
	private boolean looping;
	private Map<String, Class> propertyTypes;
	
	/**
	 * Ctor.
	 * @param adapterInputSource - the source for the CSV data
	 * @param eventTypeAlias - the alias for the event type created from the CSV data
	 */
	public CSVInputAdapterSpec(AdapterInputSource adapterInputSource, String eventTypeAlias)
	{
		this.adapterInputSource = adapterInputSource;
		this.eventTypeAlias = eventTypeAlias;
	}

	/**
	 * @param eventsPerSec
	 */
	public void setEventsPerSec(int eventsPerSec)
	{
		this.eventsPerSec = eventsPerSec;
	}

	/**
	 * @param propertyOrder - the propertyOrder to set
	 */
	public void setPropertyOrder(String[] propertyOrder)
	{
		this.propertyOrder = propertyOrder;
	}

	/**
	 * @param looping - the isLooping value to set
	 */
	public void setLooping(boolean looping)
	{
		this.looping = looping;
	}
	
	/**
	 * Set the propertyTypes value
	 * @param propertyTypes - a mapping between the names and types of the properties in the
	 *  					  CSV file; this will also be the form of the Map event created
	 *  					  from the data
	 */
	public void setPropertyTypes(Map<String, Class> propertyTypes)
	{
		this.propertyTypes = propertyTypes;
	}
	
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.AdapterSpec#setUsingEngineThread(boolean)
	 */
	public void setUsingEngineThread(boolean usingEngineThread)
	{
		this.usingEngineThread = usingEngineThread;
	}
	
	/**
	 * @return the usingEngineThread
	 */
	public boolean isUsingEngineThread()
	{
		return usingEngineThread;
	}

	/**
	 * Set the timestamp column name.
	 * @param timestampColumn - the name of the column to use for timestamps
	 */
	public void setTimestampColumn(String timestampColumn)
	{
		this.timestampColumn = timestampColumn;
	}

	/**
	 * @return the timestampColumn
	 */
	public String getTimestampColumn()
	{
		return timestampColumn;
	}

	/**
	 * @return the adapterInputSource
	 */
	public AdapterInputSource getAdapterInputSource()
	{
		return adapterInputSource;
	}

	/**
	 * @param adapterInputSource the adapterInputSource to set
	 */
	public void setAdapterInputSource(AdapterInputSource adapterInputSource)
	{
		this.adapterInputSource = adapterInputSource;
	}

	/**
	 * @return the eventTypeAlias
	 */
	public String getEventTypeAlias()
	{
		return eventTypeAlias;
	}

	/**
	 * @param eventTypeAlias the eventTypeAlias to set
	 */
	public void setEventTypeAlias(String eventTypeAlias)
	{
		this.eventTypeAlias = eventTypeAlias;
	}

	/**
	 * @return the eventsPerSec
	 */
	public Integer getEventsPerSec()
	{
		return eventsPerSec;
	}

	/**
	 * @return the looping
	 */
	public boolean isLooping()
	{
		return looping;
	}

	/**
	 * @return the propertyOrder
	 */
	public String[] getPropertyOrder()
	{
		return propertyOrder;
	}

	/**
	 * @return the propertyTypes
	 */
	public Map<String, Class> getPropertyTypes()
	{
		return propertyTypes;
	}
}
