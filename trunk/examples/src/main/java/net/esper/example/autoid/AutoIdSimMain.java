package net.esper.example.autoid;

import net.esper.client.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.util.Random;
import java.io.StringReader;
import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AutoIdSimMain {

    private final static Random RANDOM = new Random(System.currentTimeMillis());
    private final static String[] SENSOR_IDS = {"urn:epc:1:4.16.30", "urn:epc:1:4.16.32", "urn:epc:1:4.16.36", "urn:epc:1:4.16.38" };
    private final static String XML_ROOT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<pmlcore:Sensor \n" +
            "  xmlns=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\" \n" +
            "  xmlns:pmlcore=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\" \n" +
            "  xmlns:autoid=\"http://www.autoidcenter.org/2003/xml\" \n" +
            "  xmlns:pmluid=\"urn:autoid:specification:universal:Identifier:xml:schema:1\" \n" +
            "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
            "  xsi:schemaLocation=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1 AutoIdPmlCore.xsd\">\n";

    private final int numEvents;
    private final DocumentBuilder documentBuilder;

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
        if (args.length < 1) {
            System.out.println("Arguments are: <numberOfEvents>");
            System.exit(-1);
        }

        int events = 0;
        try {
            events = Integer.parseInt(args[0]);
        } catch (NullPointerException e) {
            System.out.println("Invalid numberOfEvents:" + args[0]);
            System.exit(-2);
            return;
        }

        // Run the sample
        AutoIdSimMain autoIdSimMain = new AutoIdSimMain(events);
        autoIdSimMain.run();
    }

    public AutoIdSimMain(int numEvents) throws ParserConfigurationException
    {
        this.numEvents = numEvents;

        // set up DOM parser
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        documentBuilder = builderFactory.newDocumentBuilder();
    }

    public void run() throws SAXException, IOException
    {
        // load config - this defines the XML event types to be processed
        URL url = AutoIdSimMain.class.getClassLoader().getResource("esper.examples.cfg.xml");
        Configuration config = new Configuration();
        config.configure(url);

        // get engine instance
        EPServiceProvider epService = EPServiceProviderManager.getProvider("AutoIdSim", config);

        // set up statement
        RFIDTagsPerSensorStmt rfidStmt = new RFIDTagsPerSensorStmt(epService.getEPAdministrator());
        rfidStmt.addListener(new RFIDTagsPerSensorListener());

        // Send events
        int eventCount = 0;
        while(eventCount < numEvents) {
            sendEvent(epService.getEPRuntime());
            eventCount++;
        }
    }

    private void sendEvent(EPRuntime epRuntime) throws SAXException, IOException
    {
        String eventXMLText = generateEvent();
        Document simpleDoc = documentBuilder.parse(new InputSource(new StringReader(eventXMLText)));
        epRuntime.sendEvent(simpleDoc);
    }

    private String generateEvent()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(XML_ROOT);

        String sensorId = SENSOR_IDS[RANDOM.nextInt(SENSOR_IDS.length)];
        buffer.append("<pmluid:ID>");
        buffer.append(sensorId);
        buffer.append("</pmluid:ID>");

        buffer.append("<pmlcore:Observation>");
        buffer.append("<pmlcore:Command>READ_PALLET_TAGS_ONLY</pmlcore:Command>");

        for (int i = 0; i < RANDOM.nextInt(6) + 1; i++)
        {
            buffer.append("<pmlcore:Tag><pmluid:ID>urn:epc:1:2.24.400</pmluid:ID></pmlcore:Tag>");
        }

        buffer.append("</pmlcore:Observation>");
        buffer.append("</pmlcore:Sensor>");

        return buffer.toString();
    }
}
