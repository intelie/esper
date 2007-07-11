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
using net.esper.support.util;
using net.esper.support.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestUniqueByPropertyView
	{
	    private UniqueByPropertyView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new UniqueByPropertyView("symbol");
	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testViewPush()
	    {
	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
	        stream.AddView(myView);

	        EventBean[] tradeBeans = new EventBean[10];

	        // Send some events
	        tradeBeans[0] = MakeTradeBean("IBM", 70);
	        stream.Insert(tradeBeans[0]);
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { tradeBeans[0] });
	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { tradeBeans[0] });

	        // Send 2 more events
	        tradeBeans[1] = MakeTradeBean("IBM", 75);
	        tradeBeans[2] = MakeTradeBean("CSCO", 100);
	        stream.Insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { tradeBeans[1], tradeBeans[2] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { tradeBeans[0] });
	        SupportViewDataChecker.CheckNewData(childView,new EventBean[] { tradeBeans[1], tradeBeans[2] });

	        // And 1 more events
	        tradeBeans[3] = MakeTradeBean("CSCO", 99);
	        stream.Insert(new EventBean[] { tradeBeans[3] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { tradeBeans[1], tradeBeans[3] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { tradeBeans[2] });
	        SupportViewDataChecker.CheckNewData(childView,new EventBean[] { tradeBeans[3] });

	        // And 3 more events, that throws CSCO out as the stream size was 3
	        tradeBeans[4] = MakeTradeBean("MSFT", 55);
	        tradeBeans[5] = MakeTradeBean("IBM", 77);
	        tradeBeans[6] = MakeTradeBean("IBM", 78);
	        stream.Insert(new EventBean[] { tradeBeans[4], tradeBeans[5], tradeBeans[6] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { tradeBeans[6], tradeBeans[4] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { tradeBeans[1], tradeBeans[5], tradeBeans[3] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { tradeBeans[4], tradeBeans[5], tradeBeans[6] });  // Yes the event is both in old and new data

	        // Post as old data an event --> unique event is thrown away and posted as old data
	        myView.Update(null, new EventBean[] { tradeBeans[6] });
	        ArrayAssertionUtil.AreEqualExactOrder(myView.GetEnumerator(),new EventBean[] { tradeBeans[4] });
	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { tradeBeans[6] });
	        SupportViewDataChecker.CheckNewData(childView, null);
	    }

	    [Test]
	    public void testCopyView()
	    {
	        SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.Parent = parent;

	        UniqueByPropertyView copied = (UniqueByPropertyView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(myView.UniqueFieldName, copied.UniqueFieldName);
	    }

	    private EventBean MakeTradeBean(String symbol, int price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
