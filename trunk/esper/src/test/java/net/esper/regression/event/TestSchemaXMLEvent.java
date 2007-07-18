package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    public void setUp() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig());
        epService.initialize();
        updateListener = new SupportUpdateListener();

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

        EPStatement joinView = epService.getEPAdministrator().createEQL(stmt);
        joinView.addListener(updateListener);
    }

    public void testSimpleXML() throws Exception
    {
        sendEvent("test");
        assertData();
    }

    public void testInvalid()
    {
        try
        {
            epService.getEPAdministrator().createEQL("select element1 from TestXMLSchemaType.win:length(100)");
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("Error starting view: Failed to locate property 'element1' in schema [select element1 from TestXMLSchemaType.win:length(100)]", ex.getMessage());
        }
    }

    private void assertData()
    {
        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];

        assertTrue(event.get("nodeProp") instanceof Node);
        assertEquals("SAMPLE_V6", event.get("nested1Prop"));
        assertEquals(true, event.get("nested2Prop"));
        assertEquals("SAMPLE_V8", event.get("complexProp"));
        assertEquals(5.0, event.get("indexedProp"));
        assertEquals(3.0, event.get("customProp"));
        assertEquals(true, event.get("attrOneProp"));
        assertEquals("c", event.get("attrTwoProp"));
    }

    private Configuration getConfig()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        String schemaUri = TestSchemaXMLEvent.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        eventTypeMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        eventTypeMeta.addXPathProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        configuration.addEventTypeAlias("TestXMLSchemaType", eventTypeMeta);

        return configuration;
    }

    private void sendEvent(String value) throws Exception
    {
        String xml = TestSchemaXMLEvent.XML.replaceAll("VAL1", value);
        log.debug(".sendEvent value=" + value);

        InputSource source = new InputSource(new StringReader(xml));
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(simpleDoc);
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEvent.class);
}
