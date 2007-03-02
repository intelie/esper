using System;
using System.Collections.Generic;

using net.esper.compat;
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
	public class TestAddPropertyValueView
	{
		private AddPropertyValueView myView;
		private SupportMapView parentView;
		private SupportSchemaNeutralView childView;
		private EventType parentEventType;

		[SetUp]
		public virtual void setUp()
		{
			// Set up length window view and a test child view
			myView = new AddPropertyValueView( new String[] { "symbol" }, new Object[] { "IBM" } );
			myView.ViewServiceContext = SupportViewContextFactory.makeContext();

			EDictionary<String, Type> schema = new EHashDictionary<String, Type>();
			schema.Put( "STDDEV", typeof( Double ) );
			parentEventType = SupportEventTypeFactory.createMapType( schema );

			parentView = new SupportMapView( schema );
			parentView.AddView( myView );

			childView = new SupportSchemaNeutralView();
			myView.AddView( childView );
		}

		[Test]
		public virtual void testViewUpdate()
		{
			EDataDictionary eventData = new EDataDictionary();

			// Generate some events
			eventData.Put( "STDDEV", 100 );
			EventBean eventBeanOne = SupportEventBeanFactory.CreateMapFromValues( eventData, parentEventType );
			eventData.Put( "STDDEV", 0 );
			EventBean eventBeanTwo = SupportEventBeanFactory.CreateMapFromValues( eventData, parentEventType );
			eventData.Put( "STDDEV", 99999 );
			EventBean eventBeanThree = SupportEventBeanFactory.CreateMapFromValues( eventData, parentEventType );

			// Send events
			parentView.Update( new EventBean[] { eventBeanOne, eventBeanTwo }, new EventBean[] { eventBeanThree } );

			// Checks
			EventBean[] newData = childView.LastNewData;
			Assert.AreEqual( 2, newData.Length );
			Assert.AreEqual( "IBM", newData[0][ "symbol" ] );
			Assert.AreEqual( 100, newData[0][ "STDDEV" ] );
			Assert.AreEqual( "IBM", newData[1][ "symbol" ] );
			Assert.AreEqual( 0, newData[1][ "STDDEV" ] );

			EventBean[] oldData = childView.LastOldData;
			Assert.AreEqual( 1, oldData.Length );
			Assert.AreEqual( "IBM", oldData[0][ "symbol" ] );
			Assert.AreEqual( 99999, oldData[0][ "STDDEV" ] );
		}

		[Test]
		public virtual void testViewAttachesTo()
		{
			// Should attach to anything event if the field does not exists
			AddPropertyValueView view = new AddPropertyValueView( new String[] { "dummy" }, new Object[] { "s" } );
			SupportBeanClassView parent = new SupportBeanClassView( typeof( SupportMarketDataBean ) );
			Assert.IsTrue( view.AttachesTo( parent ) == null );
		}

		[Test]
		public virtual void testCopyView()
		{
			AddPropertyValueView copied = (AddPropertyValueView) ViewSupport.ShallowCopyView( myView );
			Assert.AreEqual( myView.PropertyNames, copied.PropertyNames );
			Assert.AreEqual( myView.PropertyValues, copied.PropertyValues );
		}

		public virtual void TestAddProperty()
		{
			EDataDictionary eventData = new EDataDictionary();
			eventData.Put( "STDDEV", 100 );
			EventBean eventBean = SupportEventBeanFactory.CreateMapFromValues( eventData, parentEventType );

			EventType newEventType = SupportEventAdapterService.Service.CreateAddToEventType(
                parentEventType,
                new String[] { "test" },
                new Type[] { typeof( Int32 ) } );
			EventBean newBean = AddPropertyValueView.AddProperty(
                eventBean,
                new String[] { "test" }, 
                new Object[] { 2 },
                newEventType,
                SupportEventAdapterService.Service );

			Assert.AreEqual( 2, newBean[ "test" ] );
			Assert.AreEqual( 100, newBean[ "STDDEV" ] );
		}
	}
}
