///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestSizeView
	{
	    private SizeView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new SizeView(SupportStatementContextFactory.MakeContext());

	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    // Check values against Microsoft Excel computed values
	    [Test]
	    public void testViewPush()
	    {
	        // Set up a feed for the view under test - it will have a depth of 5 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 5);
	        stream.AddView(myView);

	        CheckIterator(0);

	        // View just counts the number of events received, removing those removed in the prior view as old data
	        stream.Insert(MakeBeans("a", 1));
	        CheckOldData(0);
	        CheckNewData(1);
	        CheckIterator(1);

	        stream.Insert(MakeBeans("b", 2));
	        CheckOldData(1);
	        CheckNewData(3);
	        CheckIterator(3);

	        // The EventStream has a depth of 3, it will expire the first message now, ie. will keep the size of 3, always
	        stream.Insert(MakeBeans("c", 1));
	        CheckOldData(3);
	        CheckNewData(4);
	        CheckIterator(4);

	        stream.Insert(MakeBeans("d", 1));
	        CheckOldData(4);
	        CheckNewData(5);
	        CheckIterator(5);

	        stream.Insert(MakeBeans("e", 2));
	        Assert.IsNull(childView.LastNewData);
	        Assert.IsNull(childView.LastOldData);
	        CheckIterator(5);

	        stream.Insert(MakeBeans("f", 1));
	        Assert.IsNull(childView.LastNewData);
	        Assert.IsNull(childView.LastOldData);
	        CheckIterator(5);
	    }

	    [Test]
	    public void testUpdate()
	    {
	        // View should not post events if data didn't change
	        myView.Update(MakeBeans("f", 1), null);

	        CheckOldData(0);
	        CheckNewData(1);
	        childView.LastNewData = null;
	        childView.LastOldData = null;

	        myView.Update(MakeBeans("f", 1), MakeBeans("f", 1));

	        Assert.IsNull(childView.LastNewData);
	        Assert.IsNull(childView.LastOldData);
	    }

	    [Test]
	    public void testSchema()
	    {
	        SizeView view = new SizeView(SupportStatementContextFactory.MakeContext());

	        EventType eventType = view.EventType;
            Assert.AreEqual(typeof(long), eventType.GetPropertyType(ViewFieldEnum.SIZE_VIEW__SIZE.Name));
	    }

	    [Test]
	    public void testCopyView()
	    {
	        Assert.IsTrue(myView.CloneView(SupportStatementContextFactory.MakeContext()) is SizeView);
	    }

	    private void CheckNewData(long expectedSize)
	    {
	        EventBean[] newData = childView.LastNewData;
	        CheckData(newData, expectedSize);
	        childView.LastNewData = null;
	    }

	    private void CheckOldData(long expectedSize)
	    {
	        EventBean[] oldData = childView.LastOldData;
	        CheckData(oldData, expectedSize);
	        childView.LastOldData = null;
	    }

	    private void CheckData(EventBean[] data, long expectedSize)
	    {
	        // The view posts in its update data always just one object containing the size
	        Assert.AreEqual(1, data.Length);
            long actualSize = (long)data[0][ViewFieldEnum.SIZE_VIEW__SIZE.Name];
	        Assert.AreEqual((long) expectedSize, actualSize);
	    }

	    private void CheckIterator(long expectedSize)
	    {
	        Assert.IsTrue(myView.GetEnumerator().MoveNext());
	        EventBean _eventBean = CollectionHelper.First(myView);
            long actualSize = (long)_eventBean[ViewFieldEnum.SIZE_VIEW__SIZE.Name];
	        Assert.AreEqual((long) expectedSize, actualSize);
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
