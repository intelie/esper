///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System.Collections.Generic;

using net.esper.collection;
using net.esper.events;

namespace net.esper.view.window
{
	/// <summary>
	/// For use with length and time window views that must provide random access into data window contents
	/// provided for the &quot;previous&quot; expression if used.
	/// </summary>
	public class IStreamRandomAccess
		: RandomAccessByIndex
		, ViewUpdatedCollection
	{
	    private List<EventBean> arrayList;
	    private readonly IStreamRandomAccessUpdateObserver updateObserver;

	    /// <summary>Ctor.</summary>
	    /// <param name="updateObserver">is invoked when updates are received</param>
	    public IStreamRandomAccess(IStreamRandomAccessUpdateObserver updateObserver)
	    {
	        this.updateObserver = updateObserver;
	        this.arrayList = new List<EventBean>();
	    }

        /// <summary>
        /// Accepts view insert and remove stream.
        /// </summary>
        /// <param name="newData">is the insert stream events or null if no data</param>
        /// <param name="oldData">is the remove stream events or null if no data</param>
	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        updateObserver.Updated(this);
	        if (newData != null)
	        {
	            for (int i = 0; i < newData.Length; i++)
	            {
	                arrayList.Insert(0, newData[i]);
	            }
	        }

	        if (oldData != null)
	        {
	            for (int i = 0; i < oldData.Length; i++)
	            {
	                arrayList.RemoveAt(arrayList.Count - 1);
	            }
	        }
	    }

        /// <summary>
        /// Returns an new data event given an index.
        /// </summary>
        /// <param name="index">to return new data for</param>
        /// <returns>new data event</returns>
	    public EventBean GetNewData(int index)
	    {
	        // New events are added to the start of the list
	        if (index < arrayList.Count )
	        {
	            return arrayList[index];
	        }
	        return null;
	    }

        /// <summary>
        /// Returns an old data event given an index.
        /// </summary>
        /// <param name="index">to return old data for</param>
        /// <returns>old data event</returns>
	    public EventBean GetOldData(int index)
	    {
	        return null;
	    }

	    /// <summary>For indicating that the collection has been updated.</summary>
	    public interface IStreamRandomAccessUpdateObserver
	    {
	        /// <summary>Callback to indicate an update</summary>
	        /// <param name="iStreamRandomAccess">is the collection</param>
	        void Updated(IStreamRandomAccess iStreamRandomAccess);
	    }
	}
}
