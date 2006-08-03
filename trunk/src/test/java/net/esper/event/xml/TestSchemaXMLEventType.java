package net.esper.event.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;

import net.esper.event.EventBean;
import net.esper.client.ConfigurationEventTypeXMLDOM;

import org.w3c.dom.Document;
import junit.framework.TestCase;

public class TestSchemaXMLEventType extends TestCase {

    private EventBean eventSchemaOne;
    private EventBean eventSchemaTwo;

	protected void setUp() throws Exception {

        ConfigurationEventTypeXMLDOM configNoNS = new ConfigurationEventTypeXMLDOM();
        configNoNS.setSchemaURI("regression/simpleSchema.xsd");
        configNoNS.setRootElementName("simpleEvent");
        configNoNS.addProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        configNoNS.addNamespacePefix("ss", "http://esper.codehaus.net/samples/schemas/simpleSchema");
        SchemaXMLEventType eventTypeNoNS = new SchemaXMLEventType(configNoNS);

        ConfigurationEventTypeXMLDOM configWithNS = new ConfigurationEventTypeXMLDOM();
        configWithNS.setSchemaURI("regression/PmlCore.xsd");
        configWithNS.setRootElementName("Sensor");
        configWithNS.setRootElementNamespace("urn:autoid:specification:interchange:PMLCore:xml:schema:1");
        configWithNS.addProperty("customProp", "count(/pmlcore:Sensor/pmlcore:Observation/pmlcore:Tag)", XPathConstants.NUMBER);
        configWithNS.addNamespacePefix("pmlcore", "urn:autoid:specification:interchange:PMLCore:xml:schema:1");
        configWithNS.addNamespacePefix("pmluid", "urn:autoid:specification:universal:Identifier:xml:schema:1");
        SchemaXMLEventType eventTypeWithNS = new SchemaXMLEventType(configWithNS);

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);

        Document noNSDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("regression/simpleWithSchema.xml"));
		eventSchemaOne = new XMLEventBean(noNSDoc, eventTypeNoNS);

        Document withNSDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("regression/sensor1.xml"));
        eventSchemaTwo = new XMLEventBean(withNSDoc, eventTypeWithNS);
	}

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

	public void testSimpleProperies() {
		assertEquals("SAMPLE_V6", eventSchemaOne.get("prop4"));
	}
	
	public void testNestedProperties() {
		assertEquals(Boolean.TRUE,eventSchemaOne.get("nested1.prop2"));
		assertEquals(Boolean.class,eventSchemaOne.get("nested1.prop2").getClass());
	}
	
	public void testMappedProperties() {
		assertEquals("SAMPLE_V7",eventSchemaOne.get("nested3.nested4('a').prop5[1]"));
		assertEquals("SAMPLE_V11",eventSchemaOne.get("nested3.nested4('c').prop5[2]"));
	}
	
	public void testIndexedProperties() {
		assertEquals(4.0,eventSchemaOne.get("nested1.nested2.prop3[2]"));
		assertEquals(Double.class,eventSchemaOne.getEventType().getPropertyType("nested1.nested2.prop3[2]"));
	}
	
	public void testCustomProperty() {
		assertEquals(Double.class,eventSchemaOne.getEventType().getPropertyType("customProp"));
		assertEquals(new Double(3),eventSchemaOne.get("customProp"));
	}
	
	public void testAttrProperty() {
		assertEquals(Boolean.TRUE,eventSchemaOne.get("prop4.attr2"));
		assertEquals(Boolean.class,eventSchemaOne.getEventType().getPropertyType("prop4.attr2"));
		
		assertEquals("b",eventSchemaOne.get("nested3.nested4[2].id"));
		assertEquals(String.class,eventSchemaOne.getEventType().getPropertyType("nested3.nested4[1].id"));
	}
	
	public void testInvalidCollectionAccess() {
		try {
			String prop = "nested3.nested4.id";
			eventSchemaOne.getEventType().getGetter(prop);
			fail("Invalid collection access: " + prop + " acepted");
		} catch (Exception e) {
			//Expected
		}
		try {
			String prop = "nested3.nested4.nested5";
			eventSchemaOne.getEventType().getGetter(prop);
			fail("Invalid collection access: " + prop + " acepted");
		} catch (Exception e) {
			//Expected
		}	
	}
}
