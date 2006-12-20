package net.esper.core;

import java.util.*;

import net.esper.client.EPListenable;
import net.esper.client.UpdateListener;

/**
 * Base class for an EPStatement - provides listener registration functions.
 */
public abstract class EPStatementSupport implements EPListenable
{
    private Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();

    /**
     * Called when the last listener is removed.
     */
    public abstract void listenerStop();

    /**
     * Called when the first listener is added.
     */
    public abstract void listenerStart();

    /**
     * Returns the set of listeners to the statement.
     * @return statement listeners
     */
    public Set<UpdateListener> getListeners()
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
        if (listeners.size() == 1)
        {
            listenerStart();
        }
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
        if (listeners.size() == 0)
        {
            listenerStop();
        }
    }

    /**
     * Remove all listeners to a statement.
     */
    public void removeAllListeners()
    {
        listeners.clear();
        listenerStop();
    }
}
