package net.esper.adapter;

import net.esper.client.EPException;

/**
 * A utility for creating a Feed from a FeedSpec.
 */
public interface FeedFactory
{
	/**
	 * Create a new Feed.
	 * @param feedSpec - the parameters for the Feed
	 * @return the created Feed
	 * @throws EPException in case of errors creating the Feed
	 */
	public Feed createFeed(FeedSpec feedSpec) throws EPException;
}