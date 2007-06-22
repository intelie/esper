///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestGroupByView
	{
	    private GroupByView myGroupByView;
	    private SupportBeanClassView ultimateChildView;
	    private StatementContext statementContext;

	    [SetUp]
	    public void SetUp()
	    {
	        statementContext = SupportStatementContextFactory.MakeContext();
	        myGroupByView = new GroupByView(statementContext, new String[] {"symbol"});

	        SupportBeanClassView childView = new SupportBeanClassView(typeof(SupportMarketDataBean));

	        MergeView myMergeView = new MergeView(statementContext, new String[]{"symbol"}, SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean)));

	        ultimateChildView = new SupportBeanClassView(typeof(SupportMarketDataBean));

	        // This is the view hierarchy
	        myGroupByView.AddView(childView);
	        childView.AddView(myMergeView);
	        myMergeView.AddView(ultimateChildView);

	        SupportBeanClassView.Instances.Clear();
	    }

	    [Test]
	    public void TestViewPush()
	    {
	        // Reset instance lists of child views
	        SupportBeanClassView.Instances.Clear();
	        SupportMapView.Instances.Clear();

	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
	        stream.AddView(myGroupByView);

	        EventBean[] tradeBeans = new EventBean[10];

	        // Send an IBM symbol event
	        tradeBeans[0] = MakeTradeBean("IBM", 70);
	        stream.Insert(tradeBeans[0]);

	        // Expect 1 new beanclass view instance and check its data
	        Assert.AreEqual(1, SupportBeanClassView.Instances.Count);
	        SupportBeanClassView child_1 = SupportBeanClassView.Instances[0];
	        SupportViewDataChecker.CheckOldData(child_1, null);
	        SupportViewDataChecker.CheckNewData(child_1, new EventBean[] { tradeBeans[0] });

	        // Check the data of the ultimate receiver
	        SupportViewDataChecker.CheckOldData(ultimateChildView, null);
	        SupportViewDataChecker.CheckNewData(ultimateChildView, new EventBean[] {tradeBeans[0]});
	    }

	    [Test]
	    public void TestUpdate()
	    {
	        // Set up a feed for the view under test - it will have a depth of 3 trades
	        SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
	        stream.AddView(myGroupByView);

	        // Send old a new events
	        EventBean[] newEvents = new EventBean[] { MakeTradeBean("IBM", 70), MakeTradeBean("GE", 10) };
	        EventBean[] oldEvents = new EventBean[] { MakeTradeBean("IBM", 65), MakeTradeBean("GE", 9) };
	        myGroupByView.Update(newEvents, oldEvents);

	        Assert.AreEqual(2, SupportBeanClassView.Instances.Count);
	        SupportBeanClassView child_1 = SupportBeanClassView.Instances[0];
	        SupportBeanClassView child_2 = SupportBeanClassView.Instances[1];
	        SupportViewDataChecker.CheckOldData(child_1, new EventBean[] { oldEvents[0] });
	        SupportViewDataChecker.CheckNewData(child_1, new EventBean[] { newEvents[0] });
	        SupportViewDataChecker.CheckOldData(child_2, new EventBean[] { oldEvents[1] });
	        SupportViewDataChecker.CheckNewData(child_2, new EventBean[] { newEvents[1] });

	        newEvents = new EventBean[] { MakeTradeBean("IBM", 71), MakeTradeBean("GE", 11) };
	        oldEvents = new EventBean[] { MakeTradeBean("IBM", 70), MakeTradeBean("GE", 10) };
	        myGroupByView.Update(newEvents, oldEvents);

	        SupportViewDataChecker.CheckOldData(child_1, new EventBean[] { oldEvents[0] });
	        SupportViewDataChecker.CheckNewData(child_1, new EventBean[] { newEvents[0] });
	        SupportViewDataChecker.CheckOldData(child_2, new EventBean[] { oldEvents[1] });
	        SupportViewDataChecker.CheckNewData(child_2, new EventBean[] { newEvents[1] });
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        try
	        {
	            myGroupByView.GetEnumerator();
	            Assert.IsTrue(false);
	        }
	        catch (UnsupportedOperationException ex)
	        {
	            // Expected exception
	        }
	    }

	    [Test]
	    public void TestMakeSubviews()
	    {
	        EventStream eventStream = new SupportStreamImpl(typeof(SupportMarketDataBean), 4);
	        GroupByView groupView = new GroupByView(statementContext, new String[] {"symbol"});
	        eventStream.AddView(groupView);

	        Object[] groupByValue = new Object[] {"IBM"};

	        // Invalid for no child nodes
	        try
	        {
	            GroupByView.MakeSubViews(groupView, groupByValue, statementContext);
	            Assert.IsTrue(false);
	        }
	        catch (EPException ex)
	        {
	            // Expected exception
	        }

	        // Invalid for child node is a merge node - doesn't make sense to group and merge only
	        MergeView mergeViewOne = new MergeView(statementContext, new String[] {"symbol"}, null);
	        groupView.AddView(mergeViewOne);
	        try
	        {
	            GroupByView.MakeSubViews(groupView, groupByValue, statementContext);
	            Assert.IsTrue(false);
	        }
	        catch (EPException ex)
	        {
	            // Expected exception
	        }

	        // Add a size view parent of merge view
	        groupView = new GroupByView(statementContext, new String[] {"symbol"});

	        SizeView sizeView_1 = new SizeView(statementContext);

	        groupView.AddView(sizeView_1);
	        mergeViewOne = new MergeView(statementContext, new String[] {"symbol"}, null);
	        sizeView_1.AddView(mergeViewOne);

            IList<View> subViews = GroupByView.MakeSubViews(groupView, groupByValue, statementContext);

	        Assert.IsTrue(subViews.Count == 1);
	        Assert.IsTrue(subViews[0] is SizeView);
	        Assert.IsTrue(subViews[0] != sizeView_1);

	        SizeView sv = (SizeView) subViews[0];
	        Assert.AreEqual(1, sv.Views.Count);
	        Assert.IsTrue(sv.Views[0] is AddPropertyValueView);

	        AddPropertyValueView md = (AddPropertyValueView) sv.Views[0];
	        Assert.AreEqual(1, md.Views.Count);
	        Assert.IsTrue(md.Views[0] == mergeViewOne);
	    }

	    private EventBean MakeTradeBean(String symbol, int price)
	    {
	        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
