package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import net.esper.collection.Pair;
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
     * @param dispatchService - for performing the dispatch
     * @param msecTimeout - timeout for preserving dispatch order through blocking
     */
    public UpdateDispatchViewBlockingWait(StatementResultService statementResultServiceImpl, DispatchService dispatchService, long msecTimeout)
    {
        super(statementResultServiceImpl, dispatchService);
        this.currentFutureWait = new UpdateDispatchFutureWait(); // use a completed future as a start
        this.msecTimeout = msecTimeout;
    }

    public void newResult(Pair<EventBean[], EventBean[]> results)
    {
        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, results);
        }

        statementResultServiceImpl.indicate(results);

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