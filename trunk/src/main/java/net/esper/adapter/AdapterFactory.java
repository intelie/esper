package net.esper.adapter;

import net.esper.client.EPException;

/**
 * A utility for creating a Adapter from a AdapterSpec.
 */
public interface AdapterFactory
{
	/**
	 * Create a new Adapter.
	 * @param adapterSpec - the parameters for the Adapter
	 * @return the created Adapter
	 * @throws EPException in case of errors creating the Adapter
	 */
	public Adapter createAdapter(AdapterSpec adapterSpec) throws EPException;
}