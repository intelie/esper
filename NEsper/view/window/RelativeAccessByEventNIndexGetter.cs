///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.view.window
{
	/// <summary>
	/// Provides random-access into window contents by event and index as a combination.
	/// </summary>
	public class RelativeAccessByEventNIndexGetter : IStreamRelativeAccess.IStreamRelativeAccessUpdateObserver
	{
	    private readonly EDictionary<EventBean, IStreamRelativeAccess> accessorByEvent = new EHashDictionary<EventBean, IStreamRelativeAccess>();
	    private readonly EDictionary<IStreamRelativeAccess, EventBean[]> eventsByAccessor  = new EHashDictionary<IStreamRelativeAccess, EventBean[]>();

        /// <summary>
        /// </summary>
        /// <param name="iStreamRelativeAccess"></param>
        /// <param name="newData"></param>
        /// Callback to indicate an update.
        /// @param iStreamRelativeAccess is the collection
        /// @param newData is the new data available
	    public void Updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData)
	    {
	        // remove data posted from the last update
	        EventBean[] lastNewData = eventsByAccessor.Fetch(iStreamRelativeAccess);
	        if (lastNewData != null)
	        {
	            for (int i = 0; i < lastNewData.Length; i++)
	            {
	                accessorByEvent.Remove(lastNewData[i]);
	            }
	        }

	        if (newData == null)
	        {
	            return;
	        }

	        // hold accessor per event for querying
	        for (int i = 0; i < newData.Length; i++)
	        {
	            accessorByEvent.Put(newData[i], iStreamRelativeAccess);
	        }

	        // save new data for access to later removal
	        eventsByAccessor[iStreamRelativeAccess] = newData;
	    }

	    /// <summary>Returns the access into window contents given an event.</summary>
	    /// <param name="_event">to which the method returns relative access from</param>
	    /// <returns>buffer</returns>
	    public IStreamRelativeAccess GetAccessor(EventBean _event)
	    {
	        IStreamRelativeAccess iStreamRelativeAccess = accessorByEvent.Fetch(_event);
	        if (iStreamRelativeAccess == null)
	        {
                throw new IllegalStateException("Accessor for window random access not found for event " + _event);
	        }
	        return iStreamRelativeAccess;
	    }
	}
} // End of namespace
