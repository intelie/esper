/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.event.EventBean;
import com.espertech.esperio.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

import javax.xml.xpath.XPathConstants;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class TestAxiom extends TestCase
{
    private static final String AXIOM_URI = "types://xml/apacheaxiom/OMNode";

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

    public void testSimpleXML() throws Exception
    {
        Configuration configuration = getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        ConfigurationEventTypeAxiom axiomType = new ConfigurationEventTypeAxiom();
        axiomType.setRootElementName("myevent");
        axiomType.addXPathProperty("xpathElement1", "/myevent/element1", XPathConstants.STRING);
        axiomType.addXPathProperty("xpathCountE21", "count(/myevent/element2/element21)", XPathConstants.NUMBER);
        axiomType.addXPathProperty("xpathAttrString", "/myevent/element3/@attrString", XPathConstants.STRING);
        axiomType.addXPathProperty("xpathAttrNum", "/myevent/element3/@attrNum", XPathConstants.NUMBER);
        axiomType.addXPathProperty("xpathAttrBool", "/myevent/element3/@attrBool", XPathConstants.BOOLEAN);
        configuration.addPlugInEventType("TestXMLNoSchemaType", new URI[] {new URI(AXIOM_URI)}, axiomType);

        EPServiceProvider epService = EPServiceProviderManager.getProvider("TestNoSchemaXML", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String stmt =
                "select element1," +
                       "element4.element41 as nestedElement," +
                       "element2.element21('e21_2') as mappedElement," +
                       "element2.element21[2] as indexedElement," +
                       "xpathElement1, xpathCountE21, xpathAttrString, xpathAttrNum, xpathAttrBool, " +
                       "invalidelement," +
                       "element3.myattribute as invalidattr " +
                      "from TestXMLNoSchemaType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        // Generate document with the specified in element1 to confirm we have independent events
        sendEvent(epService, "TestXMLNoSchemaType", "EventA");
        assertData("EventA");

        sendEvent(epService, "TestXMLNoSchemaType", "EventB");
        assertData("EventB");
    }

    public void testConfigurationXML() throws Exception
    {
        String sampleXML = "esper-axiom-sample-configuration.xml";
        URL url = this.getClass().getClassLoader().getResource(sampleXML);
        if (url == null)
        {
            throw new RuntimeException("Cannot find XML configuration: " + sampleXML);
        }

        Configuration config = new Configuration();
        config.configure(url);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select temp, sensorId from SensorEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent(epService, "SensorEvent", "<measurement><temperature>98.6</temperature><sensorid>8374744</sensorid></measurement>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals(98.6, event.get("temp"));
        assertEquals(8374744L, event.get("sensorId"));
    }

    public void testNestedXML() throws Exception
    {
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom axiomType = new ConfigurationEventTypeAxiom();
        axiomType.setRootElementName("a");
        axiomType.addXPathProperty("element1", "/a/b/c", XPathConstants.STRING);
        configuration.addPlugInEventType("AEvent", new URI[] {new URI(AXIOM_URI)}, axiomType);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select b.c as type, element1, result1 from AEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent(epService, "AEvent", "<a><b><c></c></b></a>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("", event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent(epService, "AEvent", "<a><b></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("", event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent(epService, "AEvent", "<a><b><c>text</c></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("text", event.get("type"));
        assertEquals("text", event.get("element1"));

        // Use a URI sender list
        String xml = "<a><b><c>hype</c></b></a>";
        EventSender sender = epService.getEPRuntime().getEventSender(new URI[] {new URI(AXIOM_URI)});
        InputStream s = new ByteArrayInputStream(xml.getBytes());
        OMElement documentElement = new StAXOMBuilder(s).getDocumentElement();
        sender.sendEvent(documentElement);
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("hype", event.get("type"));
        assertEquals("hype", event.get("element1"));
    }

    public void testDotEscapeSyntax() throws Exception
    {
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom axiomType = new ConfigurationEventTypeAxiom();
        axiomType.setRootElementName("myroot");
        configuration.addPlugInEventType("AEvent", new URI[] {new URI(AXIOM_URI)}, axiomType);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select a\\.b.c\\.d as val from AEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent(epService, "AEvent", "<myroot><a.b><c.d>value</c.d></a.b></myroot>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("value", event.get("val"));
    }

    public void testEventXML() throws Exception
    {
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom desc = new ConfigurationEventTypeAxiom();
        desc.addXPathProperty("event.type", "/event/@type", XPathConstants.STRING);
        desc.addXPathProperty("event.uid", "/event/@uid", XPathConstants.STRING);
        desc.setRootElementName("event");
        configuration.addPlugInEventType("MyEvent", new URI[] {new URI(AXIOM_URI)}, desc);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select event.type as type, event.uid as uid from MyEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent(epService, "MyEvent", "<event type=\"a-f-G\" uid=\"terminal.55\" time=\"2007-04-19T13:05:20.22Z\" version=\"2.0\"></event>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("a-f-G", event.get("type"));
        assertEquals("terminal.55", event.get("uid"));
    }

    public void testElementNode() throws Exception
    {
        // test for Esper-129
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom desc = new ConfigurationEventTypeAxiom();
        desc.addXPathProperty("event.type", "//event/@type", XPathConstants.STRING);
        desc.addXPathProperty("event.uid", "//event/@uid", XPathConstants.STRING);
        desc.setRootElementName("batch-event");
        configuration.addPlugInEventType("MyEvent", new URI[] {new URI(AXIOM_URI)}, desc);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select event.type as type, event.uid as uid from MyEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<batch-event>" +
                     "<event type=\"a-f-G\" uid=\"terminal.55\" time=\"2007-04-19T13:05:20.22Z\" version=\"2.0\"/>" +
                        "</batch-event>";

        sendXMLEvent(epService, "MyEvent", xml);

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("a-f-G", event.get("type"));
        assertEquals("terminal.55", event.get("uid"));
    }

    public void testNamespaceXPathRelative() throws Exception
    {
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom desc = new ConfigurationEventTypeAxiom();
        desc.setRootElementName("getQuote");
        desc.setDefaultNamespace("http://services.samples/xsd");
        desc.setRootElementNamespace("http://services.samples/xsd");
        desc.addNamespacePrefix("m0", "http://services.samples/xsd");
        desc.setResolvePropertiesAbsolute(false);
        configuration.addPlugInEventType("StockQuote", new URI[] {new URI(AXIOM_URI)}, desc);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select request.symbol as symbol_a, symbol as symbol_b from StockQuote";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<m0:getQuote xmlns:m0=\"http://services.samples/xsd\"><m0:request><m0:symbol>IBM</m0:symbol></m0:request></m0:getQuote>";
        sendXMLEvent(epService, "StockQuote", xml);

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("IBM", event.get("symbol_a"));
        assertEquals("IBM", event.get("symbol_b"));
    }

    public void testNamespaceXPathAbsolute() throws Exception
    {
        Configuration configuration = getConfiguration();
        ConfigurationEventTypeAxiom desc = new ConfigurationEventTypeAxiom();
        desc.addXPathProperty("symbol_a", "//m0:symbol", XPathConstants.STRING);
        desc.addXPathProperty("symbol_c", "/m0:getQuote/m0:request/m0:symbol", XPathConstants.STRING);
        desc.setRootElementName("getQuote");
        desc.setDefaultNamespace("http://services.samples/xsd");
        desc.setRootElementNamespace("http://services.samples/xsd");
        desc.addNamespacePrefix("m0", "http://services.samples/xsd");
        desc.setResolvePropertiesAbsolute(true);
        configuration.addPlugInEventType("StockQuote", new URI[] {new URI(AXIOM_URI)}, desc);

        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select symbol_a, symbol_b, symbol_c, request.symbol as symbol_d, symbol as symbol_e from StockQuote";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<m0:getQuote xmlns:m0=\"http://services.samples/xsd\"><m0:request><m0:symbol>IBM</m0:symbol></m0:request></m0:getQuote>";
        sendXMLEvent(epService, "StockQuote", xml);

        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("IBM", event.get("symbol_a"));
        assertEquals("IBM", event.get("symbol_c"));
        assertEquals("IBM", event.get("symbol_d"));
        assertEquals("", event.get("symbol_e"));    // should be empty string as we are doing absolute XPath
    }

    private void assertData(String element1)
    {
        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];

        assertEquals(element1, event.get("element1"));
        assertEquals("VAL4-1", event.get("nestedElement"));
        assertEquals("VAL21-2", event.get("mappedElement"));
        assertEquals("VAL21-2", event.get("indexedElement"));

        assertEquals(element1, event.get("xpathElement1"));
        assertEquals(2.0, event.get("xpathCountE21"));
        assertEquals("VAL3", event.get("xpathAttrString"));
        assertEquals(5.6, event.get("xpathAttrNum"));
        assertEquals(true, event.get("xpathAttrBool"));

        assertEquals("", event.get("invalidelement"));        // properties not found come back as empty string without schema
        assertEquals("", event.get("invalidattr"));     // attributes not supported when no schema supplied, use XPath
    }

    private void sendEvent(EPServiceProvider engine, String alias, String value) throws Exception
    {
        String xml = XML.replaceAll("VAL1", value);
        sendXMLEvent(engine, alias, xml);
    }

    private void sendXMLEvent(EPServiceProvider engine, String alias, String xml) throws Exception
    {
        InputStream s = new ByteArrayInputStream(xml.getBytes());
        OMElement documentElement = new StAXOMBuilder(s).getDocumentElement();
        EventSender sender = engine.getEPRuntime().getEventSender(alias);
        sender.sendEvent(documentElement);
    }

    private Configuration getConfiguration() throws URISyntaxException
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        // register new representation of events
        config.addPlugInEventRepresentation(new URI(AXIOM_URI),
                AxiomEventRepresentation.class.getName(), null);

        return config;
    }
}
