package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public class UpdateDispatchViewBlockingWait extends UpdateDispatchViewBase
{
    private UpdateDispatchFutureWait currentFutureWait;
    private long msecTimeout;

    /**
     * Ctor.
     * @param epServiceProvider - engine instance to supply to statement-aware listeners
     * @param statement - the statement instance to supply to statement-aware listeners
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     * @param msecTimeout - timeout for preserving dispatch order through blocking
     */
    public UpdateDispatchViewBlockingWait(EPServiceProvider epServiceProvider, EPStatement statement, EPStatementListenerSet updateListeners, DispatchService dispatchService, long msecTimeout)
    {
        super(epServiceProvider, statement, updateListeners, dispatchService);
        this.currentFutureWait = new UpdateDispatchFutureWait(); // use a completed future as a start
        this.msecTimeout = msecTimeout;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
        }
        if ((newData != null) && (newData.length != 0))
        {
            lastIterableEvent = newData[0];
            lastNewEvents.get().add(newData);
        }
        if ((oldData != null) && (oldData.length != 0))
        {
            lastOldEvents.get().add(oldData);
        }
        if (!isDispatchWaiting.get())
        {            
            UpdateDispatchFutureWait nextFutureWait;
            synchronized(this)
            {
                nextFutureWait = new UpdateDispatchFutureWait(this, currentFutureWait, msecTimeout);
                currentFutureWait.setLater(nextFutureWait);
                currentFutureWait = nextFutureWait;
            }
            dispatchService.addExternal(nextFutureWait);
            isDispatchWaiting.set(true);
        }
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewBlockingWait.class);
}