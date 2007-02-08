using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;

namespace net.esper.filter
{
    /// <summary> This class holds a list of indizes storing filter constants in {@link FilterParamIndex} nodes
    /// and a set of {@link FilterCallback}.
    /// An instance of this class represents a leaf-node (no indizes stored, just filter callbacks)
    /// but can also be non-leaf (some indizes exist) in a filter evaluation tree.
    /// Events are evaluated by asking each of the indizes to evaluate the event and by
    /// adding any filter callbacks in this node to the "matches" list of callbacks.
    /// </summary>

    public sealed class FilterCallbackSetNode : EventEvaluator
    {
        private readonly ISet<FilterCallback> callbackSet;
        private readonly IList<FilterParamIndex> indizes;
        private readonly ReaderWriterLock nodeRWLock;

        /**
         * Constructor.
         */
        public FilterCallbackSetNode()
        {
            callbackSet = new LinkedHashSet<FilterCallback>();
            indizes = new ELinkedList<FilterParamIndex>();
            nodeRWLock = new ReaderWriterLock();
        }

        /**
         * Returns an indication of whether there are any callbacks or index nodes at all in this set.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @return true if there are neither indizes nor filter callbacks stored, false if either exist.
         */
        public Boolean IsEmpty
        {
            get { return callbackSet.IsEmpty && (indizes.Count == 0); }
        }

        /// <summary>
        /// Gets the number of filer callbacks in the set.
        /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
        /// code.
        /// </summary>

        public int Count
        {
            get { return callbackSet.Count; }
        }

        /**
         * Returns the number of filter callbacks stored.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @return number of filter callbacks stored
         */
        public int FilterCallbackCount
        {
            get { return callbackSet.Count; }
        }

        /**
         * Returns to lock to use for making changes to the filter callback or inzides collections stored by this node.
         * @return lock to use in multithreaded environment
         */
        public ReaderWriterLock NodeRWLock
        {
            get { return nodeRWLock; }
        }

        /**
         * Returns list of indexes - not returning an iterator. Client classes should not change this collection.
         * @return list of indizes
         */
        public IList<FilterParamIndex> Indizes
        {
            get { return indizes; }
        }

        /**
         * Evaluate an event by asking each index to match the event. Any filter callbacks at this node automatically
         * match the event and do not need to be further evaluated, and are thus added to the "matches" list of callbacks.
         * NOTE: This client should not use the lock before calling this method.
         * @param eventBean is the event wrapper supplying the event property values
         * @param matches is the list of callbacks to add to for any matches found
         */
        public void matchEvent(EventBean eventBean, IList<FilterCallback> matches)
        {
			nodeRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );

            // Ask each of the indizes to match against the attribute values
            foreach (FilterParamIndex index in indizes)
            {
                index.matchEvent(eventBean, matches);
            }

            // Add each filter callback stored in this node to the matching list
            foreach (FilterCallback filterCallback in callbackSet)
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(
                		".match (" + Thread.CurrentThread.ManagedThreadId + ") Found a match," +
                		"  filterCallbackHash=" + filterCallback.GetHashCode() +
                        "  me=" + this +
                        "  filterCallback=" + filterCallback);
                }

                matches.Add(filterCallback);
            }

            nodeRWLock.ReleaseReaderLock();
        }

        /**
         * Returns an indication whether the filter callback exists in this node.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @param filterCallback is the filter callback to check for
         * @return true if callback found, false if not
         */
        public bool Contains(FilterCallback filterCallback)
        {
            return callbackSet.Contains(filterCallback);
        }

        /**
         * Add an index. The same index can be added twice - there is no checking done.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @param index - index to add
         */
        public void Add(FilterParamIndex index)
        {
            indizes.Add(index);
        }

        /**
         * Remove an index, returning true if it was found and removed or false if not in collection.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @param index is the index to remove
         * @return true if found, false if not existing
         */
        public bool Remove(FilterParamIndex index)
        {
            return indizes.Remove(index);
        }

        /**
         * Add a filter callback. The filter callback set allows adding the same callback twice with no effect.
         * If a client to the class needs to check that the callback already existed, the contains method does that.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @param filterCallback is the callback to add
         */
        public void Add(FilterCallback filterCallback)
        {
            callbackSet.Add(filterCallback);
        }

        /**
         * Remove a filter callback, returning true if it was found and removed or false if not in collection.
         * NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
         * code.
         * @param filterCallback is the callback to remove
         * @return true if found, false if not existing
         */
        public bool Remove(FilterCallback filterCallback)
        {
            return callbackSet.Remove(filterCallback);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(FilterCallbackSetNode));
    }
}
