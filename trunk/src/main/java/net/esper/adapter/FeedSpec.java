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
}
