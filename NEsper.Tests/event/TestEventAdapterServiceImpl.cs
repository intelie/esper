using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
	[TestFixture]
	public class TestEventAdapterServiceImpl
	{
		private static ConfigurationEventTypeXMLDOM XMLDOMConfig
		{
			get
			{
				ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
				config.RootElementName = "simpleEvent";
                config.AddXPathProperty("nested1", "/simpleEvent/nested1", XPathResultType.String);
				return config;
			}

		}
		private EventAdapterServiceImpl adapterService;

		[SetUp]
		public virtual void setUp()
		{
			adapterService = new EventAdapterServiceImpl( null );
		}

		[Test]
		public virtual void testCreateMapType()
		{
			EDictionary<String, Type> testTypesMap;
			testTypesMap = new EHashDictionary<String, Type>();
			testTypesMap.Put( "key1", typeof( String ) );
			EventType eventType = adapterService.CreateAnonymousMapType( testTypesMap );

			Assert.AreEqual( typeof( IDataDictionary ), eventType.UnderlyingType );
			Assert.AreEqual( 1, eventType.PropertyNames.Count );
			Assert.AreEqual( "key1", CollectionHelper.First( eventType.PropertyNames ) );
		}

		[Test]
		public virtual void testGetType()
		{
			adapterService.AddBeanType( "NAME", typeof( TestEventAdapterServiceImpl ).Name );

			EventType type = adapterService.GetEventType( "NAME" );
			Assert.AreEqual( typeof( TestEventAdapterServiceImpl ), type.UnderlyingType );

			EventType typeTwo = adapterService.GetEventType( typeof( TestEventAdapterServiceImpl ).Name );
			Assert.AreSame( typeTwo, typeTwo );

			Assert.IsNull( adapterService.GetEventType( "xx" ) );
		}

		[Test]
		public virtual void testAddInvalid()
		{
			try
			{
				adapterService.AddBeanType( "x", "xx" );
				Assert.Fail();
			}
			catch ( EventAdapterException ex )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testAddMapType()
		{
			EDictionary<String, Type> props = new EHashDictionary<String, Type>();
			props.Put( "a", typeof( Int64 ) );
			props.Put( "b", typeof( String ) );

			// check result type
			EventType typeOne = adapterService.AddMapType( "latencyEvent", props );
			Assert.AreEqual( typeof( Int64 ), typeOne.GetPropertyType( "a" ) );
			Assert.AreEqual( typeof( String ), typeOne.GetPropertyType( "b" ) );
			Assert.AreEqual( 2, typeOne.PropertyNames.Count );

			// add the same type with the same name, should succeed and return the same reference
			EventType typeTwo = adapterService.AddMapType( "latencyEvent", props );
			Assert.AreSame( typeOne, typeTwo );

			// add the same name with a different type, should fail
			props.Put( "b", typeof( bool ) );
			try
			{
				adapterService.AddMapType( "latencyEvent", props );
				Assert.Fail();
			}
			catch ( EventAdapterException ex )
			{
				// expected
			}
		}

		[Test]
		public virtual void testAddClassName()
		{
			EventType typeOne = adapterService.AddBeanType( "latencyEvent", typeof( SupportBean ).FullName );
			Assert.AreEqual( typeof( SupportBean ), typeOne.UnderlyingType );

			EventType typeTwo = adapterService.AddBeanType( "latencyEvent", typeof( SupportBean ).FullName );
			Assert.AreSame( typeOne, typeTwo );

			try
			{
				adapterService.AddBeanType( "latencyEvent", typeof( SupportBean_A ).FullName );
				Assert.Fail();
			}
			catch ( EventAdapterException ex )
			{
				// expected
			}
		}

		[Test]
		public virtual void testWrap()
		{
			SupportBean bean = new SupportBean();
			EventBean _event = adapterService.AdapterForBean( bean );
			Assert.AreSame( _event.Underlying, bean );
		}

		[Test]
		public virtual void testCreateAddToEventType()
		{
			EDictionary<String, Type> schema = new EHashDictionary<String, Type>();
			schema.Put( "STDDEV", typeof( Double ) );
			EventType parentEventType = adapterService.CreateAnonymousMapType( schema );

			EventType newEventType = adapterService.CreateAddToEventType(
				parentEventType,
				new String[] { "test" },
				new Type[] { typeof( Int32 ) }
				);

			Assert.IsTrue( newEventType.isProperty( "test" ) );
			Assert.AreEqual( typeof( Int32 ), newEventType.GetPropertyType( "test" ) );
		}

		[Test]
		public virtual void testAddXMLDOMType()
		{
			adapterService.AddXMLDOMType( "XMLDOMTypeOne", XMLDOMConfig );
			EventType eventType = adapterService.GetEventType( "XMLDOMTypeOne" );
			Assert.AreEqual( typeof( System.Xml.XmlNode ), eventType.UnderlyingType );

			try
			{
				adapterService.AddXMLDOMType( "a", new ConfigurationEventTypeXMLDOM() );
				Assert.Fail();
			}
			catch ( EventAdapterException ex )
			{
				// expected
			}
		}

		[Test]
		public virtual void testAdapterForDOM()
		{
			adapterService.AddXMLDOMType( "XMLDOMTypeOne", XMLDOMConfig );

			String xml =
				"<simpleEvent>\n" + 
				"  <nested1>value</nested1>\n" +
				"</simpleEvent>";

			XmlDocument simpleDoc = new XmlDocument();
			simpleDoc.LoadXml( xml );

			EventBean bean = adapterService.AdapterForDOM( simpleDoc );
			Assert.AreEqual( "value", bean[ "nested1" ] );
		}
	}
}
