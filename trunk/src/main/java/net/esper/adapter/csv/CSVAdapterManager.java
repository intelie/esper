package net.esper.adapter.csv;

import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.client.EPException;

/**
Adapter
 */
public interface CSVAdapterManager
{
	/**
	 * Create and start a CSVAdapter. 
	 * @param adapterInputSource - the source of the CSV file
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @throws EPException if there is an error creating the Adapter
	 */
	public void start(AdapterInputSource adapterInputSource,
						  String eventTypeAlias) throws EPException;
	
	/**
	 * Create and start a CSVAdapter. 
	 * @param adapterInputSource - the source of the CSV file
	 * @param eventTypeAlias - the alias for the map events generated from the CSV file
	 * @param propertyTypes - the names and types of the properties in the Map event to send into the runtime
	 * @throws EPException if there is an error creating the Adapter
	 */
	public void start(AdapterInputSource adapterInputSource,
						  String eventTypeAlias, 
						  Map<String, Class> propertyTypes) throws EPException;
}