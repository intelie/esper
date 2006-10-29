package net.esper.adapter;

/**
 * A wrapper for the parameters for a Adapter.
 */
public interface AdapterSpec
{
	/**
	 * Get the type of the Adapter this AdapterSpec is specifying.
	 * @return the AdapterType
	 */
	public AdapterType getAdapterType();
	
	/**
	 * Get a specific parameter for this Adapter.
	 * @param parameterName - the name of the parameter
	 * @return the parameter value, or null if the parameter wasn't set
	 */
	public Object getParameter(String parameterName);

	/**
	 * Set the usingEngineThread value.
	 * @param usingEngineThread - true if the Adapter should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread
	 */
	public void setUsingEngineThread(boolean usingEngineThread);
}
