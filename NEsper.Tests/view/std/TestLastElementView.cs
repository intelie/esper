using System;
using System.Collections.Generic;

using net.esper.compat;
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
	public class TestLastElementView 
	{
		private LastElementView myView;
		private SupportBeanClassView childView;
		
		[SetUp]
		public virtual void  setUp()
		{
			// Set up length window view and a test child view
			myView = new LastElementView();
			childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.AddView(childView);
		}
		
		[Test]
		public virtual void  testViewPush()
		{
			// Set up a feed for the view under test - it will have a depth of 3 trades
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 3);
			stream.AddView(myView);

			EDictionary<String, EventBean> events = EventFactoryHelper.MakeEventMap(
				new String[] { "a0", "a1", "b0", "c0", "c1", "c2", "d0", "e0" } );
			
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, null);
			ArrayAssertionUtil.assertEqualsExactOrder(
                (IEnumerator<EventBean>) myView.GetEnumerator(),
                (EventBean[]) null);
			
			// View should keep the last element for iteration, should report new data as it arrives
            stream.Insert(new EventBean[] { events.Fetch("a0"), events.Fetch("a1") });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{events.Fetch("a0")});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{events.Fetch("a0"), events.Fetch("a1")});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{events.Fetch("a1")});

            stream.Insert(new EventBean[] { events.Fetch("b0") });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{events.Fetch("a1")});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{events.Fetch("b0")});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{events.Fetch("b0")});

            stream.Insert(new EventBean[] { events.Fetch("c0"), events.Fetch("c1"), events.Fetch("c2") });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{events.Fetch("b0"), events.Fetch("c0"), events.Fetch("c1")});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{events.Fetch("c0"), events.Fetch("c1"), events.Fetch("c2")});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{events.Fetch("c2")});

            stream.Insert(new EventBean[] { events.Fetch("d0") });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{events.Fetch("c2")});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{events.Fetch("d0")});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{events.Fetch("d0")});

            stream.Insert(new EventBean[] { events.Fetch("e0") });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{events.Fetch("d0")});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{events.Fetch("e0")});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{events.Fetch("e0")});
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			// Should attach to anything
			LastElementView view = new LastElementView();
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			Assert.IsTrue(view.AttachesTo(parent) == null);
			parent.AddView(view);
			Assert.IsTrue(view.EventType == parent.EventType);
		}
		
		[Test]
		public virtual void  testCopyView()
		{
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.Parent = parent;
			ViewSupport.ShallowCopyView(myView);
		}
	}
}
