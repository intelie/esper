using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.window
{
	
	[TestFixture]
	public class TestExternallyTimedWindowView 
	{
		private ExternallyTimedWindowView myView;
		private SupportBeanClassView childView;
		
		[SetUp]
		public virtual void  setUp()
		{
			// Set up timed window view and a test child view, set the time window size to 1 second
			myView = new ExternallyTimedWindowView("timestamp", 1);
			childView = new SupportBeanClassView(typeof(SupportBeanTimestamp));
			myView.AddView(childView);
		}
		
		[Test]
		public virtual void  testIncorrectUse()
		{
			try
			{
				myView = new ExternallyTimedWindowView("goodie", 0);
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
		}
		
		[Test]
		public virtual void  testViewPush()
		{
			// Set up a feed for the view under test - it will have a depth of 3 trades
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBeanTimestamp), 3);
			stream.AddView(myView);
			
			EventBean[] a = makeBeans("a", 10000, 1);
            stream.Insert(a);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{a[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{a[0]});
			
			EventBean[] b = makeBeans("b", 10500, 2);
            stream.Insert(b);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{b[0], b[1]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{a[0], b[0], b[1]});
			
			EventBean[] c = makeBeans("c", 10900, 1);
            stream.Insert(c);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{c[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{a[0], b[0], b[1], c[0]});
			
			EventBean[] d = makeBeans("d", 11000, 1);
            stream.Insert(d);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{d[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{a[0], b[0], b[1], c[0], d[0]});
			
			EventBean[] e = makeBeans("e", 11001, 2);
            stream.Insert(e);
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{a[0]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{e[0], e[1]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{b[0], b[1], c[0], d[0], e[0], e[1]});
			
			EventBean[] f = makeBeans("f", 11501, 1);
            stream.Insert(f);
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{b[0], b[1]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{f[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{c[0], d[0], e[0], e[1], f[0]});
			
			EventBean[] g = makeBeans("g", 11900, 1);
            stream.Insert(g);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{g[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{c[0], d[0], e[0], e[1], f[0], g[0]});
			
			EventBean[] h = makeBeans("g", 12001, 3);
            stream.Insert(h);
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{c[0], d[0]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{h[0], h[1], h[2]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{e[0], e[1], f[0], g[0], h[0], h[1], h[2]});
			
			EventBean[] i = makeBeans("i", 13002, 1);
            stream.Insert(i);
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{e[0], e[1], f[0], g[0], h[0], h[1], h[2]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{i[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{i[0]});
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			// Should attach to anything as long as the field name is a long value
			ExternallyTimedWindowView view = new ExternallyTimedWindowView("dummy", 20);
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportBean));
			Assert.IsTrue(view.AttachesTo(parent) != null);
			
			view = new ExternallyTimedWindowView("intPrimitive", 20);
			Assert.IsTrue(view.AttachesTo(parent) != null);
			view = new ExternallyTimedWindowView("str", 20);
			Assert.IsTrue(view.AttachesTo(parent) != null);
			view = new ExternallyTimedWindowView("boolean", 20);
			Assert.IsTrue(view.AttachesTo(parent) != null);
			view = new ExternallyTimedWindowView("longPrimitive", 20);
			Assert.IsTrue(view.AttachesTo(parent) == null);
			
			parent.AddView(view);
			Assert.IsTrue(view.EventType == parent.EventType);
		}
		
		[Test]
		public virtual void  testCopyView()
		{
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBeanTimestamp), 3);
			stream.AddView(myView);
			
			ExternallyTimedWindowView copied = (ExternallyTimedWindowView) ViewSupport.shallowCopyView(myView);
			Assert.AreEqual(myView.TimestampFieldName, copied.TimestampFieldName);
			Assert.AreEqual(myView.MillisecondsBeforeExpiry, copied.MillisecondsBeforeExpiry);
		}
		
		private EventBean[] makeBeans(String id, long timestamp, int numBeans)
		{
			EventBean[] beans = new EventBean[numBeans];
			for (int i = 0; i < numBeans; i++)
			{
				SupportBeanTimestamp bean = new SupportBeanTimestamp(id + i, timestamp);
				beans[i] = SupportEventBeanFactory.createObject(bean);
			}
			return beans;
		}
	}
}
