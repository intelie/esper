package net.esper.adapter;

import net.esper.client.EPException;

/**
 * A Feed takes some external data, converts it into events, 
 * and sends it into the runtime engine.
 */
public interface Feed
{
	/**
	 * Start the sending of events into the runtime egine.
	 * @throws EPException in case of errors processing the events
	 */
	public void start() throws EPException;

	/**
	 * Pause the sending of events after a Feed has been started.
	 * @throws EPException if this Player has already been stopped
	 */
	public void pause() throws EPException;

	/**
	 * Resume sending events after the Feed has been paused.
	 * @throws EPException in case of errors processing the events
	 */
	public void resume() throws EPException;

	/**
	 * Stop sending events and return the Feed to the OPENED state, ready to be started
	 * once again.
	 * @throws EPException in case of errors releasing resources
	 */
	public void stop() throws EPException;

	/**
	 * Destroy the Feed, stopping the sending of all events and releasing all the 
	 * resources, and disallowing any further state changes on the Feed.
	 */
	public void destroy() throws EPException;
	
	/**
	 * Get the state of this Feed.
	 * @return state
	 */
	public FeedState getState();
}