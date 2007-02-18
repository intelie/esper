package net.esper.adapter;

import net.esper.client.*;

/**
 * An Adapter takes some external data, converts it into events,
 * and sends it into the runtime engine.
 */
public interface Adapter
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
     * Resume sending events after the Adapter has been paused.
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
     * Destroy the Adapter, stopping the sending of all events and releasing all the
     * resources, and disallowing any further state changes on the Adapter.
     */
    public void destroy() throws EPException;

    /**
     * Get the state of this Adapter.
     * @return state
     */
    public AdapterState getState();
}
