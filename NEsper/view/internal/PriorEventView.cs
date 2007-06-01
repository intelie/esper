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
using net.esper.events;
using net.esper.view;

namespace net.esper.view.internals
{
	/// <summary>
	/// View that provides access to prior events posted by the parent view for use by 'prior' expression nodes.
	/// </summary>
	public class PriorEventView : ViewSupport
	{
	    private ViewUpdatedCollection buffer;

	    /// <summary>Ctor.</summary>
	    /// <param name="buffer">
	    /// is handling the actual storage of events for use in the 'prior' expression
	    /// </param>
	    public PriorEventView(ViewUpdatedCollection buffer)
	    {
	        this.buffer = buffer;
	    }

	    public void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        buffer.Update(newData, oldData);
	        this.UpdateChildren(newData, oldData);
	    }

	    /// <summary>Returns the underlying buffer used for access to prior events.</summary>
	    /// <returns>buffer</returns>
	    protected ViewUpdatedCollection GetBuffer()
	    {
	        return buffer;
	    }

	    public EventType GetEventType()
	    {
	        return parent.EventType;
	    }

	    public IEnumerator<EventBean> GetEnumerator()
	    {
	        return parent.GetEnumerator();
	    }
	}
} // End of namespace
