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
	/**
	 * Provides relative access to insert stream events for certain window.
	 */
	public class IStreamRelativeAccess
		: RelativeAccessByEventNIndex
		, ViewUpdatedCollection
	{
	    private readonly EDictionary<EventBean, int> indexPerEvent;
	    private EventBean[] lastNewData;
	    private readonly IStreamRelativeAccessUpdateObserver updateObserver;

	    /**
	     * Ctor.
	     * @param updateObserver is invoked when updates are received
	     */
	    public IStreamRelativeAccess(IStreamRelativeAccessUpdateObserver updateObserver)
	    {
	        this.updateObserver = updateObserver;
	        indexPerEvent = new EHashDictionary<EventBean, int>();
	    }

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

	    /**
	     * For indicating that the collection has been updated.
	     */
	    public interface IStreamRelativeAccessUpdateObserver
	    {
	        /**
	         * Callback to indicate an update.
	         * @param iStreamRelativeAccess is the collection
	         * @param newData is the new data available
	         */
	        void Updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData);
	    }
	}
}
