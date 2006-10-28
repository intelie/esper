package net.esper.adapter.csv;

import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedFactory;
import net.esper.client.EPException;

/**
 * An implementation of CSVAdapter.
 */
public class CSVAdapterImpl implements CSVAdapter
{
	private final FeedFactory feedCreator;
	/**
	 * Ctor.
	 * @param feedCreator - the creator to use for creating the Feeds
	 */
	public CSVAdapterImpl(FeedFactory feedCreator)
	{
		this.feedCreator = feedCreator;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.csv.CSVAdapter#startFeed(net.esper.adapter.AdapterInputSource, java.lang.String)
	 */
	public void startFeed(AdapterInputSource adapterInputSource, String eventTypeAlias) throws EPException
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(adapterInputSource, eventTypeAlias);
		feedSpec.setUsingEngineThread(false);
		Feed feed = feedCreator.createFeed(feedSpec);
		feed.start();
	}

	/* (non-Javadoc)
	 * @see net.esper.adapter.csv.CSVAdapter#startFeed(net.esper.adapter.AdapterInputSource, java.lang.String, java.util.Map)
	 */
	public void startFeed(AdapterInputSource adapterInputSource, String eventTypeAlias, Map<String, Class> propertyTypes) throws EPException
	{
		CSVFeedSpec feedSpec = new CSVFeedSpec(adapterInputSource, eventTypeAlias);
		feedSpec.setPropertyTypes(propertyTypes);
		feedSpec.setUsingEngineThread(false);
		Feed feed = feedCreator.createFeed(feedSpec);
		feed.start();
	}
	
	
}
