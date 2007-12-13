package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.client.*;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import javax.xml.xpath.XPathConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;

public class TestEventPropertyDynamicXML extends TestCase
{
    private SupportUpdateListener listener;
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testNoSchema() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM desc = new ConfigurationEventTypeXMLDOM();
        desc.setRootElementName("event");
        configuration.addEventTypeAlias("MyEvent", desc);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String stmt = "select type? from MyEvent";
        EPStatement joinView = epService.getEPAdministrator().createEQL(stmt);
        joinView.addListener(listener);

        sendXMLEvent("<event><type>abc</type></event>");
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("abc", event.get("type?"));
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
}
