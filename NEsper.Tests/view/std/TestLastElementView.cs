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

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestLastElementView
	{
	    private LastElementView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new LastElementView();
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void TestViewPush()
	    {
	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 3);
	        stream.AddView(myView);

	        EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
	            new String[] {"a0", "a1", "b0", "c0", "c1", "c2", "d0", "e0"});

	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), null);

	        // View should keep the last element for iteration, should report new data as it arrives
	        stream.Insert(new EventBean[] {events.Fetch("a0"), events.Fetch("a1")});
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { events.Fetch("a0") });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { events.Fetch("a0"), events.Fetch("a1") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { events.Fetch("a1") });

	        stream.Insert(new EventBean[] {events.Fetch("b0")});
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { events.Fetch("a1") });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { events.Fetch("b0") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { events.Fetch("b0") });

	        stream.Insert(new EventBean[] {events.Fetch("c0"),events.Fetch("c1"),events.Fetch("c2")});
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { events.Fetch("b0"), events.Fetch("c0"), events.Fetch("c1") });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { events.Fetch("c0"), events.Fetch("c1"), events.Fetch("c2") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { events.Fetch("c2") });

	        stream.Insert(new EventBean[] {events.Fetch("d0")});
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { events.Fetch("c2") });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { events.Fetch("d0") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { events.Fetch("d0") });

	        stream.Insert(new EventBean[] {events.Fetch("e0")});
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { events.Fetch("d0") });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { events.Fetch("e0") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(), new EventBean[] { events.Fetch("e0") });
	    }
	}
} // End of namespace
