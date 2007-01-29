package net.esper.adapter;

import net.esper.client.EPException;

/**
 * An InputAdapter takes some external data, converts it into events, 
 * and sends it into the runtime engine.
 */
public interface InputAdapter
{
	/**
	 * Start the sending of events into the runtime egine.
	 * @throws EPException in case of errors processing the events
	 */
	public void start() throws EPException;

	/**
	 * Pause the sending of events after a Adapter has been started.
	 * @throws EPException if this Adapter has already been stopped
	 */
	public void pause() throws EPException;

	/**
	 * Resume sending events after the InputAdapter has been paused.
	 * @throws EPException in case of errors processing the events
	 */
	public void resume() throws EPException;

	/**
	 * Stop sending events and return the Adapter to the OPENED state, ready to be started
	 * once again.
	 * @throws EPException in case of errors releasing resources
	 */
	public void stop() throws EPException;

	/**
	 * Destroy the InputAdapter, stopping the sending of all events and releasing all the 
	 * resources, and disallowing any further state changes on the InputAdapter.
	 */
	public void destroy();
	
	/**
	 * Get the state of this InputAdapter.
	 * @return state
	 */
	public AdapterState getState();
}