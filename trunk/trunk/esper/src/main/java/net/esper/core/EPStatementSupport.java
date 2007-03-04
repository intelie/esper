package net.esper.core;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import net.esper.client.EPListenable;
import net.esper.client.UpdateListener;

/**
 * Base class for an EPStatement - provides listener registration functions.
 */
public abstract class EPStatementSupport implements EPListenable
{
    /**
     * Using a copy-on-write set here:
     * When the engine dispatches events to a set of listeners, then while iterating through the set there
     * may be listeners added or removed (the listener may remove itself).
     * Additionally, events may be dispatched by multiple threads to the same listener.
     */
    private Set<UpdateListener> listeners = new CopyOnWriteArraySet<UpdateListener>();

    /**
     * Returns the set of listeners to the statement.
     * @return statement listeners
     */
    protected Set<UpdateListener> getListeners()
    {
        return listeners;
    }

    /**
     * Add a listener to the statement.
     * @param listener to add
     */
    public void addListener(UpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        listeners.add(listener);
    }

    /**
     * Remove a listeners to a statement.
     * @param listener to remove
     */
    public void removeListener(UpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        listeners.remove(listener);
    }

    /**
     * Remove all listeners to a statement.
     */
    public void removeAllListeners()
    {
        listeners.clear();
    }
}
