package net.esper.core;

import net.esper.client.EPStatement;
import net.esper.pattern.PatternMatchCallback;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventAdapterService;
import net.esper.collection.SingleEventIterator;
import net.esper.dispatch.DispatchService;
import net.esper.util.ManagedReadWriteLock;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Statement for patterns.
 */
public class EPPatternStatementImpl extends EPStatementSupport implements PatternMatchCallback, EPStatement
{
    private final String expressionText;
    private final EventType eventType;
    private final DispatchService dispatchService;
    private final EventAdapterService eventAdapterService;
    private final EPPatternStmtStartMethod startMethod;
    private final ManagedReadWriteLock eventProcessingRWLock;
    private final ThreadLocal<PatternListenerDispatch> dispatchThreadLocal;

    private EPStatementStopMethod stopMethod;

    private EventBean lastEvent;

    /**
     * Constructor.
     * @param expressionText - expression
     * @param eventType - event type of events the pattern will fire
     * @param dispatchService - service for dispatching events
     * @param eventAdapterService - service for generating events or event wrappers and types
     * @param startMethod - method to start the pattern
     * @param eventProcessingRWLock - lock for statement create/start/stop across engine instance competing with events
     */
    public EPPatternStatementImpl(String expressionText,
                                  EventType eventType,
                                  DispatchService dispatchService,
                                  EventAdapterService eventAdapterService,
                                  EPPatternStmtStartMethod startMethod,
                                  ManagedReadWriteLock eventProcessingRWLock)
    {
        this.expressionText = expressionText;
        this.eventType = eventType;
        this.dispatchService = dispatchService;
        this.eventAdapterService = eventAdapterService;
        this.startMethod = startMethod;
        this.eventProcessingRWLock = eventProcessingRWLock;

        dispatchThreadLocal = new ThreadLocal<PatternListenerDispatch>() {
            protected synchronized PatternListenerDispatch initialValue() {
                return new PatternListenerDispatch(EPPatternStatementImpl.this.getListeners());
            }
        };

        start();
    }

    public void matchFound(Map<String, EventBean> matchEvent)
    {
        PatternListenerDispatch dispatch = dispatchThreadLocal.get();
        if (log.isDebugEnabled())
        {
            log.debug(".matchFound Listeners=" + getListeners().size() + "  dispatch=" + dispatch);
        }

        EventBean aggregateEvent = eventAdapterService.createMapFromUnderlying(matchEvent, eventType);
        lastEvent = aggregateEvent;

        if (!getListeners().isEmpty())
        {
            // The dispatch has no data after initialization and after it fired
            if (!(dispatch.hasData()))
            {
                dispatchService.addExternal(dispatch);
            }
            dispatch.add(aggregateEvent);
        }
    }

    public void stop()
    {
        if (stopMethod == null)
        {
            throw new IllegalStateException("Pattern statement already stopped");
        }

        eventProcessingRWLock.acquireWriteLock();
        try
        {
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
        lastEvent = null;
    }

    public void start()
    {
        if (stopMethod != null)
        {
            throw new IllegalStateException("Pattern statement already started");
        }

        eventProcessingRWLock.acquireWriteLock();
        try
        {
            stopMethod = startMethod.start((PatternMatchCallback) this);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();            
        }

        // Since the pattern start itself may have generated an event, dispatch
        dispatchService.dispatch();
    }

    public Iterator<EventBean> iterator()
    {
        if (stopMethod != null)
        {
            // When started, return iterator even if lastEvent is null (no event received, hasNext returns false)
            return new SingleEventIterator(lastEvent);
        }
        else
        {
            // When not started, no iterator is available
            return null;
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public String getText()
    {
        return expressionText;
    }

    private static final Log log = LogFactory.getLog(EPPatternStatementImpl.class);
}
