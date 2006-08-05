package net.esper.example.autoid;

import net.esper.client.*;
import net.esper.example.marketdatafeed.*;
import net.esper.event.xml.SchemaXMLEventType;

import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.util.Random;

public class AutoIdSimMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        /*

    public void testWithNSSimpleProperies() {
        assertEquals("urn:epc:1:4.16.36", eventSchemaTwo.get("ID"));
        assertEquals("READ_PALLET_TAGS_ONLY", eventSchemaTwo.get("Observation[1].Command"));
    }

    public void testWithNSArrayProperties() {
        assertEquals("urn:epc:1:2.24.402", eventSchemaTwo.get("Observation[1].Tag[3].ID"));
    }

    public void testWithNSCustomProperties() {
        assertEquals(5.0, eventSchemaTwo.get("customProp"));
    }

        Document withNSDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("regression/sensor1.xml"));
        eventSchemaTwo = new XMLEventBean(withNSDoc, eventTypeWithNS);

        */

        // Run the sample
        AutoIdSimMain feedSimMain = new AutoIdSimMain();
        feedSimMain.run();
    }

    public AutoIdSimMain()
    {
        Configuration config = new Configuration();
        config.configure()
    }

    public void run() throws IOException, InterruptedException
    {
    }
}
