///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.collection;
using net.esper.events;
using net.esper.view.window;

namespace net.esper.view.internals
{


	/// <summary>
	/// Buffer class for insert stream events only for use with unbound streams that inserts data only, to serve
	/// up one or more prior events in the insert stream based on an index.
	/// &lt;p&gt;
	/// Does not expect or care about the remove stream and simple keeps a rolling buffer of new data events
	/// up to the maximum prior event we are asking for.
	/// </summary>
	public class PriorEventBufferUnbound : ViewUpdatedCollection, RandomAccessByIndex
	{
	    private readonly int maxSize;
        private readonly RollingEventBuffer newEvents;

	    /// <summary>Ctor.</summary>
	    /// <param name="maxPriorIndex">
	    /// is the highest prior-event index required by any expression
	    /// </param>
	    public PriorEventBufferUnbound(int maxPriorIndex)
	    {
	        this.maxSize = maxPriorIndex + 1;
	        newEvents = new RollingEventBuffer(maxSize);
	    }

	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        // Post new data to rolling buffer starting with the oldest
	        if (newData != null)
	        {
	            for (int i = 0; i < newData.length; i++)
	            {
	                EventBean newEvent = newData[i];

	                // Add new event
	                newEvents.Add(newEvent);
	            }
	        }
	    }

	    public EventBean GetNewData(int index)
	    {
	        if (index >= maxSize)
	        {
	            throw new IllegalArgumentException("Index " + index + " not allowed, max size is " + maxSize);
	        }
	        return newEvents.Get(index);
	    }

	    public EventBean GetOldData(int index)
	    {
	        return null;
	    }
	}
} // End of namespace
