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

namespace net.esper.view.std
{
	[TestFixture]
	public class TestMergeView
	{
	    private MergeView myView;
	    private SupportBeanClassView childView;

	    [SetUp]
	    public void SetUp()
	    {
	        // Set up length window view and a test child view
	        myView = new MergeView(SupportStatementContextFactory.MakeContext(),
	                new String[] {"symbol"},
	                SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)));

	        childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.AddView(childView);
	    }

	    [Test]
	    public void testViewPush()
	    {
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 2);
	        stream.AddView(myView);

	        EventBean[] tradeBeans = new EventBean[10];

	        // Send events, expect just forwarded
	        tradeBeans[0] = MakeTradeBean("IBM", 70);
	        stream.Insert(tradeBeans[0]);

	        SupportViewDataChecker.CheckOldData(childView, null);
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { tradeBeans[0] });

	        // Send some more events, expect forwarded
	        tradeBeans[1] = MakeTradeBean("GE", 90);
	        tradeBeans[2] = MakeTradeBean("CSCO", 20);
	        stream.Insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });

	        SupportViewDataChecker.CheckOldData(childView, new EventBean[] { tradeBeans[0] });
	        SupportViewDataChecker.CheckNewData(childView, new EventBean[] { tradeBeans[1], tradeBeans[2] });
	    }

	    [Test]
	    public void testCopyView()
	    {
	        SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
	        myView.Parent = parent;

	        MergeView copied = (MergeView) myView.CloneView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(myView.GroupFieldNames, copied.GroupFieldNames);
	        Assert.AreEqual(myView.EventType, SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)));
	    }

	    private EventBean MakeTradeBean(String symbol, int price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
