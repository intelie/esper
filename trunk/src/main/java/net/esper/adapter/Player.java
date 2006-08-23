package net.esper.adapter;

import net.esper.client.EPException;

/**
 * An interface that represents a running input adapter.
 */
public interface Player
{

	/**
	 * Start the sending of events into the EPRuntime.
	 * @throws EPException in case of errors processing the events
	 */
	public void start() throws EPException;

	/**
	 * Stop the sending of events and release all resources.
	 * @throws EPException in case of errors releasing resources
	 */
	public void stop() throws EPException;

	/**
	 * Pause the sending of events.
	 * @throws EPException if this Player has already been stopped
	 */
	public void pause() throws EPException;

	/**
	 * Resume sending events after the player has been paused.
	 * @throws EPException in case of errors processing the events
	 */
	public void resume() throws EPException;

}