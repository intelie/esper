package com.espertech.esper.core;

import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.timer.TimeSourceService;
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
    private TimeSourceService timeSourceService;

    /**
     * Ctor.
     * @param dispatchService - for performing the dispatch
     * @param msecTimeout - timeout for preserving dispatch order through blocking
     * @param statementResultService - handles result delivery
     */
    public UpdateDispatchViewBlockingSpin(StatementResultService statementResultService, DispatchService dispatchService, long msecTimeout, TimeSourceService timeSourceService)
    {
        super(statementResultService, dispatchService);
        this.currentFutureSpin = new UpdateDispatchFutureSpin(timeSourceService); // use a completed future as a start
        this.msecTimeout = msecTimeout;
        this.timeSourceService = timeSourceService;
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
        newResult(new UniformPair<EventBean[]>(newData, oldData));
    }

    public void newResult(UniformPair<EventBean[]> result)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            ViewSupport.dumpUpdateParams(".update for view " + this, result);
        }
        statementResultServiceImpl.indicate(result);

        if (!isDispatchWaiting.get())
        {
            UpdateDispatchFutureSpin nextFutureSpin;
            synchronized(this)
            {
                nextFutureSpin = new UpdateDispatchFutureSpin(this, currentFutureSpin, msecTimeout, timeSourceService);
                currentFutureSpin = nextFutureSpin;
            }
            dispatchService.addExternal(nextFutureSpin);
            isDispatchWaiting.set(true);
        }
    }

    private static Log log = LogFactory.getLog(UpdateDispatchViewBlockingSpin.class);
}
