package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.FeedSpec;
import net.esper.adapter.FeedType;

/**
 * A FeedSpec for CSVFeeds.
 */
public class CSVFeedSpec implements FeedSpec
{
	private final Map<String, Object> parameters = new HashMap<String, Object>();
	
	/**
	 * Ctor.
	 * @param adapterInputSource - the source for the CSV data
	 * @param eventTypeAlias - the alias for the event type created from the CSV data
	 */
	public CSVFeedSpec(AdapterInputSource adapterInputSource, String eventTypeAlias)
	{
		parameters.put("adapterInputSource", adapterInputSource);
		parameters.put("eventTypeAlias", eventTypeAlias);
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.FeedSpec#getFeedType()
	 */
	public FeedType getFeedType()
	{
		return FeedType.CSV;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.FeedSpec#getParameter(java.lang.String)
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
	 * @param eventTypeAlias - the eventTypeAlias to set
	 */
	public void setEventTypeAlias(String eventTypeAlias)
	{
		parameters.put("eventTypeAlias", eventTypeAlias);
	}

	/**
	 * @param isLooping - the isLooping value to set
	 */
	public void setIsLooping(boolean isLooping)
	{
		parameters.put("isLooping", isLooping);
	}
}
