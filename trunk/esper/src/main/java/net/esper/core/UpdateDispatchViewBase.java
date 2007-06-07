package net.esper.core;

import net.esper.view.ViewSupport;
import net.esper.dispatch.Dispatchable;
import net.esper.dispatch.DispatchService;
import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventBeanUtility;
import net.esper.collection.SingleEventIterator;

import java.util.Set;
import java.util.LinkedList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public abstract class UpdateDispatchViewBase extends ViewSupport implements Dispatchable
{
    private Set<UpdateListener> updateListeners;

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
            return new Boolean(false);
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
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchViewBase(Set<UpdateListener> updateListeners, DispatchService dispatchService)
    {
        this.updateListeners = updateListeners;
        this.dispatchService = dispatchService;
    }

    /**
     * Set new update listeners.
     * @param updateListeners to set
     */
    public void setUpdateListeners(Set<UpdateListener> updateListeners)
    {
        this.updateListeners = updateListeners;
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

        for (UpdateListener listener : updateListeners)
        {
            listener.update(newEvents, oldEvents);
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
