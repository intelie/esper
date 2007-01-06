package net.esper.core;

import java.util.Iterator;

import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.collection.Pair;
import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.ViewProcessingException;
import net.esper.view.Viewable;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.ManagedReadWriteLock;
import net.esper.util.ManagedLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Statement implementation for EQL statements.
 *
 * The statement starts on construction.
 * When listeners are added and removed from the view a child dispatch view is added/removed to/from the parent view
 * to support push mode.
 */
public class EPEQLStatementImpl extends EPStatementSupport implements EPStatement
{
    private final String expressionText;
    private final UpdateDispatchView dispatchChildView;
    private final EPEQLStmtStartMethod startMethod;
    private final ManagedReadWriteLock eventProcessingRWLock;
    private final ManagedLock statementLock;

    private Viewable parentView;
    private EPStatementStopMethod stopMethod;

    /**
     * Ctor.
     * @param expressionText expression
     * @param dispatchService for dispatching
     * @param startMethod to start the view
     */
    public EPEQLStatementImpl(String expressionText, DispatchService dispatchService,
                               EPEQLStmtStartMethod startMethod,
                               ManagedReadWriteLock eventProcessingRWLock,
                               ManagedLock statementLock)
    {
        this.expressionText = expressionText;
        this.dispatchChildView = new UpdateDispatchView(this.getListeners(), dispatchService);
        this.startMethod = startMethod;
        this.eventProcessingRWLock = eventProcessingRWLock;
        this.statementLock = statementLock;

        start();
    }

    public void stop()
    {
        if (stopMethod == null)
        {
            throw new IllegalStateException("View statement already stopped");
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            if (!this.getListeners().isEmpty())
            {
                parentView.removeView(dispatchChildView);
            }

            stopMethod.stop();
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }
        stopMethod = null;
        parentView = null;
    }

    public void start()
    {
        if (stopMethod != null)
        {
            throw new IllegalStateException("View statement already started");
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            Pair<Viewable, EPStatementStopMethod> pair;
            try
            {
                pair = startMethod.start();
            }
            catch (ExprValidationException ex)
            {
                log.debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.getMessage(), expressionText);
            }
            catch (ViewProcessingException ex)
            {
                log.debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.getMessage(), expressionText);
            }

            parentView = pair.getFirst();
            stopMethod = pair.getSecond();

            if (!this.getListeners().isEmpty())
            {
                parentView.addView(dispatchChildView);
            }
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }
    }

    public String getText()
    {
        return expressionText;
    }

    public Iterator<EventBean> iterator()
    {
        // Return null if not started
        if (parentView == null)
        {
            return null;
        }
        return parentView.iterator();
    }

    public EventType getEventType()
    {
        return parentView.getEventType();
    }

    public void listenerStop()
    {
        if (parentView != null)
        {
            statementLock.acquireLock();
            try
            {
                parentView.removeView(dispatchChildView);
            }
            catch (RuntimeException ex)
            {
                throw ex;
            }
            finally
            {
                statementLock.releaseLock();
            }
        }
    }

    public void listenerStart()
    {
        if (parentView != null)
        {
            statementLock.acquireLock();
            try
            {
                parentView.addView(dispatchChildView);
            }
            catch (RuntimeException ex)
            {
                throw ex;
            }
            finally
            {
                statementLock.releaseLock();
            }
        }
    }

    private static Log log = LogFactory.getLog(EPEQLStatementImpl.class);
}
