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

namespace net.esper.view.window
{
	/// <summary>
	/// Provides relative access to insert stream events for certain window.
	/// </summary>
	public class IStreamRelativeAccess
		: RelativeAccessByEventNIndex
		, ViewUpdatedCollection
	{
	    private readonly EDictionary<EventBean, int> indexPerEvent;
	    private EventBean[] lastNewData;
	    private readonly IStreamRelativeAccessUpdateObserver updateObserver;

	    /// <summary>Ctor.</summary>
	    /// <param name="updateObserver">is invoked when updates are received</param>
	    public IStreamRelativeAccess(IStreamRelativeAccessUpdateObserver updateObserver)
	    {
	        this.updateObserver = updateObserver;
	        indexPerEvent = new HashDictionary<EventBean, int>();
	    }

        /// <summary>
        /// Accepts view insert and remove stream.
        /// </summary>
        /// <param name="newData">is the insert stream events or null if no data</param>
        /// <param name="oldData">is the remove stream events or null if no data</param>
	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        updateObserver.Updated(this, newData);
	        indexPerEvent.Clear();
	        lastNewData = newData;

	        if (newData != null)
	        {
	            for (int i = 0; i < newData.Length; i++)
	            {
	                indexPerEvent[newData[i]] = i;
	            }
	        }
	    }

        /// <summary>
        /// Gets the relative to event.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <param name="prevIndex">Index of the prev.</param>
        /// <returns></returns>
	    public EventBean GetRelativeToEvent(EventBean _event, int prevIndex)
	    {
	        if (lastNewData == null)
	        {
	            return null;
	        }

	        if (prevIndex == 0)
	        {
	            return _event;
	        }

			int indexIncoming;
			if (!indexPerEvent.TryGetValue(_event, out indexIncoming))
	        {
	            return null;
	        }

	        if (prevIndex > indexIncoming)
	        {
	            return null;
	        }

	        int relativeIndex = indexIncoming - prevIndex;
	        if ((relativeIndex < lastNewData.Length) && (relativeIndex >= 0))
	        {
	            return lastNewData[relativeIndex];
	        }
	        return null;
	    }

	    /// <summary>For indicating that the collection has been updated.</summary>
	    public interface IStreamRelativeAccessUpdateObserver
	    {
            /// <summary>
            /// Callback to indicate an update.
            /// </summary>
            /// <param name="iStreamRelativeAccess">is the collection</param>
            /// <param name="newData">is the new data available</param>
	        void Updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData);
	    }
	}
}
