///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.events;

namespace net.esper.collection
{
	/// <summary>
	/// Event buffer of a given size provides a random access API into the most current event to prior events
	/// up to the given size. Oldest events roll out of the buffer first.
	/// &lt;p&gt;
	/// Backed by a fixed-size array that is filled forward, then rolls back to the beginning
	/// keeping track of the current position.
	/// </summary>
	public class RollingEventBuffer
	{
	    private int size;
	    private EventBean[] buffer;
	    private int nextFreeIndex;

	    /// <summary>Ctor.</summary>
	    /// <param name="size">is the maximum number of events in buffer</param>
	    public RollingEventBuffer(int size)
	    {
	        if (size <= 0)
	        {
	            throw new ArgumentException("Minimum buffer size is 1");
	        }

	        this.size = size;
	        nextFreeIndex = 0;
	        buffer = new EventBean[size];
	    }

	    /// <summary>Add events to the buffer.</summary>
	    /// <param name="events">to add</param>
	    public void Add(EventBean[] events)
	    {
	        if (events == null)
	        {
	            return;
	        }

	        for (int i = 0; i < events.Length; i++)
	        {
	            Add(events[i]);
	        }
	    }

	    /// <summary>Add an event to the buffer.</summary>
	    /// <param name="event">to add</param>
	    public void Add(EventBean _event)
	    {
	        buffer[nextFreeIndex] = _event;
	        nextFreeIndex++;

	        if (nextFreeIndex == size)
	        {
	            nextFreeIndex = 0;
	        }
	    }

	    /// <summary>
	    /// Get an event prior to the last event posted given a number of events before the last.
	    /// &lt;p&gt;
	    /// Thus index 0 returns the last event added, index 1 returns the prior to the last event added
	    /// up to the maximum buffer size.
	    /// </summary>
	    /// <param name="index">prior event index from zero to max size</param>
	    /// <returns>prior event at given index</returns>
	    public EventBean Get(int index)
	    {
	        if (index >= size)
	        {
	            throw new ArgumentException("Invalid index " + index + " for size " + size);
	        }

	        // The newest event is at (nextFreeIndex + 1)
	        int newest = nextFreeIndex - 1;
	        int relative = newest - index;
	        if (relative < 0)
	        {
	            relative = relative + size;
	        }
	        return buffer[relative];
	    }
	}
} // End of namespace
