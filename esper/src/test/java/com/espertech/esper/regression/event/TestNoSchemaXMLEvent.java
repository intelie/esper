package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.xml.SupportXPathFunctionResolver;
import com.espertech.esper.support.xml.SupportXPathVariableResolver;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import java.io.StringReader;

public class TestNoSchemaXMLEvent extends TestCase
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
        "  <element3 attrString=\"VAL3\" attrNum=\"5\" attrBool=\"true\"/>\n" +
        "  <element4><element41>VAL4-1</element41></element4>\n" +
        "</myevent>";

    public void testVariableResolution() throws Exception
    {
        // test for ESPER-341 
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addVariable("var", int.class, 0);

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("myevent");
        configuration.addEventType("TestXMLNoSchemaType", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getProvider("TestNoSchemaXML", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmtText = "select var from TestXMLNoSchemaType.win:length(100)";
        epService.getEPAdministrator().createEPL(stmtText);
    }

    public void testSimpleXMLXPathProperties() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("myevent");
        xmlDOMEventTypeDesc.addXPathProperty("xpathElement1", "/myevent/element1", XPathConstants.STRING);
        xmlDOMEventTypeDesc.addXPathProperty("xpathCountE21", "count(/myevent/element2/element21)", XPathConstants.NUMBER);
        xmlDOMEventTypeDesc.addXPathProperty("xpathAttrString", "/myevent/element3/@attrString", XPathConstants.STRING);
        xmlDOMEventTypeDesc.addXPathProperty("xpathAttrNum", "/myevent/element3/@attrNum", XPathConstants.NUMBER);
        xmlDOMEventTypeDesc.addXPathProperty("xpathAttrBool", "/myevent/element3/@attrBool", XPathConstants.BOOLEAN);
        xmlDOMEventTypeDesc.addXPathProperty("stringCastLong", "/myevent/element3/@attrNum", XPathConstants.STRING, "long");
        xmlDOMEventTypeDesc.addXPathProperty("stringCastDouble", "/myevent/element3/@attrNum", XPathConstants.STRING, "double");
        xmlDOMEventTypeDesc.addXPathProperty("numCastInt", "/myevent/element3/@attrNum", XPathConstants.NUMBER, "int");
        xmlDOMEventTypeDesc.setXPathFunctionResolver(SupportXPathFunctionResolver.class.getName());
        xmlDOMEventTypeDesc.setXPathVariableResolver(SupportXPathVariableResolver.class.getName());
        configuration.addEventType("TestXMLNoSchemaType", xmlDOMEventTypeDesc);

        xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("my.event2");
        configuration.addEventType("TestXMLWithDots", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getProvider("TestNoSchemaXML", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        // assert type metadata
        EventTypeSPI type = (EventTypeSPI) ((EPServiceProviderSPI)epService).getEventAdapterService().getExistsTypeByName("TestXMLNoSchemaType");
        assertEquals(EventTypeMetadata.ApplicationType.XML, type.getMetadata().getOptionalApplicationType());
        assertEquals(null, type.getMetadata().getOptionalSecondaryNames());
        assertEquals("TestXMLNoSchemaType", type.getMetadata().getPrimaryName());
        assertEquals("TestXMLNoSchemaType", type.getMetadata().getPublicName());
        assertEquals("TestXMLNoSchemaType", type.getName());
        assertEquals(EventTypeMetadata.TypeClass.APPLICATION, type.getMetadata().getTypeClass());
        assertEquals(true, type.getMetadata().isApplicationConfigured());
        
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("xpathElement1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("xpathCountE21", Double.class, false, false, false, false, false),
            new EventPropertyDescriptor("xpathAttrString", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("xpathAttrNum", Double.class, false, false, false, false, false),
            new EventPropertyDescriptor("xpathAttrBool", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("stringCastLong", Long.class, false, false, false, false, false),
            new EventPropertyDescriptor("stringCastDouble", Double.class, false, false, false, false, false),
            new EventPropertyDescriptor("numCastInt", Integer.class, false, false, false, false, false),
           }, type.getPropertyDescriptors());

        String stmt =
                "select xpathElement1, xpathCountE21, xpathAttrString, xpathAttrNum, xpathAttrBool," +
                       "stringCastLong," +
                       "stringCastDouble," +
                       "numCastInt " +
                       "from TestXMLNoSchemaType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        // Generate document with the specified in element1 to confirm we have independent events
        sendEvent("EventA");
        assertDataSimpleXPath("EventA");

        sendEvent("EventB");
        assertDataSimpleXPath("EventB");
    }

    public void testSimpleXMLDOMGetter() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("myevent");
        xmlDOMEventTypeDesc.setXPathPropertyExpr(false);    // <== DOM getter
        configuration.addEventType("TestXMLNoSchemaType", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getProvider("TestNoSchemaXML", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt =
                "select element1, invalidelement, " +
                       "element4.element41 as nestedElement," +
                       "element2.element21('e21_2') as mappedElement," +
                       "element2.element21[1] as indexedElement," +
                       "element3.myattribute as invalidattribute " +
                       "from TestXMLNoSchemaType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        // Generate document with the specified in element1 to confirm we have independent events
        sendEvent("EventA");
        assertDataGetter("EventA", false);

        sendEvent("EventB");
        assertDataGetter("EventB", false);
    }

    public void testSimpleXMLXPathGetter() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("myevent");
        xmlDOMEventTypeDesc.setXPathPropertyExpr(true);    // <== XPath getter
        configuration.addEventType("TestXMLNoSchemaType", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getProvider("TestNoSchemaXML", configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt =
                "select element1, invalidelement, " +
                       "element4.element41 as nestedElement," +
                       "element2.element21('e21_2') as mappedElement," +
                       "element2.element21[1] as indexedElement," +
                       "element3.myattribute as invalidattribute " +
                       "from TestXMLNoSchemaType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        // Generate document with the specified in element1 to confirm we have independent events
        sendEvent("EventA");
        assertDataGetter("EventA", true);

        sendEvent("EventB");
        assertDataGetter("EventB", true);
    }

    public void testNestedXMLDOMGetter() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("a");
        xmlDOMEventTypeDesc.addXPathProperty("element1", "/a/b/c", XPathConstants.STRING);
        configuration.addEventType("AEvent", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select b.c as type, element1, result1 from AEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent("<a><b><c></c></b></a>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("", event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent("<a><b></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals(null, event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent("<a><b><c>text</c></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("text", event.get("type"));
        assertEquals("text", event.get("element1"));
    }

    public void testNestedXMLXPathGetter() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("a");
        xmlDOMEventTypeDesc.setXPathPropertyExpr(true);
        xmlDOMEventTypeDesc.addXPathProperty("element1", "/a/b/c", XPathConstants.STRING);
        configuration.addEventType("AEvent", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select b.c as type, element1, result1 from AEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent("<a><b><c></c></b></a>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("", event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent("<a><b></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("", event.get("type"));
        assertEquals("", event.get("element1"));

        sendXMLEvent("<a><b><c>text</c></b></a>");
        event = updateListener.assertOneGetNewAndReset();
        assertEquals("text", event.get("type"));
        assertEquals("text", event.get("element1"));
    }

    public void testDotEscapeSyntax() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName("myroot");
        configuration.addEventType("AEvent", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select a\\.b.c\\.d as val from AEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent("<myroot><a.b><c.d>value</c.d></a.b></myroot>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("value", event.get("val"));
    }

    public void testEventXML() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
        desc.addXPathProperty("event.type", "/event/@type", XPathConstants.STRING);
        desc.addXPathProperty("event.uid", "/event/@uid", XPathConstants.STRING);
        desc.setRootElementName("event");
        configuration.addEventType("MyEvent", desc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select event.type as type, event.uid as uid from MyEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        sendXMLEvent("<event type=\"a-f-G\" uid=\"terminal.55\" time=\"2007-04-19T13:05:20.22Z\" version=\"2.0\"></event>");
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("a-f-G", event.get("type"));
        assertEquals("terminal.55", event.get("uid"));
    }

    public void testElementNode() throws Exception
    {
        // test for Esper-129
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
        desc.addXPathProperty("event.type", "//event/@type", XPathConstants.STRING);
        desc.addXPathProperty("event.uid", "//event/@uid", XPathConstants.STRING);
        desc.setRootElementName("batch-event");
        configuration.addEventType("MyEvent", desc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select event.type as type, event.uid as uid from MyEvent";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<batch-event>" +
                     "<event type=\"a-f-G\" uid=\"terminal.55\" time=\"2007-04-19T13:05:20.22Z\" version=\"2.0\"/>" +
                        "</batch-event>";
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document doc = builderFactory.newDocumentBuilder().parse(source);
        Element topElement = doc.getDocumentElement();

        epService.getEPRuntime().sendEvent(topElement);
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("a-f-G", event.get("type"));
        assertEquals("terminal.55", event.get("uid"));
    }

    public void testNamespaceXPathRelative() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
        desc.setRootElementName("getQuote");
        desc.setDefaultNamespace("http://services.samples/xsd");
        desc.setRootElementNamespace("http://services.samples/xsd");
        desc.addNamespacePrefix("m0", "http://services.samples/xsd");
        desc.setXPathResolvePropertiesAbsolute(false);
        desc.setXPathPropertyExpr(true);
        configuration.addEventType("StockQuote", desc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select request.symbol as symbol_a, symbol as symbol_b from StockQuote";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<m0:getQuote xmlns:m0=\"http://services.samples/xsd\"><m0:request><m0:symbol>IBM</m0:symbol></m0:request></m0:getQuote>";
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document doc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(doc);
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("IBM", event.get("symbol_a"));
        assertEquals("IBM", event.get("symbol_b"));
    }

    public void testNamespaceXPathAbsolute() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
        desc.addXPathProperty("symbol_a", "//m0:symbol", XPathConstants.STRING);
        desc.addXPathProperty("symbol_b", "//*[local-name(.) = 'getQuote' and namespace-uri(.) = 'http://services.samples/xsd']", XPathConstants.STRING);
        desc.addXPathProperty("symbol_c", "/m0:getQuote/m0:request/m0:symbol", XPathConstants.STRING);
        desc.setRootElementName("getQuote");
        desc.setDefaultNamespace("http://services.samples/xsd");
        desc.setRootElementNamespace("http://services.samples/xsd");
        desc.addNamespacePrefix("m0", "http://services.samples/xsd");
        desc.setXPathResolvePropertiesAbsolute(true);
        desc.setXPathPropertyExpr(true);
        configuration.addEventType("StockQuote", desc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select symbol_a, symbol_b, symbol_c, request.symbol as symbol_d, symbol as symbol_e from StockQuote";
        EPStatement joinView = epService.getEPAdministrator().createEPL(stmt);
        joinView.addListener(updateListener);

        String xml = "<m0:getQuote xmlns:m0=\"http://services.samples/xsd\"><m0:request><m0:symbol>IBM</m0:symbol></m0:request></m0:getQuote>";
        //String xml = "<getQuote><request><symbol>IBM</symbol></request></getQuote>";
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document doc = builderFactory.newDocumentBuilder().parse(source);

        // For XPath resolution testing and namespaces...
        /*
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathNamespaceContext ctx = new XPathNamespaceContext();
        ctx.addPrefix("m0", "http://services.samples/xsd");
        xPath.setNamespaceContext(ctx);
        XPathExpression expression = xPath.compile("/m0:getQuote/m0:request/m0:symbol");
        xPath.setNamespaceContext(ctx);
        System.out.println("result=" + expression.evaluate(doc,XPathConstants.STRING));
        */

        epService.getEPRuntime().sendEvent(doc);
        EventBean event = updateListener.assertOneGetNewAndReset();
        assertEquals("IBM", event.get("symbol_a"));
        assertEquals("IBM", event.get("symbol_b"));
        assertEquals("IBM", event.get("symbol_c"));
        assertEquals("IBM", event.get("symbol_d"));
        assertEquals("", event.get("symbol_e"));    // should be empty string as we are doing absolute XPath
    }

    private void assertDataSimpleXPath(String element1)
    {
        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];

        assertEquals(element1, event.get("xpathElement1"));
        assertEquals(2.0, event.get("xpathCountE21"));
        assertEquals("VAL3", event.get("xpathAttrString"));
        assertEquals(5d, event.get("xpathAttrNum"));
        assertEquals(true, event.get("xpathAttrBool"));
        assertEquals(5L, event.get("stringCastLong"));
        assertEquals(5d, event.get("stringCastDouble"));
        assertEquals(5, event.get("numCastInt"));
    }

    private void assertDataGetter(String element1, boolean isInvalidReturnsEmptyString)
    {
        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];

        assertEquals(element1, event.get("element1"));
        assertEquals("VAL4-1", event.get("nestedElement"));
        assertEquals("VAL21-2", event.get("mappedElement"));
        assertEquals("VAL21-2", event.get("indexedElement"));

        if (isInvalidReturnsEmptyString)
        {
            assertEquals("", event.get("invalidelement"));
            assertEquals("", event.get("invalidattribute"));
        }
        else
        {
            assertEquals(null, event.get("invalidelement"));
            assertEquals(null, event.get("invalidattribute"));
        }
    }

    private void sendEvent(String value) throws Exception
    {
        String xml = XML.replaceAll("VAL1", value);
        log.debug(".sendEvent value=" + value);

        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(simpleDoc);
    }

    private void sendXMLEvent(String xml) throws Exception
    {
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(simpleDoc);
    }

    private static final Log log = LogFactory.getLog(TestNoSchemaXMLEvent.class);
}





