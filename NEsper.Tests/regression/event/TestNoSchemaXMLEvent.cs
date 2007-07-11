///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.util;

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
	    public void SetUp()
	    {
	        Configuration configuration = new Configuration();
	        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
	        xmlDOMEventTypeDesc.RootElementName = "myevent";
	        xmlDOMEventTypeDesc.AddXPathProperty("xpathElement1", "/myevent/element1", XPathResultType.String);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathCountE21", "count(/myevent/element2/element21)", XPathResultType.Number);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrString", "/myevent/element3/@attrString", XPathResultType.String);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrNum", "/myevent/element3/@attrNum", XPathResultType.Number);
            xmlDOMEventTypeDesc.AddXPathProperty("xpathAttrBool", "/myevent/element3/@attrBool", XPathResultType.Boolean);
	        configuration.AddEventTypeAlias("TestXMLNoSchemaType", xmlDOMEventTypeDesc);

	        epService = EPServiceProviderManager.GetProvider("TestNoSchemaXML", configuration);
	        epService.Initialize();
	        updateListener = new SupportUpdateListener();
	        epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

	        String stmt =
	                "select element1," +
	                       "element4.element41 as nestedElement," +
	                       "element2.Element21('e21_2') as mappedElement," +
	                       "element2.element21[2] as indexedElement," +
	                       "xpathElement1, xpathCountE21, xpathAttrString, xpathAttrNum, xpathAttrBool, " +
	                       "invalidelement," +
	                       "element3.myattribute as invalidattr " +
	                      "from TestXMLNoSchemaType.win:length(100)";

	        EPStatement joinView = epService.EPAdministrator.CreateEQL(stmt);
            joinView.AddListener(updateListener);
	    }

	    [Test]
	    public void testSimpleXML()
	    {
	        // Generate document with the specified in element1 to confirm we have independent events
	        SendEvent("EventA");
	        AssertData("EventA");

	        SendEvent("EventB");
	        AssertData("EventB");
	    }

	    [Test]
	    public void testNestedXML()
	    {
	        Configuration configuration = new Configuration();
	        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
	        xmlDOMEventTypeDesc.RootElementName = "a";
            xmlDOMEventTypeDesc.AddXPathProperty("element1", "/a/b/c", XPathResultType.String);
	        configuration.AddEventTypeAlias("AEvent", xmlDOMEventTypeDesc);

	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();
	        updateListener = new SupportUpdateListener();

	        String stmt = "select b.c as type, element1, result1 from AEvent";
	        EPStatement joinView = epService.EPAdministrator.CreateEQL(stmt);
            joinView.AddListener(updateListener);

	        SendXMLEvent("<a><b><c></c></b></a>");
	        EventBean _event = updateListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("", _event["type"]);
	        Assert.AreEqual("", _event["element1"]);

	        SendXMLEvent("<a><b></b></a>");
	        _event = updateListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("", _event["type"]);
	        Assert.AreEqual("", _event["element1"]);

	        SendXMLEvent("<a><b><c>text</c></b></a>");
	        _event = updateListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("text", _event["type"]);
	        Assert.AreEqual("text", _event["element1"]);
	    }

	    [Test]
	    public void testEventXML()
	    {
	        Configuration configuration = new Configuration();
	        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
            desc.AddXPathProperty("event.type", "/event/@type", XPathResultType.String);
            desc.AddXPathProperty("event.uid", "/event/@uid", XPathResultType.String);
	        desc.RootElementName = "event";
	        configuration.AddEventTypeAlias("MyEvent", desc);

	        epService = EPServiceProviderManager.GetDefaultProvider(configuration);
	        epService.Initialize();
	        updateListener = new SupportUpdateListener();

	        String stmt = "select event.type as type, event.uid as uid from MyEvent";
	        EPStatement joinView = epService.EPAdministrator.CreateEQL(stmt);
            joinView.AddListener(updateListener);

	        SendXMLEvent("<event type=\"a-f-G\" uid=\"terminal.55\" time=\"2007-04-19T13:05:20.22Z\" version=\"2.0\"></event>");
	        EventBean _event = updateListener.AssertOneGetNewAndReset();
	        Assert.AreEqual("a-f-G", _event["type"]);
	        Assert.AreEqual("terminal.55", _event["uid"]);
	    }

	    private void AssertData(String element1)
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

	        Assert.AreEqual("", _event["invalidelement"]);        // properties not found come back as empty string without schema
	        Assert.AreEqual("", _event["invalidattr"]);     // attributes not supported when no schema supplied, use XPath
	    }

	    private void SendEvent(String value)
	    {
	        String xml = XML.Replace("VAL1", value);
	        log.Debug(".sendEvent value=" + value);

            XmlDocument simpleDoc = new XmlDocument();
            simpleDoc.LoadXml(xml);

	        epService.EPRuntime.SendEvent(simpleDoc);
	    }

	    private void SendXMLEvent(String xml)
	    {
            XmlDocument simpleDoc = new XmlDocument();
            simpleDoc.LoadXml(xml);
	        epService.EPRuntime.SendEvent(simpleDoc);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}

} // End of namespace
