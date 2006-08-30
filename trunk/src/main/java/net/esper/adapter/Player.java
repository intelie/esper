package net.esper.adapter;

import net.esper.client.EPException;

/**
 * An interface that represents a runnable input adapter.
 */
public interface Player
{
	public enum State { NEW, RUNNING, PAUSED, STOPPED };

	/**
	 * Start the sending of events into the EPRuntime.
	 * @throws EPException in case of errors processing the events
	 */
	public void start() throws EPException;

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

	/**
	 * Stop the sending of events and release all resources.
	 * @throws EPException in case of errors releasing resources
	 */
	public void stop() throws EPException;

	/**
	 * Get the next event in line to be sent into the runtime , or null if there is no available event.
	 * @return an instance of SendableEvent that wraps the next event to send, or null if none
	 * @throws EPException in case of errors creating the event
	 */
	public SendableEvent read() throws EPException;

	/**
	 * Get the state of this Player.
	 * @return state
	 */
	public State getState();

}