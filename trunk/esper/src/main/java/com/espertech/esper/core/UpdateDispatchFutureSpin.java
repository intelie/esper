package com.espertech.esper.core;

import com.espertech.esper.dispatch.Dispatchable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * UpdateDispatchFutureSpin can be added to a dispatch queue that is thread-local. It represents
 * is a stand-in for a future dispatching of a statement result to statement listeners.
 * <p>
 * UpdateDispatchFutureSpin is aware of future and past dispatches:
 * (newest) DF3   <-->   DF2  <-->  DF1  (oldest), and uses a spin lock to block if required
 */
public class UpdateDispatchFutureSpin implements Dispatchable
{
    private static final Log log = LogFactory.getLog(UpdateDispatchFutureSpin.class);
    private UpdateDispatchViewBlockingSpin view;
    private UpdateDispatchFutureSpin earlier;
    private volatile boolean isCompleted;
    private long msecTimeout;

    /**
     * Ctor.
     * @param view is the blocking dispatch view through which to execute a dispatch
     * @param earlier is the older future
     * @param msecTimeout is the timeout period to wait for listeners to complete a prior dispatch
     */
    public UpdateDispatchFutureSpin(UpdateDispatchViewBlockingSpin view, UpdateDispatchFutureSpin earlier, long msecTimeout)
    {
        this.view = view;
        this.earlier = earlier;
        this.msecTimeout = msecTimeout;
    }

    /**
     * Ctor - use for the first future to indicate completion.
     */
    public UpdateDispatchFutureSpin()
    {
        isCompleted = true;
    }

    /**
     * Returns true if the dispatch completed for this future.
     * @return true for completed, false if not
     */
    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void execute()
    {
        if (!earlier.isCompleted)
        {
            long spinStartTime = System.currentTimeMillis();

            while(!earlier.isCompleted)
            {
                Thread.yield();

                long spinDelta = System.currentTimeMillis() - spinStartTime;
                if (spinDelta > msecTimeout)
                {
                    log.info("Spin wait timeout exceeded in listener dispatch");
                    break;
                }
            }
        }

        view.execute();
        isCompleted = true;

        earlier = null;
    }
}
