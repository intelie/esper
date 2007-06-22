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
using net.esper.eql.agg;
using net.esper.events;

namespace net.esper.support.eql
{
	public class SupportAggregationService : AggregationService
	{
	    private IList<Pair<EventBean[], MultiKeyUntyped>> leaveList = new List<Pair<EventBean[], MultiKeyUntyped>>();
	    private IList<Pair<EventBean[], MultiKeyUntyped>> enterList = new List<Pair<EventBean[], MultiKeyUntyped>>();

	    public void ApplyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        leaveList.Add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
	    }

	    public void ApplyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
	    {
	        enterList.Add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
	    }

	    public IList<Pair<EventBean[], MultiKeyUntyped>> LeaveList
	    {
	        get { return leaveList; }
	    }

	    public IList<Pair<EventBean[], MultiKeyUntyped>> EnterList
	    {
            get { return enterList; }
	    }

	    public void SetCurrentRow(MultiKeyUntyped groupKey)
	    {
	    }

	    public Object GetValue(int column)
	    {
	        return null;
	    }
	}
} // End of namespace
