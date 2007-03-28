using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;

namespace net.esper.filter
{
    /// <summary> This class is responsible for changes to <seealso cref="EventTypeIndex"/> for addition and removal of filters.
    /// It delegates the work to make modifications to the filter parameter tree to an <seealso cref="IndexTreeBuilder"/>.
    /// It enforces a policy that a filter callback can only be added once.
    /// </summary>

    public class EventTypeIndexBuilder
    {
        private readonly EDictionary<FilterCallback, Pair<EventType, IndexTreePath>> callbacks;
        private readonly Object callbacksLock;
        private readonly EventTypeIndex eventTypeIndex;

        /// <summary> Constructor - takes the event type index to manipulate as its parameter.</summary>
        /// <param name="eventTypeIndex">index to manipulate
        /// </param>

        public EventTypeIndexBuilder(EventTypeIndex eventTypeIndex)
        {
            this.eventTypeIndex = eventTypeIndex;
            this.callbacks = new EHashDictionary<FilterCallback, Pair<EventType, IndexTreePath>>();
            this.callbacksLock = new Object();
        }

        /// <summary> Add a filter to the event type index structure, and to the filter subtree.
        /// Throws an IllegalStateException exception if the callback is already registered.
        /// </summary>
        /// <param name="filterValueSet">is the filter information
        /// </param>
        /// <param name="filterCallback">is the callback
        /// </param>
        public void Add(FilterValueSet filterValueSet, FilterCallback filterCallback)
        {
            EventType eventType = filterValueSet.EventType;

            // Check if a filter tree exists for this event type
            FilterCallbackSetNode rootNode = eventTypeIndex[eventType];

            // Make sure we have a root node
            if (rootNode == null)
            {
                Monitor.Enter( callbacksLock );
                rootNode = eventTypeIndex[eventType];
                if (rootNode == null)
                {
                    rootNode = new FilterCallbackSetNode();
                    eventTypeIndex.Add(eventType, rootNode);
                }
                Monitor.Exit( callbacksLock );
            }

            // Make sure the filter callback doesn't already exist
            Monitor.Enter( callbacksLock );
            if (callbacks.ContainsKey(filterCallback))
            {
                Monitor.Exit( callbacksLock );
                throw new SystemException("Callback for filter specification already exists in collection");
            }
            Monitor.Exit( callbacksLock );

            // Now add to tree
            IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
            IndexTreePath path = treeBuilder.Add(filterValueSet, filterCallback, rootNode);

            Monitor.Enter( callbacksLock );
            callbacks[filterCallback] = new Pair<EventType, IndexTreePath>(eventType, path);
            Monitor.Exit( callbacksLock );
        }

        /// <summary> Remove a filter callback from the given index node.</summary>
        /// <param name="filterCallback">is the callback to remove
        /// </param>
        public void Remove(FilterCallback filterCallback)
        {
            Monitor.Enter( callbacksLock );
            Pair<EventType, IndexTreePath> pair = callbacks.Fetch( filterCallback, null ) ;
            Monitor.Exit( callbacksLock );

            if (pair == null)
            {
                throw new ArgumentException("Filter callback to be removed not found");
            }

            FilterCallbackSetNode rootNode = eventTypeIndex[pair.First];

            // Now remove from tree
            IndexTreeBuilder treeBuilder = new IndexTreeBuilder();
            treeBuilder.Remove(filterCallback, pair.Second, rootNode);

            // Remove from callbacks list
            Monitor.Enter(callbacksLock);
            callbacks.Remove(filterCallback);
            Monitor.Exit(callbacksLock);
        }
    }
}
