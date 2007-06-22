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
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestIStreamRelativeAccessImpl
	{
	    private IStreamRelativeAccess access;
	    private EventBean[] events;

	    internal class VoidObserver : IStreamRelativeAccess.IStreamRelativeAccessUpdateObserver
        {
            public void Updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData)
            {
            }
        };


	    [SetUp]
	    public void SetUp()
	    {
	    	IStreamRelativeAccess.IStreamRelativeAccessUpdateObserver updateObserver = new VoidObserver();

	        events = new EventBean[100];
	        for (int i = 0; i < events.Length; i++)
	        {
	            events[i] = SupportEventBeanFactory.CreateObject(new SupportBean());
	        }
	    }

	    [Test]
	    public void TestGet()
	    {
	        access.Update(new EventBean[] {events[0]}, null);
	        Assert.AreEqual(events[0], access.GetRelativeToEvent(events[0], 0));
	        Assert.IsNull(access.GetRelativeToEvent(events[0], 1));

	        // sends the newest event last (i.e. 1 older 2 as 1 is sent first)
	        access.Update(new EventBean[] {events[1], events[2]}, null);
	        Assert.AreEqual(events[1], access.GetRelativeToEvent(events[1], 0));
	        Assert.IsNull(access.GetRelativeToEvent(events[1], 1));
	        Assert.AreEqual(events[2], access.GetRelativeToEvent(events[2], 0));
	        Assert.AreEqual(events[1], access.GetRelativeToEvent(events[2], 1));
	        Assert.IsNull(access.GetRelativeToEvent(events[2], 2));

	        // sends the newest event last (i.e. 1 older 2 as 1 is sent first)
	        access.Update(new EventBean[] {events[3], events[4], events[5]}, null);
	        Assert.AreEqual(events[3], access.GetRelativeToEvent(events[3], 0));
	        Assert.IsNull(access.GetRelativeToEvent(events[3], 1));
	        Assert.AreEqual(events[4], access.GetRelativeToEvent(events[4], 0));
	        Assert.AreEqual(events[3], access.GetRelativeToEvent(events[4], 1));
	        Assert.IsNull(access.GetRelativeToEvent(events[4], 2));
	        Assert.AreEqual(events[5], access.GetRelativeToEvent(events[5], 0));
	        Assert.AreEqual(events[4], access.GetRelativeToEvent(events[5], 1));
	        Assert.AreEqual(events[3], access.GetRelativeToEvent(events[5], 2));
	        Assert.IsNull(access.GetRelativeToEvent(events[5], 3));
	    }
	}
} // End of namespace
