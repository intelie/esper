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
public class UpdateDispatchViewNonBlocking extends UpdateDispatchViewBase
{
    /**
     * Ctor.
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchViewNonBlocking(Set<UpdateListener> updateListeners, DispatchService dispatchService)
    {
        super(updateListeners, dispatchService);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
        }
        if (newData != null)
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

    private static Log log = LogFactory.getLog(UpdateDispatchViewNonBlocking.class);
}
