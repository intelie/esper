package net.esper.adapter;

/**
 * A FeedCoordinator coordinates several Feeds so that the events they 
 * send into the runtime engine arrive in some well-defined order, in
 * effect making the several Feeds into one large sending Feed.
 */
public interface FeedCoordinator extends Feed
{
	/**
	 * Coordinate a new feed.
	 * @param feedSpec - the parameters for the Feed
	 */
	public void coordinate(FeedSpec feedSpec);
}