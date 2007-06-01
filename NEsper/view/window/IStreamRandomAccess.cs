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
	 * For use with length and time window views that must provide random access into data window contents
	 * provided for the "previous" expression if used.
	 */
	public class IStreamRandomAccess 
		: RandomAccessByIndex
		, ViewUpdatedCollection
	{
	    private List<EventBean> arrayList;
	    private readonly IStreamRandomAccessUpdateObserver updateObserver;

	    /**
	     * Ctor.
	     * @param updateObserver is invoked when updates are received
	     */
	    public IStreamRandomAccess(IStreamRandomAccessUpdateObserver updateObserver)
	    {
	        this.updateObserver = updateObserver;
	        this.arrayList = new List<EventBean>();
	    }

	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        updateObserver.Updated(this);
	        if (newData != null)
	        {
	            for (int i = 0; i < newData.Length; i++)
	            {
	                arrayList.Add(0, newData[i]);
	            }
	        }

	        if (oldData != null)
	        {
	            for (int i = 0; i < oldData.Length; i++)
	            {
	                arrayList.Remove(arrayList.Count - 1);
	            }
	        }
	    }

	    public EventBean GetNewData(int index)
	    {
	        // New events are added to the start of the list
	        if (index < arrayList.Count )
	        {
	            return arrayList[index];
	        }
	        return null;
	    }

	    public EventBean GetOldData(int index)
	    {
	        return null;
	    }

	    /**
	     * For indicating that the collection has been updated.
	     */
	    public interface IStreamRandomAccessUpdateObserver
	    {
	        /**
	         * Callback to indicate an update
	         * @param iStreamRandomAccess is the collection
	         */
	        public void Updated(IStreamRandomAccess iStreamRandomAccess);
	    }
	}
}