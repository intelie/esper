package net.esper.core;

import net.esper.dispatch.Dispatchable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DispatchFuture can be added to a dispatch queue that is thread-local. It represents
 * is a stand-in for a future dispatching of a statement result to statement listeners.
 * <p>
 * DispatchFuture is aware of future and past dispatches:
 * (newest) DF3   <-->   DF2  <-->  DF1  (oldest)
 */
public class DispatchFuture implements Dispatchable
{
    private static final Log log = LogFactory.getLog(DispatchFuture.class);
    private UpdateDispatchViewBlocking view;
    private DispatchFuture earlier;
    private DispatchFuture later;
    private transient boolean isCompleted;
    private long msecTimeout;

    /**
     * Ctor.
     * @param view is the blocking dispatch view through which to execute a dispatch 
     * @param earlier is the older future
     * @param msecTimeout is the timeout period to wait for listeners to complete a prior dispatch
     */
    public DispatchFuture(UpdateDispatchViewBlocking view, DispatchFuture earlier, long msecTimeout)
    {
        this.view = view;
        this.earlier = earlier;
        this.msecTimeout = msecTimeout;
    }

    /**
     * Ctor - use for the first future to indicate completion.
     */
    public DispatchFuture()
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

    /**
     * Hand a later future to the dispatch to use for indicating completion via notify.
     * @param later is the later dispatch
     */
    public void setLater(DispatchFuture later)
    {
        this.later = later;
    }

    public void execute()
    {
        if (!earlier.isCompleted)
        {
            synchronized(this)
            {
                try
                {
                    this.wait(msecTimeout);
                }
                catch (InterruptedException e)
                {
                    log.error(e);
                }
            }
        }

        view.execute();
        isCompleted = true;

        if (later != null)
        {
            synchronized(later)
            {
                later.notify();
            }
        }
        earlier = null;
        later = null;
    }
}
