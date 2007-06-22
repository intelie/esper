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
	public class TestTimeBatchView
	{
	    private readonly static long TEST_INTERVAL_MSEC = 10000;

	    private TimeBatchView myView;
	    private SupportBeanClassView childView;
	    private SupportSchedulingServiceImpl schedulingServiceStub;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set the scheduling service to use
	        schedulingServiceStub = new SupportSchedulingServiceImpl();

	        // Set up length window view and a test child view
	        myView = new TimeBatchView(null, SupportStatementContextFactory.MakeContext(schedulingServiceStub), TEST_INTERVAL_MSEC, null, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void TestViewPushNoRefPoint()
	    {
	        long startTime = 1000000;
	        schedulingServiceStub.Time = (startTime);

	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);

	        EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
	            new String[] {"a1", "b1", "b2", "c1", "d1"});

	        // Send new events to the view - should have scheduled a callback for X msec after
	        myView.Update(new EventBean[] {events.Fetch("a1")}, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] {events.Fetch("a1")});
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);  // Data got batched, no data release till later

	        schedulingServiceStub.Time = (startTime + 5000);
	        myView.Update(new EventBean[] {events.Fetch("b1"), events.Fetch("b2")}, null);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] {events.Fetch("a1"), events.Fetch("b1"), events.Fetch("b2")});
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);

	        // Pretend we have a callback, check data, check scheduled new callback
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] {events.Fetch("a1"), events.Fetch("b1"), events.Fetch("b2")});
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Pretend callback received again, should schedule a callback since the last interval showed data
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 2);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[]{events.Fetch("a1"), events.Fetch("b1"), events.Fetch("b2")});   // Old data is published
	        SupportViewDataChecker.CheckNewData(childView, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Pretend callback received again, not schedule a callback since the this and last interval showed no data
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 3);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);

	        // Send new event to the view - pretend we are 500 msec into the interval
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 3 + 500);
	        myView.Update(new EventBean[]{ events.Fetch("c1")}, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC - 500) != null);
	        schedulingServiceStub.GetAdded().Clear();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{events.Fetch("c1")});
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);  // Data got batched, no data release till later

	        // Pretend callback received again
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 4);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{events.Fetch("c1")});
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Send new event to the view
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 4 + 500);
	        myView.Update(new EventBean[]{ events.Fetch("d1") }, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 0);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{events.Fetch("d1")});
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);

	        // Pretend callback again
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 5);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[]{events.Fetch("c1")});
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{events.Fetch("d1")});
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	        schedulingServiceStub.GetAdded().Clear();

	        // Pretend callback again
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 6);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[]{events.Fetch("d1")});
	        SupportViewDataChecker.CheckNewData(childView, null);

	        // Pretend callback again
	        schedulingServiceStub.Time = (startTime + TEST_INTERVAL_MSEC * 7);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);
	    }

	    [Test]
	    public void TestViewPushWithRefPoint()
	    {
	        long startTime = 50000;
	        schedulingServiceStub.Time = (startTime);

	        myView = new TimeBatchView(null, SupportStatementContextFactory.MakeContext(schedulingServiceStub), TEST_INTERVAL_MSEC, 1505L, null);
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);

	        EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
	            new String[] {"A1", "A2", "A3"});

	        // Send new events to the view - should have scheduled a callback for X msec after
	        myView.Update(new EventBean[]{ events.Fetch("A1"), events.Fetch("A2"), events.Fetch("A3")}, null);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(1505L) != null);
	        schedulingServiceStub.GetAdded().Clear();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[]{events.Fetch("A1"), events.Fetch("A2"), events.Fetch("A3")});
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, null);  // Data got batched, no data release till later

	        // Pretend we have a callback, check data, check scheduled new callback
	        schedulingServiceStub.Time = (startTime + 1505);
	        myView.SendBatch();
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),null);
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[]{events.Fetch("A1"), events.Fetch("A2"), events.Fetch("A3")});
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Count == 1);
	        Assert.IsTrue(schedulingServiceStub.GetAdded().Fetch(TEST_INTERVAL_MSEC) != null);
	    }

	    [Test]
	    public void TestComputeWaitMSec()
	    {
	        // With current=2300, ref=1000, and interval=500, expect 2500 as next interval and 200 as solution
	        long result = TimeBatchView.ComputeWaitMSec(2300, 1000, 500);
	        Assert.AreEqual(200, result);

	        result = TimeBatchView.ComputeWaitMSec(2300, 4200, 500);
	        Assert.AreEqual(400, result);

	        result = TimeBatchView.ComputeWaitMSec(2200, 4200, 500);
	        Assert.AreEqual(500, result);

	        result = TimeBatchView.ComputeWaitMSec(2200, 2200, 500);
	        Assert.AreEqual(500, result);

	        result = TimeBatchView.ComputeWaitMSec(2201, 2200, 500);
	        Assert.AreEqual(499, result);

	        result = TimeBatchView.ComputeWaitMSec(2600, 2200, 500);
	        Assert.AreEqual(100, result);

	        result = TimeBatchView.ComputeWaitMSec(2699, 2200, 500);
	        Assert.AreEqual(1, result);

	        result = TimeBatchView.ComputeWaitMSec(2699, 2700, 500);
	        Assert.AreEqual(1, result);

	        result = TimeBatchView.ComputeWaitMSec(2699, 2700, 10000);
	        Assert.AreEqual(1, result);

	        result = TimeBatchView.ComputeWaitMSec(2700, 2700, 10000);
	        Assert.AreEqual(10000, result);

	        result = TimeBatchView.ComputeWaitMSec(2700, 6800, 10000);
	        Assert.AreEqual(4100, result);

	        result = TimeBatchView.ComputeWaitMSec(23050, 16800, 10000);
	        Assert.AreEqual(3750, result);
	    }
	}
} // End of namespace
