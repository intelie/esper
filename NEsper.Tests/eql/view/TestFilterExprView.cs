using System;

using net.esper.events;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.view
{
	
	[TestFixture]
	public class TestFilterExprView 
	{
		private FilterExprView filterExprViewAdapter;
		private SupportMapView childView;
		
		[SetUp]
		public virtual void  setUp()
		{
			filterExprViewAdapter = new FilterExprView(new SupportExprEvaluator());
			childView = new SupportMapView();
			filterExprViewAdapter.AddView(childView);
		}
		
		[Test]
		public virtual void  testUpdate()
		{
			// Test all evaluate to true (ie. all pass the filter)
			EventBean[] oldEvents = SupportEventBeanFactory.MakeEvents(new bool[]{true, true});
			EventBean[] newEvents = SupportEventBeanFactory.MakeEvents(new bool[]{true, true});
			filterExprViewAdapter.Update(newEvents, oldEvents);
			
			Assert.AreEqual(newEvents, childView.LastNewData);
			Assert.AreEqual(oldEvents, childView.LastOldData);
			childView.reset();
			
			// Test all evaluate to false (ie. none pass the filter)
			oldEvents = SupportEventBeanFactory.MakeEvents(new bool[]{false, false});
			newEvents = SupportEventBeanFactory.MakeEvents(new bool[]{false, false});
            filterExprViewAdapter.Update(newEvents, oldEvents);
			
			Assert.IsFalse(childView.getAndClearIsInvoked()); // Should not be invoked if no events
			Assert.IsNull(childView.LastNewData);
			Assert.IsNull(childView.LastOldData);
			
			// Test some pass through the filter
			oldEvents = SupportEventBeanFactory.MakeEvents(new bool[]{false, true, false});
			newEvents = SupportEventBeanFactory.MakeEvents(new bool[]{true, false, true});
            filterExprViewAdapter.Update(newEvents, oldEvents);
			
			Assert.AreEqual(2, childView.LastNewData.Length);
			Assert.AreSame(newEvents[0], childView.LastNewData[0]);
			Assert.AreSame(newEvents[2], childView.LastNewData[1]);
			Assert.AreEqual(1, childView.LastOldData.Length);
			Assert.AreSame(oldEvents[1], childView.LastOldData[0]);
		}
	}
}
