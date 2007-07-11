///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.join
{
	[TestFixture]
	public class TestJoinSetFilter
	{
	    [Test]
	    public void testFilter()
	    {
	        ExprNode topNode = SupportExprNodeFactory.Make2SubNodeAnd();

	        EventBean[] pairOne = new EventBean[2];
	        pairOne[0] = MakeEvent(1, 2, "a");
	        pairOne[1] = MakeEvent(2, 1, "a");

	        EventBean[] pairTwo = new EventBean[2];
	        pairTwo[0] = MakeEvent(1, 2, "a");
	        pairTwo[1] = MakeEvent(2, 999, "a");

	        Set<MultiKey<EventBean>> eventSet = new HashSet<MultiKey<EventBean>>();
	        eventSet.Add(new MultiKey<EventBean>(pairOne));
	        eventSet.Add(new MultiKey<EventBean>(pairTwo));

	        JoinSetFilter.Filter(topNode, eventSet, true);

	        Assert.AreEqual(1, eventSet.Count);
	        Assert.AreSame(pairOne, CollectionHelper.First(eventSet).Array);
	    }

	    private EventBean MakeEvent(int intPrimitive, int intBoxed, String _string)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        _event.SetIntBoxed(intBoxed);
	        _event.SetString(_string);
	        return SupportEventBeanFactory.CreateObject(_event);
	    }
	}
} // End of namespace
