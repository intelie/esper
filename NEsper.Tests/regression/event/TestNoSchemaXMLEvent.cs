using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.events
{
	
	[TestFixture]
	public class TestNoSchemaXMLEvent 
	{
		private EPServiceProvider epService;
		private SupportUpdateListener updateListener;
		
		private static String XML =
			"<myevent>\n" + 
			"  <element1>VAL1</element1>\n" +
			"  <element2>\n" + 
			"    <element21 id=\"e21_1\">VAL21-1</element21>\n" + 
			"    <element21 id=\"e21_2\">VAL21-2</element21>\n" + 
			"  </element2>\n" + 
			"  <element3 attrString=\"VAL3\" attrNum=\"5.6\" attrBool=\"true\"/>\n" + 
			"  <element4><element41>VAL4-1</element41></element4>\n" +
			"</myevent>";
		
		[SetUp]
		public virtual void  setUp()
		{
			Configuration configuration = new Configuration();
			ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
			xmlDOMEventTypeDesc.RootElementName = "myevent";
            xmlDOMEventTypeDesc.AddXPathProperty("xpathElement1", "/myevent/element1", XPathResultType.String);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathCountE21", "count(/myevent/element2/element21)", XPathResultType.Number);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrString", "/myevent/element3/@attrString", XPathResultType.String);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrNum", "/myevent/element3/@attrNum", XPathResultType.Number);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrBool", "/myevent/element3/@attrBool", XPathResultType.Boolean);
			configuration.addEventTypeAlias("TestXMLNoSchemaType", xmlDOMEventTypeDesc);
			
			epService = EPServiceProviderManager.GetProvider("TestNoSchemaXML", configuration);
			epService.Initialize();
			updateListener = new SupportUpdateListener();
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
			
			String stmt = "select element1," + "element4.element41 as nestedElement," + "element2.element21('e21_2') as mappedElement," + "element2.element21[2] as indexedElement," + "xpathElement1, xpathCountE21, xpathAttrString, xpathAttrNum, xpathAttrBool, " + "invalidelement," + "element3.myattribute as invalidattr " + "from TestXMLNoSchemaType.win:length(100)";
			
			EPStatement joinView = epService.EPAdministrator.createEQL(stmt);
			joinView.AddListener(updateListener);
		}
		
		[Test]
		public virtual void  testSimpleXML()
		{
			// Generate document with the specified in element1 to confirm we have independent events
			SendEvent("EventA");
			assertData("EventA");
			
			SendEvent("EventB");
			assertData("EventB");
		}
		
		private void  assertData(String element1)
		{
			Assert.IsNotNull(updateListener.LastNewData);
			EventBean _event = updateListener.LastNewData[0];
			
			Assert.AreEqual(element1, _event["element1"]);
			Assert.AreEqual("VAL4-1", _event["nestedElement"]);
			Assert.AreEqual("VAL21-2", _event["mappedElement"]);
			Assert.AreEqual("VAL21-2", _event["indexedElement"]);
			
			Assert.AreEqual(element1, _event["xpathElement1"]);
			Assert.AreEqual(2.0, _event["xpathCountE21"]);
			Assert.AreEqual("VAL3", _event["xpathAttrString"]);
			Assert.AreEqual(5.6, _event["xpathAttrNum"]);
			Assert.AreEqual(true, _event["xpathAttrBool"]);
			
			Assert.AreEqual("", _event["invalidelement"]); // properties not found come back as empty string without schema
			Assert.AreEqual("", _event["invalidattr"]); // attributes not supported when no schema supplied, use XPath
		}
		
		private void  SendEvent(String value)
		{
			String xml = XML.Replace("VAL1", value);
			log.Debug(".sendEvent value=" + value);

            XmlDocument simpleDoc = new XmlDocument();
            simpleDoc.LoadXml(xml);
			
			epService.EPRuntime.SendEvent(simpleDoc);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TestNoSchemaXMLEvent));
	}
}
