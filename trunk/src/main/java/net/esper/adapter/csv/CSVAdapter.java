package net.esper.adapter.csv;

import net.esper.adapter.AdapterInputSource;
import net.esper.client.EPException;

/**
 * CSVAdapter contains convenience methods for creating CSVFeeds.
 */
public interface CSVAdapter
{
	/**
	 * Create and start a CSVFeed. 
	 * @param adapterInputSource - the source of the CSV file
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @throws EPException if there is an error creating the Feed
	 */
	public void startFeed(AdapterInputSource adapterInputSource,
			String eventTypeAlias) throws EPException;
}