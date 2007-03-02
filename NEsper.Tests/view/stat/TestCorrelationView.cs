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

namespace net.esper.view.stat
{
	
	[TestFixture]
	public class TestCorrelationView 
	{
		internal CorrelationView myView;
		internal SupportBeanClassView childView;
		
		[SetUp]
		public virtual void  setUp()
		{
			// Set up sum view and a test child view
			myView = new CorrelationView("price", "volume");
			myView.ViewServiceContext = SupportViewContextFactory.makeContext();
			
			childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
			myView.AddView(childView);
		}
		
		// Check values against Microsoft Excel computed values
		[Test]
		public virtual void  testViewComputedValues()
		{
			// Set up feed for sum view
			SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
			stream.AddView(myView);
			
			// Send a first event, checkNew values
			EventBean marketData = makeBean("IBM", 70, 1000);
            stream.Insert(marketData);
			checkOld(Double.NaN);
			checkNew(Double.NaN);
			
			// Send a second event, checkNew values
			marketData = makeBean("IBM", 70.5, 1500);
            stream.Insert(marketData);
			checkOld(Double.NaN);
			checkNew(1);
			
			// Send a third event, checkNew values
			marketData = makeBean("IBM", 70.1, 1200);
            stream.Insert(marketData);
			checkOld(1);
			checkNew(0.97622104);
			
			// Send a 4th event, this time the first event should be gone, checkNew values
			marketData = makeBean("IBM", 70.25, 1000);
            stream.Insert(marketData);
			checkOld(0.97622104);
			checkNew(0.70463404);
		}
		
		[Test]
		public virtual void  testViewAttachesTo()
		{
			CorrelationView view = new CorrelationView("symbol", "price");
			
			// The symbol field in the parent is not a number, expect an error on attach
			SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
			Assert.IsTrue(view.AttachesTo(parent) != null);
			
			// Try a non-existing field name
			view = new CorrelationView("price", "dummy");
			Assert.IsTrue(view.AttachesTo(parent) != null);
		}
		
		[Test]
		public virtual void  testGetSchema()
		{
			Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.CORRELATION__CORRELATION.Name) == typeof(double));
		}
		
		[Test]
		public virtual void  testCopyView()
		{
			CorrelationView copied = (CorrelationView) ViewSupport.ShallowCopyView(myView);
			Assert.IsTrue(myView.FieldNameX.Equals(copied.FieldNameX));
			Assert.IsTrue(myView.FieldNameY.Equals(copied.FieldNameY));
		}
		
		private void  checkNew(double correlationE)
		{
			IEnumerator<EventBean> iterator = myView.GetEnumerator();
			Assert.IsTrue(iterator.MoveNext());
			checkValues(iterator.Current, correlationE);
			Assert.IsTrue(iterator.MoveNext() == false);
			
			Assert.IsTrue(childView.LastNewData.Length == 1);
			EventBean childViewValues = childView.LastNewData[0];
			checkValues(childViewValues, correlationE);
		}
		
		private void  checkOld(double correlationE)
		{
			Assert.IsTrue(childView.LastOldData.Length == 1);
			EventBean childViewValues = childView.LastOldData[0];
			checkValues(childViewValues, correlationE);
		}
		
		private void  checkValues(EventBean values, double correlationE)
		{
			double correlation = getDoubleValue(ViewFieldEnum.CORRELATION__CORRELATION, values);
			Assert.IsTrue(DoubleValueAssertionUtil.Equals(correlation, correlationE, 6));
		}
		
		private double getDoubleValue(ViewFieldEnum field, EventBean values)
		{
			return (Double) values[field.Name];
		}
		
		private EventBean makeBean(String symbol, double price, long volume)
		{
			SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
