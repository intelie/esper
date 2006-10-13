package net.esper.adapter.csv;

import java.util.HashSet;
import java.util.Set;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.FeedCreator;
import net.esper.client.EPException;

/**
 * An implementation of CSVAdapter.
 */
public class CSVAdapterImpl implements CSVAdapter
{
	private final FeedCreator feedCreator;
	private final Set<Feed> feeds = new HashSet<Feed>();
	
	/**
	 * Ctor.
	 * @param feedCreator - the creator to use for creating the Feeds
	 */
	public CSVAdapterImpl(FeedCreator feedCreator)
	{
		this.feedCreator = feedCreator;
	}
	
	/* (non-Javadoc)
	 * @see net.esper.adapter.csv.CSVAdapter#startFeed(net.esper.adapter.AdapterInputSource, java.lang.String)
	 */
	public void startFeed(AdapterInputSource adapterInputSource, String eventTypeAlias) throws EPException
	{
		Feed feed = feedCreator.createFeed(new CSVFeedSpec(adapterInputSource, eventTypeAlias));
		feeds.add(feed);
		feed.start();
	}
}
