package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.client.XMLDOMEventTypeDesc;
import net.esper.support.util.SupportUpdateListener;

import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSInput;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestSchemaXMLEvent extends TestCase
{
    private SupportUpdateListener updateListener;

    private static String XML =
        "<myevent>\n" +
        "  <element1>VAL1</element1>\n" +
        "  <element2>\n" +
        "    <element21>VAL21-1</element21>\n" +
        "    <element21>VAL21-2</element21>\n" +
        "  </element2>\n" +
        "</myevent>";

    public void setUp() throws Exception
    {
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();

        Configuration configuration = new Configuration();
        String schemaUri = ClassLoader.getSystemResource("simpleSchema.xsd").toURI().toString();
        XMLDOMEventTypeDesc eventTypeMeta = new XMLDOMEventTypeDesc();
        eventTypeMeta.addProperty("countElement21", "count(/myevent/element2/element21)", XPathConstants.NUMBER);
        eventTypeMeta.setSchemaURI(schemaUri);
        configuration.addEventTypeAlias("TestXMLType", eventTypeMeta);

        //String stmt = "select element1 from TestXMLType.win:length(100)";

        //EPStatement joinView = epService.getEPAdministrator().createEQL(stmt);
        //joinView.addListener(updateListener);
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

        //epService.getEPRuntime().sendEvent(simpleDoc, "TestXMLType");
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEvent.class);
}
