///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.view.window;

namespace net.esper.view.internals
{
	/// <summary>
	/// Buffers view-posted insert stream (new data) and remove stream (old data) events for
	/// use with determining prior results in these streams, for multiple different prior events.
    /// <para>
	/// Buffers only exactly those events in new data and old data that are being asked for via the
	/// 2 or more 'prior' functions that specify different indexes. For example &quot;select Prior(2, price), Prior(1, price)&quot;
	/// results in on buffer instance handling both the need to the immediatly prior (1) and the 2-events-ago
	/// event (2).
	/// </para>
    /// <para>
	/// As all views are required to post new data and post old data that removes the new data to subsequent views,
	/// this buffer can be attached to all views and should not result in a memory leak.
	/// </para>
    /// <para>
	/// When the buffer receives old data (rstream) events it removes the prior events to the rstream events
	/// from the buffer the next time it receives a post (not immediatly) to allow queries to the buffer.
	/// </para>
	/// </summary>
	public class PriorEventBufferMulti : ViewUpdatedCollection, RelativeAccessByEventNIndex
	{
	    private readonly int priorToIndexesSize;
	    private readonly int[] priorToIndexes;
	    private readonly EDictionary<EventBean, EventBean[]> priorEventMap;
	    private readonly RollingEventBuffer newEvents;
	    private EventBean[] lastOldData;

	    /// <summary>Ctor.</summary>
	    /// <param name="priorToIndexSet">
	    /// holds a list of prior-event indexes.
        /// <para>
        /// For example, an array {0,4,6} means the current event, 4 events before the current event
	    /// and 6 events before the current event.
	    /// </para>
	    /// </param>
	    public PriorEventBufferMulti(int[] priorToIndexSet)
	    {
	        // Determine the maximum prior index to retain
	        int maxPriorIndex = 0;
	        foreach (int priorIndex in priorToIndexSet)
	        {
	            if (priorIndex > maxPriorIndex)
	            {
	                maxPriorIndex = priorIndex;
	            }
	        }

	        // Copy the set of indexes into an array, sort in ascending order
	        priorToIndexesSize = priorToIndexSet.Length;
	        priorToIndexes = new int[priorToIndexesSize];
	        int count = 0;
	        foreach (int priorIndex in priorToIndexSet)
	        {
	            priorToIndexes[count++] = priorIndex;
	        }

            Array.Sort(priorToIndexes);

	        // Construct a rolling buffer of new data for holding max index + 1 (position 1 requires 2 events to keep)
	        newEvents = new RollingEventBuffer(maxPriorIndex + 1);
	        priorEventMap = new HashDictionary<EventBean, EventBean[]>();
	    }

        /// <summary>
        /// Accepts view insert and remove stream.
        /// </summary>
        /// <param name="newData">is the insert stream events or null if no data</param>
        /// <param name="oldData">is the remove stream events or null if no data</param>
	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        // Remove last old data posted in previous post
	        if (lastOldData != null)
	        {
	            for (int i = 0; i < lastOldData.Length; i++)
	            {
	                priorEventMap.Remove(lastOldData[i]);
	            }
	        }

	        // Post new data to rolling buffer starting with the oldest
	        if (newData != null)
	        {
	            for (int i = 0; i < newData.Length; i++)
	            {
	                EventBean newEvent = newData[i];

	                // Add new event
	                newEvents.Add(newEvent);

	                // Save prior index events in array
	                EventBean[] priorEvents = new EventBean[priorToIndexesSize];
	                for (int j = 0; j < priorToIndexesSize; j++)
	                {
	                    int priorIndex = priorToIndexes[j];
	                    priorEvents[j] = newEvents.Get(priorIndex);
	                }
	                priorEventMap.Put(newEvent, priorEvents);
	            }
	        }

	        // Save old data to be removed next time we get posted results
	        lastOldData = oldData;
	    }

        /// <summary>
        /// Gets the relative to event.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <param name="priorToIndex">Index of the prior to.</param>
        /// <returns></returns>
	    public EventBean GetRelativeToEvent(EventBean _event, int priorToIndex)
	    {
	        if (priorToIndex >= priorToIndexesSize)
	        {
	            throw new ArgumentException("Index " + priorToIndex + " not allowed, max size is " + priorToIndexesSize);
	        }
	        EventBean[] priorEvents = priorEventMap.Fetch(_event);
	        if (priorEvents == null)
	        {
	            throw new IllegalStateException("Event not currently in collection, event=" + _event);
	        }
	        return priorEvents[priorToIndex];
	    }
	}
} // End of namespace
