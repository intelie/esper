package net.esper.core;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.dispatch.DispatchService;
import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience view for dispatching view updates received from a parent view to update listeners
 * via the dispatch service.
 */
public class UpdateDispatchViewBlockingSpin extends UpdateDispatchViewBase
{
    private UpdateDispatchFutureSpin currentFutureSpin;
    private long msecTimeout;

    /**
     * Ctor.
     * @param dispatchService - for performing the dispatch
     * @param msecTimeout - timeout for preserving dispatch order through blocking
     */
    public UpdateDispatchViewBlockingSpin(StatementResultService statementResultService, DispatchService dispatchService, long msecTimeout)
    {
        super(statementResultService, dispatchService);
        this.currentFutureSpin = new UpdateDispatchFutureSpin(); // use a completed future as a start
        this.msecTimeout = msecTimeout;
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
            UpdateDispatchFutureSpin nextFutureSpin;
            synchronized(this)
            {
                nextFutureSpin = new UpdateDispatchFutureSpin(this, currentFutureSpin, msecTimeout);
                currentFutureSpin = nextFutureSpin;
            }
            dispatchService.addExternal(nextFutureSpin);
            isDispatchWaiting.set(true);
        }
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewBlockingSpin.class);
}
