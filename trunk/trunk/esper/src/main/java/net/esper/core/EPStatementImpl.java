package net.esper.core;

import net.esper.client.EPStatement;
import net.esper.client.EPStatementState;
import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.Viewable;

import java.util.Iterator;

/**
 * Statement implementation for EQL statements.
 */
public class EPStatementImpl extends EPStatementSupport implements EPStatement
{
    private final String statementId;
    private final String statementName;
    private final String expressionText;
    private boolean isPattern;
    private UpdateDispatchView dispatchChildView;
    private StatementLifecycleSvc statementLifecycleSvc;

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
     */
    public EPStatementImpl(String statementId,
                              String statementName,
                              String expressionText,
                              boolean isPattern,
                              DispatchService dispatchService,
                              StatementLifecycleSvc statementLifecycleSvc)
    {
        this.isPattern = isPattern;
        this.statementId = statementId;
        this.statementName = statementName;
        this.expressionText = expressionText;
        this.statementLifecycleSvc = statementLifecycleSvc;
        this.dispatchChildView = new UpdateDispatchView(this.getListeners(), dispatchService);
        this.currentState = EPStatementState.STOPPED;
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
        dispatchChildView.clear();
    }

    public void destroy()
    {
        if (currentState == EPStatementState.DESTROYED)
        {
            throw new IllegalStateException("Statement already destroyed");
        }
        statementLifecycleSvc.destroy(statementId);
    }

    public void initialize()
    {
        parentView = null;
        eventType = null;
        dispatchChildView = null;
        statementLifecycleSvc = null;
    }

    public EPStatementState getState()
    {
        return currentState;
    }

    public void setCurrentState(EPStatementState currentState)
    {
        this.currentState = currentState;
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
}
