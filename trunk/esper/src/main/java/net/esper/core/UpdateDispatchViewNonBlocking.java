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
public class UpdateDispatchViewNonBlocking extends UpdateDispatchViewBase
{
    /**
     * Ctor.
     * @param epServiceProvider - engine instance to supply to statement-aware listeners
     * @param statement - the statement instance to supply to statement-aware listeners
     * @param updateListeners - listeners to update
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchViewNonBlocking(EPServiceProvider epServiceProvider, EPStatement statement, EPStatementListenerSet updateListeners, DispatchService dispatchService)
    {
        super(epServiceProvider, statement, updateListeners, dispatchService);
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
