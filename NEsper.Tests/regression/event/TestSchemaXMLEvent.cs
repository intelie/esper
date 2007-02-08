using System;
using System.IO;
using System.Xml;
using System.Xml.Schema;
using System.Xml.XPath;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.events
{
	[TestFixture]
	public class TestSchemaXMLEvent 
	{
		private Configuration Config
		{
			get
			{
				Configuration configuration = new Configuration();
				ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
				eventTypeMeta.RootElementName = "simpleEvent";

                Uri uri = ResourceManager.ResolveResourceURL(CLASSLOADER_SCHEMA_URI);

                String schemaUri = uri.ToString();
				eventTypeMeta.SchemaResource = schemaUri;
				eventTypeMeta.AddNamespacePrefix("ss", "samples:schemas:simpleSchema");
                eventTypeMeta.AddXPathProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathResultType.Number);
				configuration.addEventTypeAlias("TestXMLSchemaType", eventTypeMeta);
				
				return configuration;
			}
		}

		private static String CLASSLOADER_SCHEMA_URI = "regression/simpleSchema.xsd";
		
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;
		
		private static String XML =
            "<simpleEvent xmlns=\"samples:schemas:simpleSchema\" xmlns:ss=\"samples:schemas:simpleSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"samples:schemas:simpleSchema\n" +
            "simpleSchema.xsd\">\n" +
            "\t<nested1 attr1=\"SAMPLE_ATTR1\">\n" +
            "\t\t<prop1>SAMPLE_V1</prop1>\n" + 
            "\t\t<prop2>true</prop2>\n" + 
            "\t\t<nested2>\n" + 
            "\t\t\t<prop3>3</prop3>\n" + 
            "\t\t\t<prop3>4</prop3>\n" + 
            "\t\t\t<prop3>5</prop3>\n" + 
            "\t\t</nested2>\n" +
            "\t</nested1>\n" +
            "\t<prop4 ss:attr2=\"true\">SAMPLE_V6</prop4>\n" + 
            "\t<nested3>\n" + "\t\t<nested4 id=\"a\">\n" +
            "\t\t\t<prop5>SAMPLE_V7</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V8</prop5>\n" + 
            "\t\t</nested4>\n" + 
            "\t\t<nested4 id=\"b\">\n" +
            "\t\t\t<prop5>SAMPLE_V9</prop5>\n" + 
            "\t\t</nested4>\n" +
            "\t\t<nested4 id=\"c\">\n" + 
            "\t\t\t<prop5>SAMPLE_V10</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V11</prop5>\n" +
            "\t\t</nested4>\n" + "\t</nested3>\n" + 
            "</simpleEvent>";
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetProvider("TestSchemaXML", Config);
			epService.Initialize();
			updateListener = new SupportUpdateListener();
			
			String stmt =
                "select nested1 as nodeProp," +
                "prop4 as nested1Prop," +
                "nested1.prop2 as nested2Prop," +
                "nested3.nested4('a').prop5[1] as complexProp," + 
                "nested1.nested2.prop3[2] as indexedProp," +
                "customProp," + "prop4.attr2 as attrOneProp," + 
                "nested3.nested4[2].id as attrTwoProp" + 
                " from TestXMLSchemaType.win:length(100)";
			
			EPStatement joinView = epService.EPAdministrator.createEQL(stmt);
			joinView.AddListener(updateListener);
		}
		
		[Test]
		public virtual void  testSimpleXML()
		{
			SendEvent("test");
			assertData();
		}
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
				epService.EPAdministrator.createEQL("select element1 from TestXMLSchemaType.win:length(100)");
				Assert.Fail();
			}
			catch (EPStatementException ex)
			{
				Assert.AreEqual("Error Starting view: Failed to locate property 'element1' in schema [select element1 from TestXMLSchemaType.win:length(100)]", ex.Message);
			}
		}
		
		private void  assertData()
		{
			Assert.IsNotNull(updateListener.LastNewData);
			EventBean _event = updateListener.LastNewData[0];
			
			Assert.IsTrue(_event["nodeProp"] is System.Xml.XmlNode);
			Assert.AreEqual("SAMPLE_V6", _event["nested1Prop"]);
			Assert.AreEqual(true, _event["nested2Prop"]);
			Assert.AreEqual("SAMPLE_V8", _event["complexProp"]);
			Assert.AreEqual(5.0, _event["indexedProp"]);
			Assert.AreEqual(3.0, _event["customProp"]);
			Assert.AreEqual(true, _event["attrOneProp"]);
			Assert.AreEqual("c", _event["attrTwoProp"]);
		}
		
		private void  SendEvent(String value)
		{
            String xml = TestSchemaXMLEvent.XML.Replace("VAL1", value);
			log.Debug(".sendEvent value=" + value);

            XmlDocument simpleDoc = new XmlDocument();
            simpleDoc.LoadXml(xml);
			
			epService.EPRuntime.SendEvent(simpleDoc);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TestSchemaXMLEvent));
	}
}
