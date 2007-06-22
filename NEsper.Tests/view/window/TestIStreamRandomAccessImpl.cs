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
using net.esper.view.window;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestIStreamRandomAccessImpl
	{
	    private IStreamRandomAccess access;
	    private EventBean[] events;

	    internal class VoidObserver : IStreamRandomAccess.IStreamRandomAccessUpdateObserver
        {
            public void Updated(IStreamRandomAccess iStreamRandomAccess)
            {
            }
        }

	    [SetUp]
	    public void SetUp()
	    {
	    	access = new IStreamRandomAccess(new VoidObserver());
	        events = new EventBean[100];
	        for (int i = 0; i < events.Length; i++)
	        {
	            events[i] = SupportEventBeanFactory.CreateObject(new SupportBean());
	        }
	    }

	    [Test]
	    public void TestFlow()
	    {
	        Assert.IsNull(access.GetNewData(0));
	        Assert.IsNull(access.GetOldData(0));

	        access.Update(new EventBean[] {events[0]}, null);
	        Assert.AreEqual(events[0], access.GetNewData(0));
	        Assert.IsNull(access.GetNewData(1));
	        Assert.IsNull(access.GetOldData(0));

	        access.Update(new EventBean[] {events[1], events[2]}, null);
	        Assert.AreEqual(events[2], access.GetNewData(0));
	        Assert.AreEqual(events[1], access.GetNewData(1));
	        Assert.AreEqual(events[0], access.GetNewData(2));
	        Assert.IsNull(access.GetNewData(3));
	        Assert.IsNull(access.GetOldData(0));

	        access.Update(new EventBean[] {events[3]}, new EventBean[] {events[0]});
	        Assert.AreEqual(events[3], access.GetNewData(0));
	        Assert.AreEqual(events[2], access.GetNewData(1));
	        Assert.AreEqual(events[1], access.GetNewData(2));
	        Assert.IsNull(access.GetNewData(3));
	        Assert.IsNull(access.GetOldData(0));

	        access.Update(null, new EventBean[] {events[1], events[2]});
	        Assert.AreEqual(events[3], access.GetNewData(0));
	        Assert.IsNull(access.GetNewData(1));
	        Assert.IsNull(access.GetOldData(0));

	        access.Update(null, new EventBean[] {events[3]});
	        Assert.IsNull(access.GetNewData(0));
	        Assert.IsNull(access.GetOldData(0));
	    }
	}
} // End of namespace
