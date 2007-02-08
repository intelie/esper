using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.std
{
	
	[TestFixture]
	public class TestUniqueByPropertyView 
	{
		private UniqueByPropertyView myView;
		private SupportBeanClassView childView;
		
		[SetUp]
		public virtual void  setUp()
		{
			// Set up length window view and a test child view
			myView = new UniqueByPropertyView("symbol");
			childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.AddView(childView);
		}
		
		[Test]
		public virtual void  testViewPush()
		{
			// Set up a feed for the view under test - it will have a depth of 3 trades
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
			stream.AddView(myView);
			
			EventBean[] tradeBeans = new EventBean[10];
			
			// Send some events
			tradeBeans[0] = makeTradeBean("IBM", 70);
            stream.Insert(tradeBeans[0]);
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{tradeBeans[0]});
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{tradeBeans[0]});
			
			// Send 2 more events
			tradeBeans[1] = makeTradeBean("IBM", 75);
			tradeBeans[2] = makeTradeBean("CSCO", 100);
            stream.Insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{tradeBeans[1], tradeBeans[2]});
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{tradeBeans[0]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{tradeBeans[1], tradeBeans[2]});
			
			// And 1 more events
			tradeBeans[3] = makeTradeBean("CSCO", 99);
            stream.Insert(new EventBean[] { tradeBeans[3] });
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{tradeBeans[1], tradeBeans[3]});
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{tradeBeans[2]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{tradeBeans[3]});
			
			// And 3 more events, that throws CSCO out as the stream size was 3
			tradeBeans[4] = makeTradeBean("MSFT", 55);
			tradeBeans[5] = makeTradeBean("IBM", 77);
			tradeBeans[6] = makeTradeBean("IBM", 78);
            stream.Insert(new EventBean[] { tradeBeans[4], tradeBeans[5], tradeBeans[6] });
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{tradeBeans[6], tradeBeans[4]});
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{tradeBeans[1], tradeBeans[5], tradeBeans[3]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{tradeBeans[4], tradeBeans[5], tradeBeans[6]}); // Yes the event is both in old and new data
			
			// Post as old data an event --> unique event is thrown away and posted as old data
			myView.Update(null, new EventBean[]{tradeBeans[6]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{tradeBeans[4]});
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{tradeBeans[6]});
			SupportViewDataChecker.checkNewData(childView, null);
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			// Should attach to anything as long as the field exists
			UniqueByPropertyView view = new UniqueByPropertyView("dummy");
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			Assert.IsTrue(view.AttachesTo(parent) != null);
			
			view = new UniqueByPropertyView("symbol");
			Assert.IsTrue(view.AttachesTo(parent) == null);
			
			parent.AddView(view);
			Assert.IsTrue(view.EventType == parent.EventType);
		}
		
		[Test]
		public virtual void  testCopyView()
		{
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.Parent = parent;
			
			UniqueByPropertyView copied = (UniqueByPropertyView) ViewSupport.shallowCopyView(myView);
			Assert.AreEqual(myView.UniqueFieldName, copied.UniqueFieldName);
		}
		
		private EventBean makeTradeBean(String symbol, int price)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
