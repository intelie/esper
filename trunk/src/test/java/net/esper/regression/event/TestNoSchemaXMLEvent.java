package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

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
        "    <element21>VAL21-1</element21>\n" +
        "    <element21>VAL21-2</element21>\n" +
        "  </element2>\n" +
        "</myevent>";

    public void setUp()
    {
        Configuration configuration = new Configuration();
        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootNodeName("myevent");
        xmlDOMEventTypeDesc.addProperty("xpathElement1", "/myevent/element1", XPathConstants.STRING);
        xmlDOMEventTypeDesc.addProperty("xpathCountE21", "count(/myevent/element2/element21)", XPathConstants.NUMBER);
        configuration.addEventTypeAlias("TestXMLType", xmlDOMEventTypeDesc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmt = "select element1, xpathElement1, xpathCountE21 from TestXMLType.win:length(100)";

        EPStatement joinView = epService.getEPAdministrator().createEQL(stmt);
        joinView.addListener(updateListener);
    }

    public void testSimpleXML() throws Exception
    {
        sendEvent("test");
        assertData("test", 2);
    }

    private void assertData(String element1, double count)
    {
        assertNotNull(updateListener.getLastNewData());
        EventBean event = updateListener.getLastNewData()[0];
        assertEquals(element1, event.get("element1"));
        assertEquals(element1, event.get("xpathElement1"));
        assertEquals(count, event.get("xpathCountE21"));
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

    private static final Log log = LogFactory.getLog(TestNoSchemaXMLEvent.class);
}

