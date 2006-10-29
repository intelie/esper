package net.esper.adapter;

import net.esper.adapter.csv.CSVAdapterManager;

/** 
 * A manager for different input adapters.
 */
public interface EPAdapterManager
{
	/**
	 * Create a new Adapter.
	 * @param adapterSpec - the parameters for this Adapter
	 * @return the newly created Adapter
	 */
	public Adapter createAdapter(AdapterSpec adapterSpec);
	
	/**
	 * Create an instance of AdapterCoordinator.
	 * @param usingEngineThread - true if the coordinator should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread 
	 * @return a new instance of AdapterCoordinator
	 */
	public AdapterCoordinatorImpl createAdapterCoordinator(boolean usingEngineThread);

	/**
	 * Get the adapter for CSV files.
	 * @return the CSVAdapterManager
	 */
	public CSVAdapterManager getCSVAdapter();
}