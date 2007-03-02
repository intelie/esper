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
	public class TestRegressionLinestView
	{
		internal RegressionLinestView myView;
		internal SupportBeanClassView childView;

		[SetUp]
		public virtual void setUp()
		{
			// Set up sum view and a test child view
			myView = new RegressionLinestView( "price", "volume" );
			myView.ViewServiceContext = SupportViewContextFactory.makeContext();

			childView = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			myView.AddView( childView );
		}

		// Check values against Microsoft Excel computed values
		[Test]
		public virtual void testViewComputedValues()
		{
			// Set up feed for sum view
			SupportStreamImpl stream = new SupportStreamImpl( typeof( SupportMarketDataBean ), 3 );
			stream.AddView( myView );

			// Send a first event, checkNew values
			EventBean marketData = makeBean( "IBM", 70, 1000 );
            stream.Insert(marketData);
			checkOld( Double.NaN, Double.NaN );
			checkNew( Double.NaN, Double.NaN );

			// Send a second event, checkNew values
			marketData = makeBean( "IBM", 70.5, 1500 );
            stream.Insert(marketData);
			checkOld( Double.NaN, Double.NaN );
			checkNew( 1000, -69000 );

			// Send a third event, checkNew values
			marketData = makeBean( "IBM", 70.1, 1200 );
            stream.Insert(marketData);
			checkOld( 1000, -69000 );
			checkNew( 928.5714286, -63952.380953 );

			// Send a 4th event, this time the first event should be gone, checkNew values
			marketData = makeBean( "IBM", 70.25, 1000 );
            stream.Insert(marketData);
			checkOld( 928.5714286, -63952.380953 );
			checkNew( 877.5510204, -60443.877555 );
		}

		[Test]
		public virtual void testViewAttachesTo()
		{
			RegressionLinestView view = new RegressionLinestView( "symbol", "price" );

			// The symbol field in the parent is not a number, expect an error on attach
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			Assert.IsTrue( view.AttachesTo( parent ) != null );

			// Try a non-existing field name
			view = new RegressionLinestView( "price", "dummy" );
			Assert.IsTrue( view.AttachesTo( parent ) != null );
		}

		[Test]
		public virtual void testGetSchema()
		{
			Assert.AreEqual( myView.EventType.GetPropertyType( ViewFieldEnum.REGRESSION__SLOPE.Name ), typeof( double ) );
			Assert.AreEqual( myView.EventType.GetPropertyType( ViewFieldEnum.REGRESSION__YINTERCEPT.Name ), typeof( double ) );
		}

		[Test]
		public virtual void testCopyView()
		{
			RegressionLinestView copied = (RegressionLinestView) ViewSupport.ShallowCopyView( myView );
			Assert.IsTrue( myView.FieldNameX.Equals( copied.FieldNameX ) );
			Assert.IsTrue( myView.FieldNameY.Equals( copied.FieldNameY ) );
		}

		private void checkNew( double slopeE, double yinterceptE )
		{
			IEnumerator<EventBean> iterator = myView.GetEnumerator();
			Assert.IsTrue( iterator.MoveNext() );
			checkValues( iterator.Current, slopeE, yinterceptE );
			Assert.IsFalse( iterator.MoveNext() );

			Assert.IsTrue( childView.LastNewData.Length == 1 );
			EventBean childViewValues = childView.LastNewData[0];
			checkValues( childViewValues, slopeE, yinterceptE );
		}

		private void checkOld( double slopeE, double yinterceptE )
		{
			Assert.IsTrue( childView.LastOldData.Length == 1 );
			EventBean childViewValues = childView.LastOldData[0];
			checkValues( childViewValues, slopeE, yinterceptE );
		}

		private void checkValues( EventBean eventBean, double slopeE, double yinterceptE )
		{
			double slope = getDoubleValue( ViewFieldEnum.REGRESSION__SLOPE, eventBean );
			double yintercept = getDoubleValue( ViewFieldEnum.REGRESSION__YINTERCEPT, eventBean );
			Assert.IsTrue( DoubleValueAssertionUtil.Equals( slope, slopeE, 6 ) );
			Assert.IsTrue( DoubleValueAssertionUtil.Equals( yintercept, yinterceptE, 6 ) );
		}

		private double getDoubleValue( ViewFieldEnum field, EventBean _event )
		{
			return (Double) _event[ field.Name ];
		}

		private EventBean makeBean( String symbol, double price, long volume )
		{
			SupportMarketDataBean bean = new SupportMarketDataBean( symbol, price, volume, "" );
			return SupportEventBeanFactory.createObject( bean );
		}
	}
}
