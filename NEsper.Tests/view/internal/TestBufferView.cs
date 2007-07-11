///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;

namespace net.esper.view.internals
{
	[TestFixture]
	public class TestBufferView
	{
	    private BufferView bufferView;
	    private SupportBufferObserver observer;

	    [SetUp]
	    public void SetUp()
	    {
	        observer = new SupportBufferObserver();
	        bufferView = new BufferView(1);
	        bufferView.Observer = (observer);
	    }

	    [Test]
	    public void testUpdate()
	    {
	        // Observer starts with no data
	        Assert.IsFalse(observer.GetAndResetHasNewData());

	        // Send some data
	        EventBean[] newEvents = MakeBeans("n", 1);
	        EventBean[] oldEvents = MakeBeans("o", 1);
	        bufferView.Update(newEvents, oldEvents);

	        // make sure received
	        Assert.IsTrue(observer.GetAndResetHasNewData());
	        Assert.AreEqual(1, observer.GetAndResetStreamId());
	        Assert.IsNotNull(observer.GetAndResetNewEventBuffer());
	        Assert.IsNotNull(observer.GetAndResetOldEventBuffer());

	        // Reset and send null data
	        Assert.IsFalse(observer.GetAndResetHasNewData());
	        bufferView.Update(null, null);
	        Assert.IsTrue(observer.GetAndResetHasNewData());
	    }

	    private EventBean[] MakeBeans(String id, int numTrades)
	    {
	        EventBean[] trades = new EventBean[numTrades];
	        for (int i = 0; i < numTrades; i++)
	        {
	            SupportBean_A bean = new SupportBean_A(id + i);
	            trades[i] = SupportEventBeanFactory.CreateObject(bean);
	        }
	        return trades;
	    }
	}
} // End of namespace
