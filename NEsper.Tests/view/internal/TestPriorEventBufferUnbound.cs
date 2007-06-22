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
using net.esper.view.internals;

namespace net.esper.view.internals
{
	[TestFixture]
	public class TestPriorEventBufferUnbound
	{
	    private PriorEventBufferUnbound buffer;
	    private EventBean[] events;

	    [SetUp]
	    public void SetUp()
	    {
	        buffer = new PriorEventBufferUnbound(3);

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
	        Assert.AreEqual(events[1], buffer.GetNewData(0));
	        Assert.AreEqual(events[0], buffer.GetNewData(1));
	        Assert.IsNull(buffer.GetNewData(2));
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        try
	        {
	            buffer.GetNewData(6);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
