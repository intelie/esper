using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.std
{
	
	[TestFixture]
	public class TestGroupByView 
	{
		private GroupByView myGroupByView;
		private SupportBeanClassView ultimateChildView;
		private ViewServiceContext viewServiceContext;
		
		[SetUp]
		public virtual void  setUp()
		{
			viewServiceContext = SupportViewContextFactory.makeContext();
			myGroupByView = new GroupByView(new String[]{"symbol"});
			myGroupByView.ViewServiceContext = viewServiceContext;
			
			SupportBeanClassView childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			
			MergeView myMergeView = new MergeView(new String[]{"symbol"});
			
			ultimateChildView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			
			// This is the view hierarchy
			myGroupByView.AddView(childView);
			childView.AddView(myMergeView);
			myMergeView.AddView(ultimateChildView);
			
			SupportBeanClassView.getInstances().Clear();
		}
		
		[Test]
		public virtual void  testViewPush()
		{
			// Reset instance lists of child views
			SupportBeanClassView.getInstances().Clear();
			SupportMapView.getInstances().Clear();
			
			// Set up a feed for the view under test - it will have a depth of 3 trades
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
			stream.AddView(myGroupByView);
			
			EventBean[] tradeBeans = new EventBean[10];
			
			// Send an IBM symbol event
			tradeBeans[0] = makeTradeBean("IBM", 70);
			stream.Insert(tradeBeans[0]);
			
			// Expect 1 new beanclass view instance and check its data
			Assert.AreEqual(1, SupportBeanClassView.getInstances().Count);
			SupportBeanClassView child_1 = SupportBeanClassView.getInstances()[0];
			SupportViewDataChecker.checkOldData(child_1, null);
			SupportViewDataChecker.checkNewData(child_1, new EventBean[]{tradeBeans[0]});
			
			// Check the data of the ultimate receiver
			SupportViewDataChecker.checkOldData(ultimateChildView, null);
			SupportViewDataChecker.checkNewData(ultimateChildView, new EventBean[]{tradeBeans[0]});
		}
		
		[Test]
		public virtual void  testUpdate()
		{
			// Set up a feed for the view under test - it will have a depth of 3 trades
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
			stream.AddView(myGroupByView);
			
			// Send old a new events
			EventBean[] newEvents = new EventBean[]{makeTradeBean("IBM", 70), makeTradeBean("GE", 10)};
			EventBean[] oldEvents = new EventBean[]{makeTradeBean("IBM", 65), makeTradeBean("GE", 9)};
            myGroupByView.Update(newEvents, oldEvents);
			
			Assert.AreEqual(2, SupportBeanClassView.getInstances().Count);
			SupportBeanClassView child_1 = SupportBeanClassView.getInstances()[0];
			SupportBeanClassView child_2 = SupportBeanClassView.getInstances()[1];
			SupportViewDataChecker.checkOldData(child_1, new EventBean[]{oldEvents[0]});
			SupportViewDataChecker.checkNewData(child_1, new EventBean[]{newEvents[0]});
			SupportViewDataChecker.checkOldData(child_2, new EventBean[]{oldEvents[1]});
			SupportViewDataChecker.checkNewData(child_2, new EventBean[]{newEvents[1]});
			
			newEvents = new EventBean[]{makeTradeBean("IBM", 71), makeTradeBean("GE", 11)};
			oldEvents = new EventBean[]{makeTradeBean("IBM", 70), makeTradeBean("GE", 10)};
            myGroupByView.Update(newEvents, oldEvents);
			
			SupportViewDataChecker.checkOldData(child_1, new EventBean[]{oldEvents[0]});
			SupportViewDataChecker.checkNewData(child_1, new EventBean[]{newEvents[0]});
			SupportViewDataChecker.checkOldData(child_2, new EventBean[]{oldEvents[1]});
			SupportViewDataChecker.checkNewData(child_2, new EventBean[]{newEvents[1]});
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			// Should attach to anything as long as the field exists
			GroupByView view = new GroupByView("dummy");
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			Assert.IsTrue(view.AttachesTo(parent) != null);
			
			view = new GroupByView("symbol");
			Assert.IsTrue(view.AttachesTo(parent) == null);
			
			parent.AddView(view);
			Assert.IsTrue(view.EventType == parent.EventType);
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
				myGroupByView.GetEnumerator();
				Assert.IsTrue(false);
			}
			catch (System.NotSupportedException ex)
			{
				// Expected exception
			}
		}
		
		[Test]
		public virtual void  testMakeSubviews()
		{
			EventStream eventStream = new SupportStreamImpl(typeof(SupportMarketDataBean), 4);
			GroupByView groupView = new GroupByView("symbol");
			eventStream.AddView(groupView);
			
			Object[] groupByValue = new Object[]{"IBM"};
			
			// Invalid for no child nodes
			try
			{
				GroupByView.MakeSubViews(groupView, groupByValue, viewServiceContext);
				Assert.IsTrue(false);
			}
			catch (EPException ex)
			{
				// Expected exception
			}
			
			// Invalid for child node is a merge node - doesn't make sense to group and merge only
			MergeView mergeViewOne = new MergeView(new String[]{"symbol"});
			groupView.AddView(mergeViewOne);
			try
			{
				GroupByView.MakeSubViews(groupView, groupByValue, viewServiceContext);
				Assert.IsTrue(false);
			}
			catch (EPException ex)
			{
				// Expected exception
			}
			
			// Add a size view parent of merge view
			groupView = new GroupByView("symbol");
			groupView.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			SizeView sizeView_1 = new SizeView();
			sizeView_1.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			groupView.AddView(sizeView_1);
			mergeViewOne = new MergeView(new String[]{"symbol"});
			sizeView_1.AddView(mergeViewOne);
			
      IList<View> subViews = GroupByView.MakeSubViews(groupView, groupByValue, viewServiceContext);
			
			Assert.IsTrue(subViews.Count == 1);
			Assert.IsTrue(subViews[0] is SizeView);
			Assert.IsTrue(subViews[0] != sizeView_1);
			
			SizeView sv = (SizeView) subViews[0];
			Assert.AreEqual(1, sv.GetViews().Count);
			Assert.IsTrue(sv.GetViews()[0] is AddPropertyValueView);
			
			AddPropertyValueView md = (AddPropertyValueView) sv.GetViews()[0];
			Assert.AreEqual(1, md.GetViews().Count);
			Assert.IsTrue(md.GetViews()[0] == mergeViewOne);
		}
		
		private EventBean makeTradeBean(String symbol, int price)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
