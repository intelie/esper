// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.events;
using net.esper.util;

namespace net.esper.collection
{
	[TestFixture]
	public class TestFlushedEventBuffer
	{
	    private FlushedEventBuffer buffer;
	    private EventBean[] events;

	    [SetUp]
	    public void SetUp()
	    {
	        buffer = new FlushedEventBuffer();
	        events = new EventBean[10];

	        for (int i = 0; i < events.Length; i++)
	        {
	            events[i] = SupportEventBeanFactory.CreateObject(i);
	        }
	    }

	    [Test]
	    public void TestFlow()
	    {
	        // test empty buffer
	        buffer.Add(null);
	        Assert.IsNull(buffer.GetAndFlush());
	        buffer.Flush();

	        // test add single events
	        buffer.Add(new EventBean[] { events[0] });
	        EventBean[] results = buffer.GetAndFlush();
	        Assert.IsTrue((results.Length == 1) && (results[0] == events[0]));

	        buffer.Add(new EventBean[] { events[0] });
	        buffer.Add(new EventBean[] { events[1] });
	        results = buffer.GetAndFlush();
	        Assert.IsTrue((results.Length == 2));
	        Assert.AreSame(events[0], results[0]);
	        Assert.AreSame(events[1], results[1]);

	        buffer.Flush();
	        Assert.IsNull(buffer.GetAndFlush());

	        // Add multiple events
	        buffer.Add(new EventBean[] { events[2], events[3] });
	        buffer.Add(new EventBean[] { events[4], events[5] });
	        results = buffer.GetAndFlush();
	        Assert.IsTrue((results.Length == 4));
	        Assert.AreSame(events[2], results[0]);
	        Assert.AreSame(events[3], results[1]);
	        Assert.AreSame(events[4], results[2]);
	        Assert.AreSame(events[5], results[3]);

	        buffer.Flush();
	        Assert.IsNull(buffer.GetAndFlush());
	    }
	}
} // End of namespace
