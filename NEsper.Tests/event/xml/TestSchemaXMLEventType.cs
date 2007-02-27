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
		private EventBean eventSchemaOne;
		
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
			
			eventSchemaOne = new XMLEventBean(noNSDoc, eventTypeNoNS);
		}
		
		[Test]
		public virtual void  testSimpleProperies()
		{
			Assert.AreEqual("SAMPLE_V6", eventSchemaOne["prop4"]);
		}
		
		[Test]
		public virtual void  testNestedProperties()
		{
			Assert.AreEqual(true, eventSchemaOne["nested1.prop2"]);
			Assert.AreEqual(typeof(bool), eventSchemaOne["nested1.prop2"].GetType());
		}
		
		[Test]
		public virtual void  testMappedProperties()
		{
			Assert.AreEqual("SAMPLE_V8", eventSchemaOne["nested3.nested4('a').prop5[1]"]);
			Assert.AreEqual("SAMPLE_V11", eventSchemaOne["nested3.nested4('c').prop5[1]"]);
		}
		
		[Test]
		public virtual void  testIndexedProperties()
		{
			Assert.AreEqual(5.0, eventSchemaOne["nested1.nested2.prop3[2]"]);
			Assert.AreEqual(typeof(Double), eventSchemaOne.EventType.GetPropertyType("nested1.nested2.prop3[2]"));
		}
		
		[Test]
		public virtual void  testCustomProperty()
		{
			Assert.AreEqual(typeof(Double), eventSchemaOne.EventType.GetPropertyType("customProp"));
			Assert.AreEqual((double) 3, eventSchemaOne["customProp"]);
		}
		
		[Test]
		public virtual void  testAttrProperty()
		{
			Assert.AreEqual(true, eventSchemaOne["prop4.attr2"]);
			Assert.AreEqual(typeof(bool), eventSchemaOne.EventType.GetPropertyType("prop4.attr2"));
			
			Assert.AreEqual("c", eventSchemaOne["nested3.nested4[2].id"]);
			Assert.AreEqual(typeof(String), eventSchemaOne.EventType.GetPropertyType("nested3.nested4[1].id"));
		}
		
		[Test]
		public virtual void  testInvalidCollectionAccess()
		{
			try
			{
				String prop = "nested3.nested4.id";
				eventSchemaOne.EventType.GetGetter(prop);
				Assert.Fail( "Invalid collection access: " + prop + " acepted" );
			}
			catch (System.Exception e)
			{
				//Expected
			}
			try
			{
				String prop = "nested3.nested4.nested5";
				eventSchemaOne.EventType.GetGetter(prop);
				Assert.Fail( "Invalid collection access: " + prop + " acepted" );
			}
			catch (System.Exception e)
			{
				//Expected
			}
		}
	}
}
