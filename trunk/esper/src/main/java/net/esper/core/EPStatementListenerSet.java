package net.esper.core;

import net.esper.client.UpdateListener;
import net.esper.client.StatementAwareUpdateListener;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Provides update listeners for use by statement instances, and the management methods around these.
 * <p>
 * The collection of update listeners is based on copy-on-write:
 * When the engine dispatches events to a set of listeners, then while iterating through the set there
 * may be listeners added or removed (the listener may remove itself).
 * Additionally, events may be dispatched by multiple threads to the same listener.
 */
public class EPStatementListenerSet
{
    CopyOnWriteArraySet<UpdateListener> listeners;
    CopyOnWriteArraySet<StatementAwareUpdateListener> stmtAwareListeners;
    private transient EPStatementListenerSetCallback epStatementListenerSetCallback;
    

    /**
     * Ctor.
     */
    public EPStatementListenerSet()
    {
        listeners = new CopyOnWriteArraySet<UpdateListener>();
        stmtAwareListeners = new CopyOnWriteArraySet<StatementAwareUpdateListener>();
    }

    /**
     * Ctor.
     * @param listeners is a set of update listener
     * @param stmtAwareListeners is a set of statement-aware update listener
     */
    public EPStatementListenerSet(CopyOnWriteArraySet<UpdateListener> listeners, CopyOnWriteArraySet<StatementAwareUpdateListener> stmtAwareListeners)
    {
        this.listeners = listeners;
        this.stmtAwareListeners = stmtAwareListeners;
    }

    public void setEpStatementListenerSetCallback(EPStatementListenerSetCallback epStatementListenerSetCallback) {
        this.epStatementListenerSetCallback = epStatementListenerSetCallback;
    }

    /**
     * Returns the set of listeners to the statement.
     * @return statement listeners
     */
    public CopyOnWriteArraySet<UpdateListener> getListeners()
    {
        return listeners;
    }

    /**
     * Returns the set of statement-aware listeners.
     * @return statement-aware listeners
     */
    public CopyOnWriteArraySet<StatementAwareUpdateListener> getStmtAwareListeners()
    {
        return stmtAwareListeners;
    }

    /**
     * Set the update listener set to use.
     * @param listenerSet a collection of update listeners
     */
    public void setListeners(EPStatementListenerSet listenerSet)
    {
        this.listeners = listenerSet.getListeners();
        this.stmtAwareListeners = listenerSet.getStmtAwareListeners();
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
        }
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
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
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
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
        }
    }

    /**
     * Remove all listeners to a statement.
     */
    public void removeAllListeners()
    {
        listeners.clear();
        stmtAwareListeners.clear();
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
        }
    }

    /**
     * Add a listener to the statement.
     * @param listener to add
     */
    public void addListener(StatementAwareUpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        stmtAwareListeners.add(listener);
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
        }
    }

    /**
     * Remove a listeners to a statement.
     * @param listener to remove
     */
    public void removeListener(StatementAwareUpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        stmtAwareListeners.remove(listener);
        if (epStatementListenerSetCallback != null)
        {
            epStatementListenerSetCallback.newListenerSet(this);
        }
    }
}
