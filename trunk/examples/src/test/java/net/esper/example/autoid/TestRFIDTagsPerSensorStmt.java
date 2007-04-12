package net.esper.example.autoid;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.example.support.SupportUpdateListener;
import net.esper.event.EventBean;

import java.net.URL;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

public class TestRFIDTagsPerSensorStmt extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {
        URL url = TestRFIDTagsPerSensorStmt.class.getClassLoader().getResource("esper.examples.cfg.xml");
        Configuration config = new Configuration();
        config.configure(url);

        epService = EPServiceProviderManager.getProvider("AutoIdSim", config);
        epService.initialize();

        listener = new SupportUpdateListener();
        RFIDTagsPerSensorStmt rfidStmt = new RFIDTagsPerSensorStmt(epService.getEPAdministrator());
        rfidStmt.addListener(listener);
    }

    public void testEvents() throws Exception {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);

        Document sensor1Doc = builderFactory.newDocumentBuilder().parse(TestRFIDTagsPerSensorStmt.class.getClassLoader().getResourceAsStream("data/AutoIdSensor1.xml"));
        epService.getEPRuntime().sendEvent(sensor1Doc);
        assertReceived("urn:epc:1:4.16.36", 5);
    }

    private void assertReceived(String sensorId, double numTags)
    {
        assertTrue(listener.isInvoked());
        assertEquals(1, listener.getLastNewData().length);
        EventBean event = listener.getLastNewData()[0];
        assertEquals(sensorId, event.get("sensorId"));
        assertEquals(numTags, event.get("numTagsPerSensor"));
        listener.reset();
    }
}