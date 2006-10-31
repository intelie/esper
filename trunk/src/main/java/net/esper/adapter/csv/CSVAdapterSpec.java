package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.AdapterSpec;
import net.esper.adapter.AdapterType;

/**
 * A spec for CSVAdapters.
 */
public class CSVAdapterSpec implements AdapterSpec
{
	private final Map<String, Object> parameters = new HashMap<String, Object>();
	
	/**
	 * Ctor.
	 * @param adapterInputSource - the source for the CSV data
	 * @param eventTypeAlias - the alias for the event type created from the CSV data
	 */
	public CSVAdapterSpec(AdapterInputSource adapterInputSource, String eventTypeAlias)
	{
		parameters.put("adapterInputSource", adapterInputSource);
		parameters.put("eventTypeAlias", eventTypeAlias);
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.AdapterSpec#getAdapterType()
	 */
	public AdapterType getAdapterType()
	{
		return AdapterType.CSV;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.AdapterSpec#getParameter(java.lang.String)
	 */
	public Object getParameter(String parameterName)
	{
		return parameters.get(parameterName);
	}
	
	/**
	 * @param eventsPerSec
	 */
	public void setEventsPerSec(int eventsPerSec)
	{
		parameters.put("eventsPerSec", eventsPerSec);
	}

	/**
	 * @param propertyOrder - the propertyOrder to set
	 */
	public void setPropertyOrder(String[] propertyOrder)
	{
		parameters.put("propertyOrder", propertyOrder);
	}

	/**
	 * @param looping - the isLooping value to set
	 */
	public void setLooping(boolean looping)
	{
		parameters.put("looping", looping);
	}
	
	/**
	 * Set the propertyTypes value
	 * @param propertyTypes - a mapping between the names and types of the properties in the
	 *  					  CSV file; this will also be the form of the Map event created
	 *  					  from the data
	 */
	public void setPropertyTypes(Map<String, Class> propertyTypes)
	{
		parameters.put("propertyTypes", propertyTypes);
	}
	
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.AdapterSpec#setUsingEngineThread(boolean)
	 */
	public void setUsingEngineThread(boolean usingEngineThread)
	{
		parameters.put("usingEngineThread", usingEngineThread);
	}
	
	/**
	 * Set the timestamp column name.
	 * @param timestampColumn - the name of the column to use for timestamps
	 */
	public void setTimestampColumn(String timestampColumn)
	{
		parameters.put("timestampColumn", timestampColumn);
	}
}
