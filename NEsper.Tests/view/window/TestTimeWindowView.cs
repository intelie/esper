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
using net.esper.support.schedule;
using net.esper.support.util;
using net.esper.support.view;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestTimeWindowView
	{
	    private readonly static long TEST_WINDOW_MSEC = 60000;

	    private TimeWindowView myView;
	    private SupportBeanClassView childView;
	    private SupportSchedulingServiceImpl schedulingServiceStub;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set the scheduling service to use
	        schedulingServiceStub = new SupportSchedulingServiceImpl();

	        // Set up length window view and a test child view
	        myView = new TimeWindowView(SupportStatementContextFactory.MakeContext(schedulingServiceStub), null, TEST_WINDOW_MSEC, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testViewPushAndExpire()
	    {
	        long startTime = 1000000;
	        schedulingServiceStub.Time = (startTime);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);

	        EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
	            new String[] {"a1", "b1", "b2", "c1", "d1", "e1", "f1", "f2"});

	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, null);

	        // Send new events to the view - should have scheduled a callback for X msec after
	        myView.Update(new EventBean[]{ events.Fetch("a1") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_WINDOW_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("a1") });
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("a1") });

	        // Send more events, check
	        schedulingServiceStub.Time = (startTime + 10000);
	        myView.Update(new EventBean[]{ events.Fetch("b1"), events.Fetch("b2") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);

	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("a1"), events.Fetch("b1"), events.Fetch("b2") });
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("b1"), events.Fetch("b2") });

	        // Send more events, check
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC - 1);
	        myView.Update(new EventBean[]{ events.Fetch("c1") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);

	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("a1"), events.Fetch("b1"), events.Fetch("b2"), events.Fetch("c1") });
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("c1") });

	        // Pretend we are getting the callback from scheduling, check old data and check new scheduling
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC);
	        myView.Expire();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("b1"), events.Fetch("b2"), events.Fetch("c1") });
	        SupportViewDataChecker.CheckOldData(childView,new EventBean[]{ events.Fetch("a1") });
	        SupportViewDataChecker.CheckNewData(childView, null);

	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(10000L) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Send another 2 events
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC);
	        myView.Update(new EventBean[]{ events.Fetch("d1") }, null);
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("d1") });

	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 1);
	        myView.Update(new EventBean[]{ events.Fetch("e1") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("e1") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("b1"), events.Fetch("b2"), events.Fetch("c1"), events.Fetch("d1"), events.Fetch("e1") } );

	        // Pretend callback received
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 10000);
	        myView.Expire();
	        SupportViewDataChecker.CheckOldData(childView,new EventBean[]{ events.Fetch("b1"), events.Fetch("b2") });
	        SupportViewDataChecker.CheckNewData(childView, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("c1"), events.Fetch("d1"), events.Fetch("e1") } );

	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(49999L) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Pretend callback received
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 59999);
	        myView.Expire();
	        SupportViewDataChecker.CheckOldData(childView,new EventBean[]{ events.Fetch("c1") });
	        SupportViewDataChecker.CheckNewData(childView, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("d1"), events.Fetch("e1") });

	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(1L) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Send another event
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 200);
	        myView.Update(new EventBean[]{ events.Fetch("f1"), events.Fetch("f2") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	        SupportViewDataChecker.CheckOldData(childView,null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{ events.Fetch("f1"), events.Fetch("f2") });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("d1"), events.Fetch("e1"), events.Fetch("f1"), events.Fetch("f2") });

	        // Pretend callback received, we didn't schedule for 1 msec after, but for 100 msec after
	        // testing what happens when clock resolution or some other delay happens
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 60099);
	        myView.Expire();
	        SupportViewDataChecker.CheckOldData(childView,new EventBean[]{ events.Fetch("d1"), events.Fetch("e1") });
	        SupportViewDataChecker.CheckNewData(childView, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{ events.Fetch("f1"), events.Fetch("f2") } );

	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(101L) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Pretend callback received
	        schedulingServiceStub.Time = (startTime + TEST_WINDOW_MSEC + 60201);
	        myView.Expire();
	        SupportViewDataChecker.CheckOldData(childView,new EventBean[]{ events.Fetch("f1"), events.Fetch("f2") });
	        SupportViewDataChecker.CheckNewData(childView, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	    }

	    public EventBean[] MakeEvents(String[] ids)
	    {
	        return EventFactoryHelper.MakeEvents(ids);
	    }
	}
} // End of namespace
