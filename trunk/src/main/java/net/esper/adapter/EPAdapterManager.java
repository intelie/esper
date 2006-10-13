package net.esper.adapter;

import net.esper.adapter.csv.CSVAdapter;

/** 
 * A manager for different input adapters.
 */
public interface EPAdapterManager
{
	/**
	 * Create a new Feed.
	 * @param feedSpec - the parameters for this feed
	 * @return the newly created feed
	 */
	public Feed createFeed(FeedSpec feedSpec);
	
	/**
	 * Create an instance of FeedCoordinator.
	 * @return a new instance of FeedCoordinator
	 */
	public FeedCoordinatorImpl createFeedCoordinator();

	/**
	 * Get the adapter for CSV files.
	 * @return the CSVAdapter
	 */
	public CSVAdapter getCSVAdapter();
}