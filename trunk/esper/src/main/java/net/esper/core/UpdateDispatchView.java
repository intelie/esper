package net.esper.core;

import java.util.Iterator;
import java.util.Set;
import java.util.LinkedList;

import net.esper.client.UpdateListener;
import net.esper.dispatch.DispatchService;
import net.esper.dispatch.Dispatchable;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import net.esper.collection.SingleEventIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public class UpdateDispatchView extends ViewSupport implements Dispatchable
{
    private Set<UpdateListener> updateListeners;
    private final DispatchService dispatchService;
    private EventBean lastIterableEvent;

    private ThreadLocal<Boolean> isDispatchWaiting = new ThreadLocal<Boolean>() {
        protected synchronized Boolean initialValue() {
            return new Boolean(false);
        }
    };

    private ThreadLocal<LinkedList<EventBean[]>> lastNewEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
        }
    };
    private ThreadLocal<LinkedList<EventBean[]>> lastOldEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
        }
    };

    /**
     * Ctor.
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchView(Set<UpdateListener> updateListeners, DispatchService dispatchService)
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

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
        }
        if ((newData != null) && (newData.length > 0))
        {
            lastIterableEvent = newData[0];
            lastNewEvents.get().add(newData);
        }
        if (oldData != null)
        {
            lastOldEvents.get().add(oldData);
        }
        if (!isDispatchWaiting.get())
        {
            dispatchService.addExternal(this);
            isDispatchWaiting.set(true);
        }
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

    private static Log log = LogFactory.getLog(UpdateDispatchView.class);
}

