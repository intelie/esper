package net.esper.core;

import net.esper.dispatch.Dispatchable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DF3   <-->   DF2  <-->  DF1
 *
 * DF1 completes: set DF2.earlier = null, notify DF2
 */
public class DispatchFuture implements Dispatchable
{
    private static final Log log = LogFactory.getLog(DispatchFuture.class);
    private UpdateDispatchViewBlocking view;
    private DispatchFuture earlier;
    private DispatchFuture later;
    private transient boolean isCompleted;
    private long msecTimeout;

    public DispatchFuture(UpdateDispatchViewBlocking view, DispatchFuture earlier, long msecTimeout)
    {
        this.view = view;
        this.earlier = earlier;
        this.msecTimeout = msecTimeout;
    }

    public DispatchFuture()
    {
        isCompleted = true;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

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
