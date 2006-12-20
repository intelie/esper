package net.esper.filter;

import net.esper.event.EventType;
import net.esper.collection.Pair;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class is responsible for changes to {@link EventTypeIndex} for addition and removal of filters.
 * It delegates the work to make modifications to the filter parameter tree to an {@link IndexTreeBuilder}.
 * It enforces a policy that a filter callback can only be added once.
 */
public class EventTypeIndexBuilder
{
    private final Map<FilterCallback, Pair<EventType, IndexTreePath>> callbacks;
    private final Lock callbacksLock;
    private final EventTypeIndex eventTypeIndex;

    /**
     * Constructor - takes the event type index to manipulate as its parameter.
     * @param eventTypeIndex - index to manipulate
     */
    public EventTypeIndexBuilder(EventTypeIndex eventTypeIndex)
    {
        this.eventTypeIndex = eventTypeIndex;

        this.callbacks = new HashMap<FilterCallback, Pair<EventType, IndexTreePath>>();
        this.callbacksLock = new ReentrantLock();
    }

    /**
     * Add a filter to the event type index structure, and to the filter subtree.
     * Throws an IllegalStateException exception if the callback is already registered.
     * @param filterValueSet is the filter information
     * @param filterCallback is the callback
     */
    public final void add(FilterValueSet filterValueSet, FilterCallback filterCallback)
    {
        EventType eventType = filterValueSet.getEventType();

        // Check if a filter tree exists for this event type
        FilterCallbackSetNode rootNode = eventTypeIndex.get(eventType);

        // Make sure we have a root node
        if (rootNode == null)
        {
            callbacksLock.lock();
            rootNode = eventTypeIndex.get(eventType);
            if (rootNode == null)
            {
                rootNode = new FilterCallbackSetNode();
                eventTypeIndex.add(eventType, rootNode);
            }
            callbacksLock.unlock();
        }

        // Make sure the filter callback doesn't already exist
        callbacksLock.lock();
        if (callbacks.containsKey(filterCallback))
        {
            callbacksLock.unlock();
            throw new IllegalStateException("Callback for filter specification already exists in collection");
        }
        callbacksLock.unlock();

        // Now add to tree
        IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
        IndexTreePath path = treeBuilder.add(filterValueSet, filterCallback, rootNode);

        callbacksLock.lock();
        callbacks.put(filterCallback, new Pair<EventType, IndexTreePath>(eventType, path));
        callbacksLock.unlock();
    }

    /**
     * Remove a filter callback from the given index node.
     * @param filterCallback is the callback to remove
     */
    public final void remove(FilterCallback filterCallback)
    {
        callbacksLock.lock();
        Pair<EventType, IndexTreePath> pair = callbacks.get(filterCallback);
        callbacksLock.unlock();

        if (pair == null)
        {
            throw new IllegalArgumentException("Filter callback to be removed not found");
        }

        FilterCallbackSetNode rootNode = eventTypeIndex.get(pair.getFirst());

        // Now remove from tree
        IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
        treeBuilder.remove(filterCallback, pair.getSecond(), rootNode);

        // Remove from callbacks list
        callbacksLock.lock();
        callbacks.remove(filterCallback);
        callbacksLock.unlock();
    }
}
