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

namespace net.esper.view.window
{
	[TestFixture]
	public class TestLengthWindowView
	{
	    private LengthWindowView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new LengthWindowView(null, 5, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void TestIncorrectUse()
	    {
	        try
	        {
	            myView = new LengthWindowView(null, 0, null);
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected exception
	        }
	    }

	    // Check values against Microsoft Excel computed values
	    [Test]
	    public void TestViewPush()
	    {
	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 3);
	        stream.AddView(myView);

	        EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
	            new String[] {"a0", "b0", "b1", "c0", "c1", "d0", "e0", "e1", "e2", "f0", "f1",
	            "g0", "g1", "g2", "g3", "g4",
	            "h0", "h1", "h2", "h3", "h4", "h5", "h6",
	            "i0"});

	        // Fill the window with events up to the depth of 5
	        stream.Insert(MakeArray(events, new String[]{"a0"}));
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, MakeArray(events, new String[]{ "a0"}));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "a0"}));

	        stream.Insert(MakeArray(events, new String[]{"b0", "b1"}));
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, MakeArray(events, new String[]{ "b0", "b1"}));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "a0", "b0", "b1" }));

	        stream.Insert(MakeArray(events, new String[]{"c0", "c1"}));

	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "c0", "c1" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "a0", "b0", "b1", "c0", "c1" }));

	        // Send further events, expect to get events back that fall out of the window (a0)
	        stream.Insert(MakeArray(events, new String[]{"d0"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "a0" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "d0" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "b0", "b1", "c0", "c1", "d0" }));

	        stream.Insert(MakeArray(events, new String[]{"e0", "e1", "e2"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "b0", "b1", "c0" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "e0", "e1", "e2" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "c1", "d0", "e0", "e1", "e2" }));

	        stream.Insert(MakeArray(events, new String[]{"f0", "f1"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "c1", "d0" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "f0", "f1" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "e0", "e1", "e2", "f0", "f1" }));

	        // Push as many events as the window takes
	        stream.Insert(MakeArray(events, new String[]{"g0", "g1", "g2", "g3", "g4"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "e0", "e1", "e2", "f0", "f1" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4" }));

	        // Push 2 more events then the window takes
	        stream.Insert(MakeArray(events, new String[]{"h0", "h1", "h2", "h3", "h4", "h5", "h6"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "g0", "g1", "g2", "g3", "g4", "h0", "h1" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "h0", "h1", "h2", "h3", "h4", "h5", "h6" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "h2", "h3", "h4", "h5", "h6" }));

	        // Push 1 last event to make sure the last overflow was handled correctly
	        stream.Insert(MakeArray(events, new String[]{"i0"}));
	        SupportViewDataChecker.CheckOldData(childView, MakeArray(events, new String[]{ "h2" }));
	        SupportViewDataChecker.CheckNewData(childView,MakeArray(events, new String[]{ "i0" }));
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),MakeArray(events, new String[]{ "h3", "h4", "h5", "h6", "i0" }));
	    }

	    private EventBean[] MakeArray(EDictionary<String, EventBean> events, String[] ids)
	    {
	        return EventFactoryHelper.MakeArray(events, ids);
	    }
	}
} // End of namespace
