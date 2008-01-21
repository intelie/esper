package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
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
     * @param dispatchService - for performing the dispatch
     */
    public UpdateDispatchViewNonBlocking(StatementResultService statementResultServiceImpl, DispatchService dispatchService)
    {
        super(statementResultServiceImpl, dispatchService);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
        }
        statementResultServiceImpl.indicate(newData, oldData);
        if (!isDispatchWaiting.get())
        {
            dispatchService.addExternal(this);
            isDispatchWaiting.set(true);
        }
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewNonBlocking.class);
}
