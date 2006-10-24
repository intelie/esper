package net.esper.adapter;

/**
 * A wrapper for the parameters for a Feed.
 */
public interface FeedSpec
{
	/**
	 * Get the type of the Feed this FeedSpec is specifying.
	 * @return the FeedType
	 */
	public FeedType getFeedType();
	
	/**
	 * Get a specific parameter for this feed.
	 * @param parameterName - the name of the parameter
	 * @return the parameter value, or null if the parameter wasn't set
	 */
	public Object getParameter(String parameterName);

	/**
	 * Set the usingEngineThread value.
	 * @param usingEngineThread - true if the Feed should set time by the scheduling service in the engine, 
	 *                            false if it should set time externally through the calling thread
	 */
	public void setUsingEngineThread(boolean usingEngineThread);
}
