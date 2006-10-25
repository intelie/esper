package net.esper.core;

import java.util.*;

import net.esper.client.EPListenable;
import net.esper.client.UpdateListener;
import net.esper.persist.LogContextNode;
import net.esper.persist.LogContextChangedCallback;
import net.esper.client.logstate.LogEntryType;

/**
 * Base class for an EPStatement - provides listener registration functions.
 */
public abstract class EPStatementSupport implements EPListenable, LogContextChangedCallback
{
    private LogContextNode<Set<UpdateListener>> listenerState;

    protected EPStatementSupport(LogContextNode<String> statementLogContext)
    {
        Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();
        listenerState = statementLogContext.createChild(LogEntryType.SUBSCRIPTION, listeners);
        listenerState.setStateChangedCallback(this);
    }

    public void updated()
    {
        if (listenerState.getState().size() > 0)
        {
            listenerStart();
        }
    }

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
        return Collections.unmodifiableSet(listenerState.getState());
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

        listenerState.getState().add(listener);
        listenerState.update();
        if (listenerState.getState().size() == 1)
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

        listenerState.getState().remove(listener);
        listenerState.update();
        if (listenerState.getState().size() == 0)
        {
            listenerStop();
        }
    }

    /**
     * Remove all listeners to a statement.
     */
    public void removeAllListeners()
    {
        listenerState.getState().clear();
        listenerState.update();
        listenerStop();
    }

    public LogContextNode<Set<UpdateListener>> getListenerState()
    {
        return listenerState;
    }
}
