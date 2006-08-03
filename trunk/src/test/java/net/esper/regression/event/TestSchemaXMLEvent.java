package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;

import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;
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
        Configuration configuration = new Configuration();
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        String schemaUri = ClassLoader.getSystemResource(CLASSLOADER_SCHEMA_URI).toURI().toString();
        eventTypeMeta.setSchemaURI(schemaUri);
        eventTypeMeta.addProperty("countElement21", "count(/myevent/element2/element21)", XPathConstants.NUMBER);
        configuration.addEventTypeAlias("TestXMLType", eventTypeMeta);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();


        String stmt = "select element1 from TestXMLType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEQL(stmt);
        joinView.addListener(updateListener);
    }

    public void testSimpleXML() throws Exception
    {
        sendEvent("test");
        assertNotNull(updateListener.getLastNewData());
    }

    private void sendEvent(String value) throws Exception
    {
        String xml = TestSchemaXMLEvent.XML.replaceAll("VAL1", value);
        TestSchemaXMLEvent.log.debug(".sendEvent value=" + value);

        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        epService.getEPRuntime().sendEvent(simpleDoc);
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEvent.class);
}
