package net.esper.filter;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Mapping of event type to a tree-like structure
 * containing filter parameter constants in indexes {@link FilterParamIndex} and filter callbacks in {@link FilterCallbackSetNode}.
 * <p>
 * This class evaluates events for the purpose of filtering by (1) looking up the event's {@link EventType}
 * and (2) asking the subtree for this event type to evaluate the event.
 * <p>
 * The class performs all the locking required for multithreaded access.
 */
public class EventTypeIndex implements EventEvaluator
{
    private Map<EventType, FilterCallbackSetNode> eventTypes;
    private ReadWriteLock eventTypesRWLock;

    /**
     * Constructor.
     */
    public EventTypeIndex()
    {
        eventTypes = new HashMap<EventType, FilterCallbackSetNode>();
        eventTypesRWLock = new ReentrantReadWriteLock();
    }

    /**
     * Add a new event type to the index and use the specified node for the root node of its subtree.
     * If the event type already existed, the method will throw an IllegalStateException.
     * @param eventType is the event type to be added to the index
     * @param rootNode is the root node of the subtree for filter constant indizes and callbacks
     */
    public void add(EventType eventType, FilterCallbackSetNode rootNode)
    {
        eventTypesRWLock.writeLock().lock();
        if (eventTypes.containsKey(eventType))
        {
            eventTypesRWLock.writeLock().unlock();
            throw new IllegalStateException("Event type already in index, add not performed, type=" + eventType);
        }
        eventTypes.put(eventType, rootNode);
        eventTypesRWLock.writeLock().unlock();
    }

    /**
     * Returns the root node for the given event type, or null if this event type has not been seen before.
     * @param eventType is an event type
     * @return the subtree's root node
     */
    public FilterCallbackSetNode get(EventType eventType)
    {
        eventTypesRWLock.readLock().lock();
        FilterCallbackSetNode result = eventTypes.get(eventType);
        eventTypesRWLock.readLock().unlock();

        return result;
    }

    public void matchEvent(EventBean event, List<FilterCallback> matches)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".matchEvent Event received for matching, event=" + event);
        }

        EventType eventType = event.getEventType();

        // Attempt to match exact type
        matchType(eventType, event, matches);

        // No supertype means we are done
        if (eventType.getSuperTypes() == null)
        {
            return;
        }

        for (Iterator<EventType> it = eventType.getDeepSuperTypes(); it.hasNext();)
        {
            EventType superType = it.next();
            matchType(superType, event, matches);
        }
    }

    private void matchType(EventType eventType, EventBean eventBean, List<FilterCallback> matches)
    {
        eventTypesRWLock.readLock().lock();
        FilterCallbackSetNode rootNode = eventTypes.get(eventType);
        eventTypesRWLock.readLock().unlock();

        // If the top class node is null, no filters have yet been registered for this event type.
        // In this case, log a message and done.
        if (rootNode == null)
        {
            if (log.isDebugEnabled())
            {
                String message = "Event type is not known to the filter service, eventType=" + eventType;
                log.debug(".matchEvent " + message);
            }
            return;
        }

        rootNode.matchEvent(eventBean, matches);
    }

    private static final Log log = LogFactory.getLog(EventTypeIndex.class);
}
