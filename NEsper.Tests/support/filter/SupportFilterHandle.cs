// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{
	public class SupportFilterHandle : FilterHandleCallback
	{
	    private int countInvoked;
	    private EventBean lastEvent;

	    public void MatchFound(EventBean _event)
	    {
	        countInvoked++;
	        lastEvent = _event;
	    }

	    public int GetCountInvoked()
	    {
	        return countInvoked;
	    }

	    public EventBean GetLastEvent()
	    {
	        return lastEvent;
	    }

	    public void SetCountInvoked(int countInvoked)
	    {
	        this.countInvoked = countInvoked;
	    }

	    public void SetLastEvent(EventBean lastEvent)
	    {
	        this.lastEvent = lastEvent;
	    }

	    public int GetAndResetCountInvoked()
	    {
	        int count = countInvoked;
	        countInvoked = 0;
	        return count;
	    }
	}
} // End of namespace
