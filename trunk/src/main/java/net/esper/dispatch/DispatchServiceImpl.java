package net.esper.dispatch;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.LinkedList;

import net.esper.util.AssertionFacility;

/**
 * Implements dispatch service using a thread-local linked list of Dispatchable instances.
 */
public class DispatchServiceImpl implements DispatchService
{
    private static final ThreadLocal<LinkedList<Dispatchable>> threadDispatchQueueInternal = new ThreadLocal<LinkedList<Dispatchable>>()
    {
        protected synchronized LinkedList<Dispatchable> initialValue()
        {
            return new LinkedList<Dispatchable>();
        }
    };
    private static final ThreadLocal<LinkedList<Dispatchable>> threadDispatchQueueExternal = new ThreadLocal<LinkedList<Dispatchable>>()
    {
        protected synchronized LinkedList<Dispatchable> initialValue()
        {
            return new LinkedList<Dispatchable>();
        }
    };

    public void dispatch()
    {
        dispatchFromQueue(threadDispatchQueueInternal.get());
        dispatchFromQueue(threadDispatchQueueExternal.get());
    }

    public void addExternal(Dispatchable dispatchable)
    {
        LinkedList<Dispatchable> dispatchQueue = threadDispatchQueueExternal.get();
        addToQueue(dispatchable, dispatchQueue);
    }

    public void addInternal(Dispatchable dispatchable)
    {
        LinkedList<Dispatchable> dispatchQueue = threadDispatchQueueInternal.get();
        addToQueue(dispatchable, dispatchQueue);
    }

    private void addToQueue(Dispatchable dispatchable, LinkedList<Dispatchable> dispatchQueue)
    {
        // Make sure the same dispatchable is added once.
        // Could this be a performance problem when the list gets large, it should not get large.
        AssertionFacility.assertFalse(dispatchQueue.contains(dispatchable), "Dispatchable instance already in queue");

        dispatchQueue.add(dispatchable);
    }

    private void dispatchFromQueue(LinkedList<Dispatchable> dispatchQueue)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".dispatchFromQueue Dispatch queue is " + dispatchQueue.size() + " elements");
        }

        while(true)
        {
            Dispatchable next = dispatchQueue.poll();
            if (next != null)
            {
                next.execute();
            }
            else
            {
                break;
            }
        }
    }

    private static final Log log = LogFactory.getLog(DispatchServiceImpl.class);
}
