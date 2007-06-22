// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.view.internals;

namespace net.esper.view.internals
{
	[TestFixture]
	public class TestPriorEventBufferSingle
	{
	    private PriorEventBufferSingle buffer;
	    private EventBean[] events;

	    [SetUp]
	    public void SetUp()
	    {
	        buffer = new PriorEventBufferSingle(3);

	        events = new EventBean[100];
	        for (int i = 0; i < events.Length; i++)
	        {
	            SupportBean_S0 bean = new SupportBean_S0(i);
	            events[i] = SupportEventBeanFactory.CreateObject(bean);
	        }
	    }

	    [Test]
	    public void TestFlow()
	    {
	        buffer.Update(new EventBean[] {events[0], events[1]}, null);
	        AssertEvents0And1();

	        buffer.Update(new EventBean[] {events[2]}, null);
	        AssertEvents0And1();
	        AssertEvents2();

	        buffer.Update(new EventBean[] {events[3], events[4]}, null);
	        AssertEvents0And1();
	        AssertEvents2();
	        AssertEvents3And4();

	        buffer.Update(null, new EventBean[] {events[0]});
	        AssertEvents0And1();
	        AssertEvents2();
	        AssertEvents3And4();

	        buffer.Update(null, new EventBean[] {events[1], events[3]});
	        TryInvalid(events[0], 0);
	        Assert.IsNull(buffer.GetRelativeToEvent(events[1], 0));
	        AssertEvents2();
	        AssertEvents3And4();

	        buffer.Update(new EventBean[] {events[5]}, null);
	        TryInvalid(events[0], 0);
	        TryInvalid(events[1], 0);
	        TryInvalid(events[3], 0);
	        AssertEvents2();
	        Assert.AreEqual(events[1], buffer.GetRelativeToEvent(events[4], 0));
	        Assert.AreEqual(events[2], buffer.GetRelativeToEvent(events[5], 0));
	    }

	    private void AssertEvents0And1()
	    {
	        Assert.IsNull(buffer.GetRelativeToEvent(events[0], 0));     // getting 0 is getting prior 1 (see indexes)
	        Assert.IsNull(buffer.GetRelativeToEvent(events[1], 0));
	    }

	    private void AssertEvents2()
	    {
	        Assert.IsNull(buffer.GetRelativeToEvent(events[2], 0));
	    }

	    private void AssertEvents3And4()
	    {
	        Assert.AreEqual(events[0], buffer.GetRelativeToEvent(events[3], 0));
	        Assert.AreEqual(events[1], buffer.GetRelativeToEvent(events[4], 0));
	    }

	    public void TryInvalid(EventBean _event, int index)
	    {
	        try
	        {
	            buffer.GetRelativeToEvent(_event, index);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        try
	        {
	            buffer.GetRelativeToEvent(events[1], 2);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
