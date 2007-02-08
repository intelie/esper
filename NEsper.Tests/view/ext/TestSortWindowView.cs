using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.ext
{
	
	[TestFixture]
	public class TestSortWindowView 
	{
		private SortWindowView myView;
		private SupportBeanClassView childView;
		private Object[] propertiesAndDirections;
		
		[SetUp]
		public virtual void  setUp()
		{
			// Set up length window view and a test child view
			propertiesAndDirections = new Object[]{"volume", false};
			myView = new SortWindowView(propertiesAndDirections, 5);
			childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.AddView(childView);
		}
		
		[Test]
		public virtual void  testConstructor()
		{
			propertiesAndDirections = new Object[]{"volume", false, "mother", true};
			myView = new SortWindowView(propertiesAndDirections, 5);
			
			Assert.AreEqual(2, myView.SortFieldNames.Length);
			Assert.AreEqual("volume", myView.SortFieldNames[0]);
			Assert.AreEqual("mother", myView.SortFieldNames[1]);
			
			Assert.AreEqual(2, myView.IsDescendingValues.Length);
			Assert.AreEqual(false, myView.IsDescendingValues[0]);
			Assert.AreEqual(true, myView.IsDescendingValues[1]);
			
			Assert.AreEqual(5, myView.SortWindowSize);
		}
		
		[Test]
		public virtual void  testIncorrectUse()
		{
			try
			{
				propertiesAndDirections = new Object[]{"volume", false};
				myView = new SortWindowView(propertiesAndDirections, - 1);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
			
			try
			{
				propertiesAndDirections = new Object[0];
				myView = new SortWindowView(propertiesAndDirections, 2);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
			
			try
			{
				propertiesAndDirections = new Object[]{"volume"};
				myView = new SortWindowView(propertiesAndDirections, 2);
				Assert.Fail();
			}
			catch (ArgumentException ex)
			{
				// Expected exception
			}
			
			try
			{
				propertiesAndDirections = new Object[]{"volume", "name"};
				myView = new SortWindowView(propertiesAndDirections, 2);
				Assert.Fail();
			}
			catch (System.InvalidCastException ex)
			{
				// Expected exception
			}
		}
		
		[Test]
		public virtual void  testViewOneProperty()
		{
			// Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 10);
			stream.AddView(myView);
			
			EventBean[] bean = new EventBean[12];
			
			bean[0] = makeBean(1000);
            stream.Insert(bean[0]);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[0]});
			
			bean[1] = makeBean(800);
			bean[2] = makeBean(1200);
            stream.Insert(new EventBean[] { bean[1], bean[2] });
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[1], bean[2]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[1], bean[0], bean[2]});
			
			bean[3] = makeBean(1200);
			bean[4] = makeBean(1000);
			bean[5] = makeBean(1400);
			bean[6] = makeBean(1100);
            stream.Insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[5], bean[2]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[3], bean[4], bean[5], bean[6]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[1], bean[4], bean[0], bean[6], bean[3]});
			
			bean[7] = makeBean(800);
			bean[8] = makeBean(700);
			bean[9] = makeBean(1200);
            stream.Insert(new EventBean[] { bean[7], bean[8], bean[9] });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[3], bean[9], bean[6]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[7], bean[8], bean[9]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[7], bean[1], bean[4], bean[0]});
			
			bean[10] = makeBean(1050);
            stream.Insert(new EventBean[] { bean[10] }); // Thus bean[0] will be old data !
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[0]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[10]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[7], bean[1], bean[4], bean[10]});
			
			bean[11] = makeBean(2000);
            stream.Insert(new EventBean[] { bean[11] }); // Thus bean[1] will be old data !
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[1]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[11]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[7], bean[4], bean[10], bean[11]});
		}
		
		[Test]
		public virtual void  testViewTwoProperties()
		{
			// Set up a sort windows that sorts on two properties
			propertiesAndDirections = new Object[]{"volume", false, "price", true};
			myView = new SortWindowView(propertiesAndDirections, 5);
			childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.AddView(childView);
			
			// Set up a feed for the view under test - the depth is 10 events so bean[10] will cause bean[0] to go old
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 10);
			stream.AddView(myView);
			
			EventBean[] bean = new EventBean[12];
			
			bean[0] = makeBean(20d, 1000);
            stream.Insert(bean[0]);
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[0]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[0]});
			
			bean[1] = makeBean(19d, 800);
			bean[2] = makeBean(18d, 1200);
            stream.Insert(new EventBean[] { bean[1], bean[2] });
			SupportViewDataChecker.checkOldData(childView, null);
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[1], bean[2]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[1], bean[0], bean[2]});
			
			bean[3] = makeBean(17d, 1200);
			bean[4] = makeBean(16d, 1000);
			bean[5] = makeBean(15d, 1400);
			bean[6] = makeBean(14d, 1100);
            stream.Insert(new EventBean[] { bean[3], bean[4], bean[5], bean[6] });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[5], bean[3]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[3], bean[4], bean[5], bean[6]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[1], bean[0], bean[4], bean[6], bean[2]});
			
			bean[7] = makeBean(13d, 800);
			bean[8] = makeBean(12d, 700);
			bean[9] = makeBean(11d, 1200);
            stream.Insert(new EventBean[] { bean[7], bean[8], bean[9] });
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[9], bean[2], bean[6]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[7], bean[8], bean[9]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[1], bean[7], bean[0], bean[4]});
			
			bean[10] = makeBean(10d, 1050);
            stream.Insert(new EventBean[] { bean[10] }); // Thus bean[0] will be old data !
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[0]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[10]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[1], bean[7], bean[4], bean[10]});
			
			bean[11] = makeBean(2000);
            stream.Insert(new EventBean[] { bean[11] }); // Thus bean[1] will be old data !
			SupportViewDataChecker.checkOldData(childView, new EventBean[]{bean[1]});
			SupportViewDataChecker.checkNewData(childView, new EventBean[]{bean[11]});
			ArrayAssertionUtil.assertEqualsExactOrder(myView.GetEnumerator(), new EventBean[]{bean[8], bean[7], bean[4], bean[10], bean[11]});
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			
			Object[] newPropertiesAndDirections = new Object[]{"dummy", true};
			SortWindowView view = new SortWindowView(newPropertiesAndDirections, 1);
			Assert.IsTrue(view.AttachesTo(parent) != null);
			
			newPropertiesAndDirections = new Object[]{"symbol", true};
			view = new SortWindowView(newPropertiesAndDirections, 100);
			Assert.IsTrue(view.AttachesTo(parent) == null);
			
			parent.AddView(view);
			Assert.IsTrue(view.EventType == parent.EventType);
		}
		
		[Test]
		public virtual void  testCopyView()
		{
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.Parent = parent;
			
			SortWindowView copied = (SortWindowView) ViewSupport.shallowCopyView(myView);
			
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) myView.SortFieldNames,
                (ICollection<string>) copied.SortFieldNames);
			Assert.AreEqual(myView.SortWindowSize, copied.SortWindowSize);
			ArrayAssertionUtil.assertEqualsExactOrder(myView.IsDescendingValues, copied.IsDescendingValues);
		}
		
		private EventBean makeBean(long volume)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", 0, volume, "");
			return SupportEventBeanFactory.createObject(bean);
		}
		
		private EventBean makeBean(double price, long volume)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean("CSCO.O", price, volume, "");
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
