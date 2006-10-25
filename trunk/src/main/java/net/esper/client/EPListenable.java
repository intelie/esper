package net.esper.client;

import java.util.Set;

/**
 * Interface to add and remove update listeners.
 */
public interface EPListenable
{
    /**
     * Add an listener that observes events.
     * @param listener to add
     */
    public void addListener(UpdateListener listener);

    /**
     * Remove an listener that observes events.
     * @param listener to remove
     */
    public void removeListener(UpdateListener listener);

    /**
     * Remove all listeners.
     */
    public void removeAllListeners();

    public Set<UpdateListener> getListeners();
}

