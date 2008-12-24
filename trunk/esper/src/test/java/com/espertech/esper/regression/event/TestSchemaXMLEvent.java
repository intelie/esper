package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.event.EventTypeAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import java.io.StringReader;

public class TestSchemaXMLEvent extends TestCase
{
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
            "\t<nested3>\n" +
            "\t\t<nested4 id=\"a\">\n" +
            "\t\t\t<prop5>SAMPLE_V7</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V8</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t\t<nested4 id=\"b\">\n" +
            "\t\t\t<prop5>SAMPLE_V9</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t\t<nested4 id=\"c\">\n" +
            "\t\t\t<prop5>SAMPLE_V10</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V11</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t</nested3>\n" +
            "</simpleEvent>";

    public void testSchemaXMLQuery_XPathBacked() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig(true));
        epService.initialize();
        updateListener = new SupportUpdateListener();

        runAssertionQuery();
    }

    public void testSchemaXMLQuery_DOMGetterBacked() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig(true));
        epService.initialize();
        updateListener = new SupportUpdateListener();

        runAssertionQuery();
    }

    public void testSchemaXMLTranspose_DOMGetterBacked() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig(false));
        epService.initialize();
        updateListener = new SupportUpdateListener();
        
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyNestedStream select nested1 from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, true),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select nested1.attr1 as attr1, nested1.prop1 as prop1, nested1.prop2 as prop2, nested1.nested2.prop3 as prop3, nested1.nested2 as nested2 from MyNestedStream");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("prop1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("prop2", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("attr1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("prop3", Integer.class, false, false, false, false, false),
            new EventPropertyDescriptor("nested2", Node.class, false, false, false, false, true),
           }, stmtSelect.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtSelect.getEventType());

        EPStatement stmtSelectWildcard = epService.getEPAdministrator().createEPL("select * from MyNestedStream");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, true),
           }, stmtSelectWildcard.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtSelectWildcard.getEventType());

        EPStatement stmtInsertWildcard = epService.getEPAdministrator().createEPL("insert into MyNestedStreamTwo select nested1.* from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
                new EventPropertyDescriptor("prop1", String.class, false, false, false, false, false),
                new EventPropertyDescriptor("prop2", Boolean.class, false, false, false, false, false),
                new EventPropertyDescriptor("attr1", String.class, false, false, false, false, false),
                new EventPropertyDescriptor("nested2", Node.class, false, false, false, false, true),
           }, stmtInsertWildcard.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        Document eventDoc = sendEvent("test");
        ArrayAssertionUtil.assertProps(stmtSelect.iterator().next(), "prop1,prop2,attr1,prop3".split(","),
                new Object[] {"SAMPLE_V1", "", true, ""});
        EventTypeAssertionUtil.assertConsistency(stmtSelect.iterator().next());

    }

    private void runAssertionQuery() throws Exception
    {
        String stmtSelectWild = "select * from TestXMLSchemaType";
        EPStatement wildStmt = epService.getEPAdministrator().createEPL(stmtSelectWild);
        EventType type = wildStmt.getEventType();

        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, true),
            new EventPropertyDescriptor("prop4", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("nested3", Node.class, false, false, false, false, true),
            new EventPropertyDescriptor("customProp", Double.class, false, false, false, false, false),
           }, type.getPropertyDescriptors());

        String stmt =
                "select nested1 as nodeProp," +
                        "prop4 as nested1Prop," +
                        "nested1.prop2 as nested2Prop," +
                        "nested3.nested4('a').prop5[1] as complexProp," +
                        "nested1.nested2.prop3[2] as indexedProp," +
                        "customProp," +
                        "prop4.attr2 as attrOneProp," +
                        "nested3.nested4[2].id as attrTwoProp" +
                " from TestXMLSchemaType.win:length(100)";

        EPStatement selectStmt = epService.getEPAdministrator().createEPL(stmt);
        selectStmt.addListener(updateListener);
        type = selectStmt.getEventType();
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nodeProp", Node.class, false, false, false, false, true),
            new EventPropertyDescriptor("nested1Prop", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("nested2Prop", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("complexProp", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("indexedProp", Integer.class, false, false, false, false, false),
            new EventPropertyDescriptor("customProp", Double.class, false, false, false, false, false),
            new EventPropertyDescriptor("attrOneProp", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("attrTwoProp", String.class, false, false, false, false, false),
           }, type.getPropertyDescriptors());

        Document eventDoc = sendEvent("test");

        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];

        assertSame(eventDoc.getDocumentElement().getChildNodes().item(1), event.get("nodeProp"));
        assertEquals("SAMPLE_V6", event.get("nested1Prop"));
        assertEquals(true, event.get("nested2Prop"));
        assertEquals("SAMPLE_V8", event.get("complexProp"));
        assertEquals(5, event.get("indexedProp"));
        assertEquals(3.0, event.get("customProp"));
        assertEquals(true, event.get("attrOneProp"));
        assertEquals("c", event.get("attrTwoProp"));

        /**
         * Comment-in for performance testing
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++)
        {
            sendEvent("test");
        }
        long end = System.nanoTime();
        double delta = (end - start) / 1000d / 1000d / 1000d;
        System.out.println(delta);
         */
    }

    public void testAddRemoveType()
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig(false));
        epService.initialize();
        updateListener = new SupportUpdateListener();
        ConfigurationOperations configOps = epService.getEPAdministrator().getConfiguration();

        // test remove type with statement used (no force)
        configOps.addEventTypeAlias("MyXMLEvent", getConfigTestType("p01", false));
        EPStatement stmt = epService.getEPAdministrator().createEPL("select p01 from MyXMLEvent", "stmtOne");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"stmtOne"}, configOps.getEventTypeAliasUsedBy("MyXMLEvent").toArray());

        try {
            configOps.removeEventType("MyXMLEvent", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyXMLEvent"));
        }

        // destroy statement and type
        stmt.destroy();
        assertTrue(configOps.getEventTypeAliasUsedBy("MyXMLEvent").isEmpty());
        assertTrue(configOps.isEventTypeAliasExists("MyXMLEvent"));
        assertTrue(configOps.removeEventType("MyXMLEvent", false));
        assertFalse(configOps.removeEventType("MyXMLEvent", false));    // try double-remove
        assertFalse(configOps.isEventTypeAliasExists("MyXMLEvent"));
        try {
            epService.getEPAdministrator().createEPL("select p01 from MyXMLEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // add back the type
        configOps.addEventTypeAlias("MyXMLEvent", getConfigTestType("p20", false));
        assertTrue(configOps.isEventTypeAliasExists("MyXMLEvent"));
        assertTrue(configOps.getEventTypeAliasUsedBy("MyXMLEvent").isEmpty());

        // compile
        epService.getEPAdministrator().createEPL("select p20 from MyXMLEvent", "stmtTwo");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"stmtTwo"}, configOps.getEventTypeAliasUsedBy("MyXMLEvent").toArray());
        try {
            epService.getEPAdministrator().createEPL("select p01 from MyXMLEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // remove with force
        try {
            configOps.removeEventType("MyXMLEvent", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyXMLEvent"));
        }
        assertTrue(configOps.removeEventType("MyXMLEvent", true));
        assertFalse(configOps.isEventTypeAliasExists("MyXMLEvent"));
        assertTrue(configOps.getEventTypeAliasUsedBy("MyXMLEvent").isEmpty());

        // add back the type
        configOps.addEventTypeAlias("MyXMLEvent", getConfigTestType("p03", false));
        assertTrue(configOps.isEventTypeAliasExists("MyXMLEvent"));

        // compile
        epService.getEPAdministrator().createEPL("select p03 from MyXMLEvent");
        try {
            epService.getEPAdministrator().createEPL("select p20 from MyXMLEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }
    }

    public void testInvalid()
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig(false));
        epService.initialize();

        try
        {
            epService.getEPAdministrator().createEPL("select element1 from TestXMLSchemaType.win:length(100)");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Failed to locate property 'element1' in schema [select element1 from TestXMLSchemaType.win:length(100)]", ex.getMessage());
        }
    }

    private Configuration getConfig(boolean isUseXPathPropertyExpression)
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("TestXMLSchemaType", getConfigTestType(null, isUseXPathPropertyExpression));
        configuration.addEventTypeAlias("TestXMLSchemaType", getConfigNestedType(isUseXPathPropertyExpression));
        return configuration;
    }

    private Document sendEvent(String value) throws Exception
    {
        String xml = TestSchemaXMLEvent.XML.replaceAll("VAL1", value);
        log.debug(".sendEvent value=" + value);

        InputSource source = new InputSource(new StringReader(xml));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(simpleDoc);

        return simpleDoc;
    }

    private ConfigurationEventTypeXMLDOM getConfigTestType(String additionalXPathProperty, boolean isUseXPathPropertyExpression)
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        String schemaUri = TestSchemaXMLEvent.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        eventTypeMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        eventTypeMeta.addXPathProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        eventTypeMeta.setPropertyExprXPath(isUseXPathPropertyExpression);
        if (additionalXPathProperty != null)
        {
            eventTypeMeta.addXPathProperty(additionalXPathProperty, "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        }
        return eventTypeMeta;        
    }

    private ConfigurationEventTypeXMLDOM getConfigNestedType(boolean isUseXPathPropertyExpression)
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("nested1");
        String schemaUri = TestSchemaXMLEvent.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        eventTypeMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        eventTypeMeta.addXPathProperty("customProp", "count(ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        eventTypeMeta.setPropertyExprXPath(isUseXPathPropertyExpression);
        return eventTypeMeta;
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEvent.class);
}
