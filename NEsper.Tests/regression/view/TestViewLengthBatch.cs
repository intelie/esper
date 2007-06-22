// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestViewLengthBatch
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	    private SupportBean[] events;

	    [SetUp]
	    public void SetUp()
	    {
	        listener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();

	        events = new SupportBean[100];
	        for (int i = 0; i < events.Length; i++)
	        {
	            events[i] = new SupportBean();
	        }
	    }

	    [Test]
	    public void TestLengthBatchSize2()
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(
	                "select * from " + typeof(SupportBean).FullName + ".win:length_batch(2)");
	        stmt.AddListener(listener);

	        SendEvent(events[0]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[0] });

	        SendEvent(events[1]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[0], events[1]}, null);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[2]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[2] });

	        SendEvent(events[3]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[2], events[3]}, new SupportBean[] {events[0], events[1]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[4]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[4] });

	        SendEvent(events[5]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[4], events[5]}, new SupportBean[] {events[2], events[3]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);
	    }

	    [Test]
	    public void TestLengthBatchSize1()
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(
	                "select * from " + typeof(SupportBean).FullName + ".win:length_batch(1)");
	        stmt.AddListener(listener);

	        SendEvent(events[0]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[0]}, null);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[1]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[1]}, new SupportBean[] {events[0]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[2]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[2]}, new SupportBean[] {events[1]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);
	    }

	    [Test]
	    public void TestLengthBatchSize3()
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(
	                "select * from " + typeof(SupportBean).FullName + ".win:length_batch(3)");
	        stmt.AddListener(listener);

	        SendEvent(events[0]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[0] });

	        SendEvent(events[1]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[0], events[1]});

	        SendEvent(events[2]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[3]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[3]});

	        SendEvent(events[4]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), new SupportBean[] {events[3], events[4]});

	        SendEvent(events[5]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);
	    }

	    [Test]
	    public void TestLengthBatchSize3And2Staggered()
	    {
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(
	                "select * from " + typeof(SupportBean).FullName + ".win:length_batch(3).win:length_batch(2)");
	        stmt.AddListener(listener);

	        SendEvent(events[0]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[1]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[2]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[0], events[1], events[2]}, null);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[3]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[4]);
	        Assert.IsFalse(listener.IsInvoked);
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);

	        SendEvent(events[5]);
	        listener.AssertUnderlyingAndReset(new SupportBean[] {events[3], events[4], events[5]}, new SupportBean[] {events[0], events[1], events[2]});
	        ArrayAssertionUtil.AreEqualExactOrderUnderlying(stmt.GetEnumerator(), null);
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(
	                "select * from " + typeof(SupportMarketDataBean).FullName + ".win:length_batch(0)");
	            Assert.Fail();
	        }
	        catch (Exception ex)
	        {
	            // expected
	        }
	    }

	    private void SendEvent(SupportBean _event)
	    {
	        epService.EPRuntime.SendEvent(_event);
	    }
	}
} // End of namespace
