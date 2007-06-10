///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
	/// This class holds a list of indizes storing filter constants in {@link FilterParamIndexBase} nodes
	/// and a set of {@link FilterHandle}.
	/// An instance of this class represents a leaf-node (no indizes stored, just filter callbacks)
	/// but can also be non-leaf (some indizes exist) in a filter evaluation tree.
	/// Events are evaluated by asking each of the indizes to evaluate the event and by
	/// adding any filter callbacks in this node to the "matches" list of callbacks.
	/// </summary>
	public sealed class FilterHandleSetNode : EventEvaluator
	{
	    private readonly Set<FilterHandle> callbackSet;
	    private readonly List<FilterParamIndexBase> indizes;
        private readonly ReaderWriterLock nodeRWLock;

	    /// <summary>Constructor.</summary>
	    public FilterHandleSetNode()
	    {
	        callbackSet = new LinkedHashSet<FilterHandle>();
	        indizes = new List<FilterParamIndexBase>();
	        nodeRWLock = new ReaderWriterLock();
	    }

	    /// <summary>
	    /// Returns an indication of whether there are any callbacks or index nodes at all in this set.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <returns>
	    /// true if there are neither indizes nor filter callbacks stored, false if either exist.
	    /// </returns>
	    internal bool IsEmpty
	    {
            get { return ((callbackSet.Count == 0) && (indizes.Count == 0)); }
	    }

	    /// <summary>
	    /// Returns the number of filter callbacks stored.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <returns>number of filter callbacks stored</returns>
	    internal int FilterCallbackCount
	    {
            get { return callbackSet.Count; }
	    }

	    /// <summary>
	    /// Returns to lock to use for making changes to the filter callback or inzides collections stored by this node.
	    /// </summary>
	    /// <returns>lock to use in multithreaded environment</returns>
	    internal ReaderWriterLock NodeRWLock
	    {
            get { return nodeRWLock; }
	    }

	    /// <summary>
	    /// Returns list of indexes - not returning an iterator. Client classes should not change this collection.
	    /// </summary>
	    /// <returns>list of indizes</returns>
	    internal IList<FilterParamIndexBase> Indizes
	    {
            get { return indizes; }
	    }

	    /// <summary>
	    /// Evaluate an event by asking each index to match the event. Any filter callbacks at this node automatically
	    /// match the event and do not need to be further evaluated, and are thus added to the "matches" list of callbacks.
	    /// NOTE: This client should not use the lock before calling this method.
	    /// </summary>
	    /// <param name="eventBean">
	    /// is the event wrapper supplying the event property values
	    /// </param>
	    /// <param name="matches">
	    /// is the list of callbacks to add to for any matches found
	    /// </param>
	    public void MatchEvent(EventBean eventBean, IList<FilterHandle> matches)
	    {
	        nodeRWLock.ReadLock().Lock();

	        // Ask each of the indizes to match against the attribute values
	        foreach (FilterParamIndexBase index in indizes)
	        {
	            index.MatchEvent(eventBean, matches);
	        }

	        // Add each filter callback stored in this node to the matching list
	        foreach (FilterHandle filterCallback in callbackSet)
	        {
	            if (log.IsDebugEnabled)
	            {
	                log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") Found a match, filterCallbackHash=" + filterCallback.GetHashCode() +
	                        "  me=" + this +
	                        "  filterCallback=" + filterCallback);
	            }

	            matches.Add(filterCallback);
	        }

	        nodeRWLock.ReadLock().Unlock();
	    }

	    /// <summary>
	    /// Returns an indication whether the filter callback exists in this node.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <param name="filterCallback">is the filter callback to check for</param>
	    /// <returns>true if callback found, false if not</returns>
	    internal bool Contains(FilterHandle filterCallback)
	    {
	        return callbackSet.Contains(filterCallback);
	    }

	    /// <summary>
	    /// Add an index. The same index can be added twice - there is no checking done.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <param name="index">index to add</param>
	    internal void Add(FilterParamIndexBase index)
	    {
	        indizes.Add(index);
	    }

	    /// <summary>
	    /// Remove an index, returning true if it was found and removed or false if not in collection.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <param name="index">is the index to remove</param>
	    /// <returns>true if found, false if not existing</returns>
	    internal bool Remove(FilterParamIndexBase index)
	    {
	        return indizes.Remove(index);
	    }

	    /// <summary>
	    /// Add a filter callback. The filter callback set allows adding the same callback twice with no effect.
	    /// If a client to the class needs to check that the callback already existed, the contains method does that.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <param name="filterCallback">is the callback to add</param>
	    internal void Add(FilterHandle filterCallback)
	    {
	        callbackSet.Add(filterCallback);
	    }

	    /// <summary>
	    /// Remove a filter callback, returning true if it was found and removed or false if not in collection.
	    /// NOTE: the client to this method must use the read-write lock of this object to lock, if required by the client
	    /// code.
	    /// </summary>
	    /// <param name="filterCallback">is the callback to remove</param>
	    /// <returns>true if found, false if not existing</returns>
	    internal bool Remove(FilterHandle filterCallback)
	    {
	        return callbackSet.Remove(filterCallback);
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
