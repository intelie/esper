package net.esper.dispatch;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.LinkedList;

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

    private static void addToQueue(Dispatchable dispatchable, LinkedList<Dispatchable> dispatchQueue)
    {
        dispatchQueue.add(dispatchable);
    }

    private static void dispatchFromQueue(LinkedList<Dispatchable> dispatchQueue)
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
