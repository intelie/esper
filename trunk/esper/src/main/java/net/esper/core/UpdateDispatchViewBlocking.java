package net.esper.core;

import net.esper.client.UpdateListener;
import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public class UpdateDispatchViewBlocking extends UpdateDispatchViewBase
{
    private DispatchFuture currentFuture;
    private long msecTimeout;

    /**
     * Ctor.
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchViewBlocking(Set<UpdateListener> updateListeners, DispatchService dispatchService, long msecTimeout)
    {
        super(updateListeners, dispatchService);
        this.currentFuture = new DispatchFuture(); // use a completed future as a start
        this.msecTimeout = msecTimeout;
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
            DispatchFuture nextFuture;
            synchronized(this)
            {
                nextFuture = new DispatchFuture(this, currentFuture, msecTimeout);
                currentFuture.setLater(nextFuture);
                currentFuture = nextFuture;
            }
            dispatchService.addExternal(nextFuture);
            isDispatchWaiting.set(true);
        }
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewBlocking.class);
}