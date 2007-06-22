using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;
using System.Net;

using net.esper.client;
using net.esper.compat;
using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events.xml
{
	[TestFixture]
	public class TestSchemaXMLEventType
	{
		private EventBean _eventSchemaOne;
		
		[SetUp]
		public virtual void  setUp()
		{
            Uri schemaUrl = ResourceManager.ResolveResourceURL("regression/simpleSchema.xsd" ) ;
			ConfigurationEventTypeXMLDOM configNoNS = new ConfigurationEventTypeXMLDOM();
			configNoNS.SchemaResource = schemaUrl.ToString();
			configNoNS.RootElementName = "simpleEvent";
			configNoNS.AddXPathProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathResultType.Number);
			configNoNS.AddNamespacePrefix("ss", "samples:schemas:simpleSchema");
			SchemaXMLEventType eventTypeNoNS = new SchemaXMLEventType(configNoNS);

            XmlDocument noNSDoc = new XmlDocument();
            using( Stream stream = ResourceManager.GetResourceAsStream("regression/simpleWithSchema.xml") )
            {
                noNSDoc.Load( stream ) ;
            }
			
			_eventSchemaOne = new XMLEventBean(noNSDoc, eventTypeNoNS);
		}
		
		[Test]
		public virtual void  testSimpleProperies()
		{
			Assert.AreEqual("SAMPLE_V6", _eventSchemaOne["prop4"]);
		}
		
		[Test]
		public virtual void  testNestedProperties()
		{
			Assert.AreEqual(true, _eventSchemaOne["nested1.prop2"]);
			Assert.AreEqual(typeof(bool), _eventSchemaOne["nested1.prop2"].GetType());
		}
		
		[Test]
		public virtual void  testMappedProperties()
		{
			Assert.AreEqual("SAMPLE_V8", _eventSchemaOne["nested3.nested4('a').prop5[1]"]);
			Assert.AreEqual("SAMPLE_V11", _eventSchemaOne["nested3.nested4('c').prop5[1]"]);
		}
		
		[Test]
		public virtual void  testIndexedProperties()
		{
			Assert.AreEqual(5.0, _eventSchemaOne["nested1.nested2.prop3[2]"]);
			Assert.AreEqual(typeof(double?), _eventSchemaOne.EventType.GetPropertyType("nested1.nested2.prop3[2]"));
		}
		
		[Test]
		public virtual void  testCustomProperty()
		{
			Assert.AreEqual(typeof(double?), _eventSchemaOne.EventType.GetPropertyType("customProp"));
			Assert.AreEqual((double) 3, _eventSchemaOne["customProp"]);
		}
		
		[Test]
		public virtual void  testAttrProperty()
		{
			Assert.AreEqual(true, _eventSchemaOne["prop4.attr2"]);
			Assert.AreEqual(typeof(bool), _eventSchemaOne.EventType.GetPropertyType("prop4.attr2"));
			
			Assert.AreEqual("c", _eventSchemaOne["nested3.nested4[2].id"]);
			Assert.AreEqual(typeof(String), _eventSchemaOne.EventType.GetPropertyType("nested3.nested4[1].id"));
		}
		
		[Test]
		public virtual void  testInvalidCollectionAccess()
		{
			try
			{
				String prop = "nested3.nested4.id";
				_eventSchemaOne.EventType.GetGetter(prop);
				Assert.Fail( "Invalid collection access: " + prop + " acepted" );
			}
			catch (System.Exception e)
			{
				//Expected
			}
			try
			{
				String prop = "nested3.nested4.nested5";
				_eventSchemaOne.EventType.GetGetter(prop);
				Assert.Fail( "Invalid collection access: " + prop + " acepted" );
			}
			catch (System.Exception e)
			{
				//Expected
			}
		}
	}
}
