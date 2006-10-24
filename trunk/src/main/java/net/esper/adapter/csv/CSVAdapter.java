package net.esper.adapter.csv;

import java.util.Map;

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
	
	/**
	 * Create and start a CSVFeed. 
	 * @param adapterInputSource - the source of the CSV file
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @param propertyTypes - the names and types of the properties in the Map event to send into the runtime
	 * @throws EPException if there is an error creating the Feed
	 */
	public void startFeed(AdapterInputSource adapterInputSource,
						  String eventTypeAlias, 
						  Map<String, Class> propertyTypes) throws EPException;
}