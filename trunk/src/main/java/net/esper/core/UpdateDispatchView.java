package net.esper.core;

import java.util.Iterator;
import java.util.Vector;
import java.util.Set;

import net.esper.client.UpdateListener;
import net.esper.dispatch.DispatchService;
import net.esper.dispatch.Dispatchable;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.persist.LogContextNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public class UpdateDispatchView extends ViewSupport implements Dispatchable
{
    private final LogContextNode<Set<UpdateListener>> updateListenerState;
    private final DispatchService dispatchService;
    private boolean isDispatchWaiting;

    private Vector<EventBean[]> lastNewEvents = new Vector<EventBean[]>();
    private Vector<EventBean[]> lastOldEvents = new Vector<EventBean[]>();

    /**
     * Ctor.
     * @param updateListenerState - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchView(LogContextNode<Set<UpdateListener>> updateListenerState, DispatchService dispatchService)
    {
        this.updateListenerState = updateListenerState;
        this.dispatchService = dispatchService;
    }

    public String attachesTo(Viewable parentViewable)
    {
        return null;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
        }
        if (newData != null)
        {
            lastNewEvents.add(newData);
        }
        if (oldData != null)
        {
            lastOldEvents.add(oldData);
        }
        if (!isDispatchWaiting)
        {
            dispatchService.addExternal(this);
            isDispatchWaiting = true;
        }
    }

    public EventType getEventType()
    {
        return null;
    }

    public Iterator<EventBean> iterator()
    {
        return null;
    }

    public void execute()
    {
        isDispatchWaiting = false;
        EventBean[] newEvents = EventBeanUtility.flatten(lastNewEvents);
        EventBean[] oldEvents = EventBeanUtility.flatten(lastOldEvents);

        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".execute", newEvents, oldEvents);
        }

        for (UpdateListener listener : updateListenerState.getState())
        {
            listener.update(newEvents, oldEvents);
        }

        lastNewEvents.clear();
        lastOldEvents.clear();
    }

    private static Log log = LogFactory.getLog(UpdateDispatchView.class);
}

