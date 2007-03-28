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
    /// <summary> This class holds a list of indizes storing filter constants in <seealso cref="FilterParamIndex" /> nodes
    /// and a set of <seealso cref="FilterCallback"/>.
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

        /// <summary>
        /// Initializes a new instance of the <see cref="FilterCallbackSetNode"/> class.
        /// </summary>
        
        public FilterCallbackSetNode()
        {
            callbackSet = new LinkedHashSet<FilterCallback>();
            indizes = new List<FilterParamIndex>();
            nodeRWLock = new ReaderWriterLock();
        }

        /// <summary>
        /// Returns an indication of whether there are any callbacks or index nodes at
        /// all in this set.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to lock,
        /// if required by the clientcode.
        /// </para>
        /// </summary>
        /// <returns>true if there are neither indizes nor filter callbacks stored, false if either exist.</returns>

        public Boolean IsEmpty
        {
            get { return callbackSet.IsEmpty && (indizes.Count == 0); }
        }

        /// <summary>
        /// Returns the number of filter callbacks stored.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to
        /// lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <returns>number of filter callbacks stored</returns>

        public int FilterCallbackCount
        {
            get { return callbackSet.Count; }
        }

        /// <summary>
        /// Returns to lock to use for making changes to the filter callback 
        /// or inzides collections stored by this node.
        /// </summary>
        /// <returns>lock to use in multithreaded environment</returns>

        public ReaderWriterLock NodeRWLock
        {
            get { return nodeRWLock; }
        }

        /// <summary>
        /// Returns list of indexes - not returning an iterator. Client classes should not
        /// change this collection.
        /// </summary>
        /// <returns>list of indizes</returns>

        public IList<FilterParamIndex> Indizes
        {
            get { return indizes; }
        }

        /// <summary>
        /// Evaluate an event by asking each index to match the event. Any filter callbacks
        /// at this node automaticallymatch the event and do not need to be further
        /// evaluated, and are thus added to the "matches" list of callbacks.
        /// <para>
        /// NOTE: This client should not use the lock before calling this method.
        /// </para>
        /// </summary>
        /// <param name="eventBean">is the event wrapper supplying the event property values</param>
        /// <param name="matches">is the list of callbacks to add to for any matches found</param>

        public void MatchEvent(EventBean eventBean, IList<FilterCallback> matches)
        {
			nodeRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );

            // Ask each of the indizes to match against the attribute values
            foreach (FilterParamIndex index in indizes)
            {
                index.MatchEvent(eventBean, matches);
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

        /// <summary>
        /// Returns an indication whether the filter callback exists in this node.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object 
        /// to lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <param name="filterCallback">is the filter callback to check for</param>
        /// <returns>true if callback found, false if not</returns>

        public bool Contains(FilterCallback filterCallback)
        {
            return callbackSet.Contains(filterCallback);
        }

        /// <summary>
        /// Add an index. The same index can be added twice - there is no checking done.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to
        /// lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <param name="index">index to add</param>

        public void Add(FilterParamIndex index)
        {
            indizes.Add(index);
        }

        /// <summary>
        /// Remove an index, returning true if it was found and removed or false if not in
        /// collection.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to
        /// lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <param name="index">is the index to remove</param>
        /// <returns>true if found, false if not existing</returns>

        public bool Remove(FilterParamIndex index)
        {
            return indizes.Remove(index);
        }

        /// <summary>
        /// Add a filter callback. The filter callback set allows adding the same callback
        /// twice with no effect.  If a client to the class needs to check that the callback
        /// already existed, the contains method does that.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to 
        /// lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <param name="filterCallback">is the callback to add</param>

        public void Add(FilterCallback filterCallback)
        {
            callbackSet.Add(filterCallback);
        }

        /// <summary>
        /// Remove a filter callback, returning true if it was found and removed or false
        /// if not in collection.
        /// <para>
        /// NOTE: the client to this method must use the read-write lock of this object to
        /// lock, if required by the clientcode.
        /// </para>
        /// </summary>
        /// <param name="filterCallback">is the callback to remove</param>
        /// <returns>true if found, false if not existing</returns>

        public bool Remove(FilterCallback filterCallback)
        {
            return callbackSet.Remove(filterCallback);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(FilterCallbackSetNode));
    }
}
