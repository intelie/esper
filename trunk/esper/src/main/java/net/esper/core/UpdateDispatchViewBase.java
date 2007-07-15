package net.esper.core;

import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;
import net.esper.collection.SingleEventIterator;
import net.esper.dispatch.DispatchService;
import net.esper.dispatch.Dispatchable;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public abstract class UpdateDispatchViewBase extends ViewSupport implements Dispatchable
{
    private final EPServiceProvider epServiceProvider;
    private final EPStatement statement;
    private EPStatementListenerSet statementListenerSet;

    /**
     * Dispatches events to listeners.
     */
    protected final DispatchService dispatchService;

    /**
     * For iteration with patterns.
     */
    protected EventBean lastIterableEvent;

    /**
     * Flag to indicate we have registered a dispatch.
     */
    protected ThreadLocal<Boolean> isDispatchWaiting = new ThreadLocal<Boolean>() {
        protected synchronized Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    /**
     * Buffer for holding dispatchable events.
     */
    protected ThreadLocal<LinkedList<EventBean[]>> lastNewEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
        }
    };
    /**
     * Buffer for holding dispatchable events.
     */
    protected ThreadLocal<LinkedList<EventBean[]>> lastOldEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
        }
    };

    /**
     * Ctor.
     * @param epServiceProvider - the engine instance to provided to statement-aware listener
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     * @param statement is the statement to supply to statement-aware listener
     */
    public UpdateDispatchViewBase(EPServiceProvider epServiceProvider, EPStatement statement, EPStatementListenerSet updateListeners, DispatchService dispatchService)
    {
        this.epServiceProvider = epServiceProvider;
        this.statement = statement;
        this.statementListenerSet = updateListeners;
        this.dispatchService = dispatchService;
    }

    /**
     * Set new update listeners.
     * @param updateListeners to set
     */
    public void setUpdateListeners(EPStatementListenerSet updateListeners)
    {
        this.statementListenerSet = updateListeners;
    }

    public EventType getEventType()
    {
        return null;
    }

    public Iterator<EventBean> iterator()
    {
        // Iterates over the last new event. For Pattern statements
        // to allow polling the last event that fired.
        return new SingleEventIterator(lastIterableEvent);
    }

    public void execute()
    {
        isDispatchWaiting.set(false);
        EventBean[] newEvents = EventBeanUtility.flatten(lastNewEvents.get());
        EventBean[] oldEvents = EventBeanUtility.flatten(lastOldEvents.get());

        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".execute", newEvents, oldEvents);
        }

        for (UpdateListener listener : statementListenerSet.listeners)
        {
            listener.update(newEvents, oldEvents);
        }
        if (!(statementListenerSet.stmtAwareListeners.isEmpty()))
        {
            for (StatementAwareUpdateListener listener : statementListenerSet.getStmtAwareListeners())
            {
                listener.update(newEvents, oldEvents, statement, epServiceProvider);
            }
        }

        lastNewEvents.get().clear();
        lastOldEvents.get().clear();
    }

    /**
     * Remove event reference to last event.
     */
    public void clear()
    {
        lastIterableEvent = null;
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewBase.class);
}
