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

    private Viewable parentView;
    private EPStatementStopMethod stopMethod;

    /**
     * Ctor.
     * @param expressionText expression
     * @param dispatchService for dispatching
     * @param startMethod to start the view
     */
    public EPEQLStatementImpl(String expressionText, DispatchService dispatchService,
                               EPEQLStmtStartMethod startMethod)
    {
        this.expressionText = expressionText;
        this.dispatchChildView = new UpdateDispatchView(this.getListeners(), dispatchService);
        this.startMethod = startMethod;

        start();
    }

    public void stop()
    {
        if (stopMethod == null)
        {
            throw new IllegalStateException("View statement already stopped");
        }

        if (!this.getListeners().isEmpty())
        {
            parentView.removeView(dispatchChildView);
        }

        stopMethod.stop();
        stopMethod = null;
        parentView = null;
    }

    public void start()
    {
        if (stopMethod != null)
        {
            throw new IllegalStateException("View statement already started");
        }

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
            parentView.removeView(dispatchChildView);
        }
    }

    public void listenerStart()
    {
        if (parentView != null)
        {
            parentView.addView(dispatchChildView);
        }
    }

    private static Log log = LogFactory.getLog(EPEQLStatementImpl.class);
}
