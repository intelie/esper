///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{
	public class SupportEventEvaluator : EventEvaluator
	{
	    private int countInvoked;
	    private EventBean lastEvent;
	    private IList<FilterHandle> lastMatches;

	    public void MatchEvent(EventBean _event, IList<FilterHandle> matches)
	    {
	        countInvoked++;
	        lastEvent = _event;
	        lastMatches = matches;
	    }

	    public EventBean GetLastEvent()
	    {
	        return lastEvent;
	    }

	    public IList<FilterHandle> GetLastMatches()
	    {
	        return lastMatches;
	    }

	    public void SetCountInvoked(int countInvoked)
	    {
	        this.countInvoked = countInvoked;
	    }

	    public void SetLastEvent(EventBean lastEvent)
	    {
	        this.lastEvent = lastEvent;
	    }

	    public void SetLastMatches(List<FilterHandle> lastMatches)
	    {
	        this.lastMatches = lastMatches;
	    }

	    public int GetAndResetCountInvoked()
	    {
	        int count = countInvoked;
	        countInvoked = 0;
	        return count;
	    }
	}
} // End of namespace
