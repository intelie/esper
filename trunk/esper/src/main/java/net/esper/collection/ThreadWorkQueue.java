package net.esper.collection;

import java.util.LinkedList;

/**
 * Simple queue implementation based on a Linked List per thread.
 * Objects can be added to the queue tail or queue head.
 */
public class ThreadWorkQueue
{
    private static final ThreadLocal<LinkedList<Object>> threadQueue = new ThreadLocal<LinkedList<Object>>()
    {
        protected synchronized LinkedList<Object> initialValue()
        {
            return new LinkedList<Object>();
        }
    };

    /**
     * Adds event to the end of the event queue.
     * @param event to add
     */
    public static void add(Object event)
    {
        LinkedList<Object> queue = threadQueue.get();
        queue.addLast(event);
    }

    /**
     * Adds event to the front of the queue.
     * @param event to add
     */
    public static void addFront(Object event)
    {
        LinkedList<Object> queue = threadQueue.get();
        queue.addFirst(event);
    }

    /**
     * Returns the next event to getSelectListEvents, or null if there are no more events.
     * @return next event to getSelectListEvents
     */
    public static Object next()
    {
        LinkedList<Object> queue = threadQueue.get();
        return queue.poll();
    }
}
