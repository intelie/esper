package net.esper.core;

import net.esper.client.EPStatementState;
import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.client.EPServiceProvider;
import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.Viewable;

import java.util.Iterator;

/**
 * Statement implementation for EQL statements.
 */
public class EPStatementImpl implements EPStatementSPI
{
    private final EPStatementListenerSet statementListenerSet;
    private final String statementId;
    private final String statementName;
    private final String expressionText;
    private boolean isPattern;
    private UpdateDispatchViewBase dispatchChildView;
    private StatementLifecycleSvc statementLifecycleSvc;

    private long timeLastStateChange;
    private Viewable parentView;
    private EPStatementState currentState;
    private EventType eventType;

    /**
     * Ctor.
     * @param statementId is a unique ID assigned by the engine for the statement
     * @param statementName is the statement name assigned during creation, or the statement id if none was assigned
     * @param expressionText is the EQL and/or pattern expression
     * @param isPattern is true to indicate this is a pure pattern expression
     * @param dispatchService for dispatching events to listeners to the statement
     * @param statementLifecycleSvc handles lifecycle transitions for the statement
     * @param isBlockingDispatch is true if the dispatch to listeners should block to preserve event generation order
     * @param msecBlockingTimeout is the max number of milliseconds of block time
     * @param epServiceProvider is the engine instance to provide to statement-aware update listeners
     * @param timeLastStateChange the timestamp the statement was created and started
     */
    public EPStatementImpl(EPServiceProvider epServiceProvider,
                           String statementId,
                              String statementName,
                              String expressionText,
                              boolean isPattern,
                              DispatchService dispatchService,
                              StatementLifecycleSvc statementLifecycleSvc,
                              long timeLastStateChange,
                              boolean isBlockingDispatch,
                              long msecBlockingTimeout)
    {
        this.isPattern = isPattern;
        this.statementId = statementId;
        this.statementName = statementName;
        this.expressionText = expressionText;
        this.statementLifecycleSvc = statementLifecycleSvc;
        statementListenerSet = new EPStatementListenerSet();
        if (isBlockingDispatch)
        {
            this.dispatchChildView = new UpdateDispatchViewBlocking(epServiceProvider, this, statementListenerSet, dispatchService, msecBlockingTimeout);
        }
        else
        {
            this.dispatchChildView = new UpdateDispatchViewNonBlocking(epServiceProvider, this, statementListenerSet, dispatchService);
        }
        this.currentState = EPStatementState.STOPPED;
        this.timeLastStateChange = timeLastStateChange;
    }

    public String getStatementId()
    {
        return statementId;
    }

    public void start()
    {
        if (statementLifecycleSvc == null)
        {
            throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
        }
        statementLifecycleSvc.start(statementId);
    }

    public void stop()
    {
        if (statementLifecycleSvc == null)
        {
            throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
        }
        statementLifecycleSvc.stop(statementId);

        // On stop, we give the dispatch view a chance to dispatch final results, if any
        dispatchChildView.dispatchOnStop();
        
        dispatchChildView.clear();
    }

    public void destroy()
    {
        if (currentState == EPStatementState.DESTROYED)
        {
            throw new IllegalStateException("Statement already destroyed");
        }
        statementLifecycleSvc.destroy(statementId);
        parentView = null;
        eventType = null;
        dispatchChildView = null;
        statementLifecycleSvc = null;
    }

    public EPStatementState getState()
    {
        return currentState;
    }

    public void setCurrentState(EPStatementState currentState, long timeLastStateChange)
    {
        this.currentState = currentState;
        this.timeLastStateChange = timeLastStateChange;
    }

    public void setParentView(Viewable viewable)
    {
        if (viewable == null)
        {
            parentView.removeView(dispatchChildView);
            parentView = null;
        }
        else
        {
            parentView = viewable;
            parentView.addView(dispatchChildView);
            eventType = parentView.getEventType();
        }
    }

    public String getText()
    {
        return expressionText;
    }

    public String getName()
    {
        return statementName;
    }

    public Iterator<EventBean> iterator()
    {
        // Return null if not started
        if (parentView == null)
        {
            return null;
        }
        if (isPattern)
        {
            return dispatchChildView.iterator();        // Which simply keeps the last event
        }
        else
        {
            return parentView.iterator();
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns the set of listeners to the statement.
     * @return statement listeners
     */
    public EPStatementListenerSet getListenerSet()
    {
        return statementListenerSet;
    }

    public void setListeners(EPStatementListenerSet listenerSet)
    {
        statementListenerSet.setListeners(listenerSet);
        if (dispatchChildView != null)
        {
            dispatchChildView.setUpdateListeners(listenerSet);
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

        statementListenerSet.addListener(listener);
        statementLifecycleSvc.updatedListeners(statementId, statementName, statementListenerSet);
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

        statementListenerSet.removeListener(listener);
        statementLifecycleSvc.updatedListeners(statementId, statementName, statementListenerSet);
    }

    /**
     * Remove all listeners to a statement.
     */
    public void removeAllListeners()
    {
        statementListenerSet.removeAllListeners();
        statementLifecycleSvc.updatedListeners(statementId, statementName, statementListenerSet);
    }

    public void addListener(StatementAwareUpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        statementListenerSet.addListener(listener);
        statementLifecycleSvc.updatedListeners(statementId, statementName, statementListenerSet);
    }

    public void removeListener(StatementAwareUpdateListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Null listener reference supplied");
        }

        statementListenerSet.removeListener(listener);
        statementLifecycleSvc.updatedListeners(statementId, statementName, statementListenerSet);
    }

    public Iterator<StatementAwareUpdateListener> getStatementAwareListeners()
    {
        return statementListenerSet.getStmtAwareListeners().iterator();
    }

    public Iterator<UpdateListener> getUpdateListeners()
    {
        return statementListenerSet.getListeners().iterator();
    }

    public long getTimeLastStateChange()
    {
        return timeLastStateChange;
    }

    public boolean isStarted()
    {
        return currentState == EPStatementState.STARTED;
    }

    public boolean isStopped()
    {
        return currentState == EPStatementState.STOPPED;
    }

    public boolean isDestroyed()
    {
        return currentState == EPStatementState.DESTROYED;
    }
}
