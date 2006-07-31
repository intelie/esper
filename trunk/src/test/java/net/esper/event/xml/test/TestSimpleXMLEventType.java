package net.esper.event.xml.test;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import net.esper.event.EventBean;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.event.xml.XPathPropertyGetter;
import net.esper.event.xml.simple.SimpleXMLEventType;

import org.w3c.dom.Document;

import junit.framework.TestCase;

public class TestSimpleXMLEventType extends TestCase {

    private EventBean event;
	
	protected void setUp() throws Exception {
		super.setUp();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		Document simpleDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("simple.xml"));

		XPathFactory factory = XPathFactory.newInstance();
		XPathExpression expression = factory.newXPath().compile("count(/simpleEvent/nested3/nested4)");  
		Map<String,TypedEventPropertyGetter> custom = new HashMap<String,TypedEventPropertyGetter>();
		custom.put("customProp",new XPathPropertyGetter("customProp",expression,XPathConstants.NUMBER));

        SimpleXMLEventType eventType = new SimpleXMLEventType();
		eventType.setExplicitProperties(custom);
		eventType.setXPathFactory(factory);
		eventType.setEventName("simpleEvent"); 
		event = eventType.newEvent(simpleDoc);
	}

	public void testSimpleProperies() {
		assertEquals("SAMPLE_V6", event.get("prop4"));
	}
	
	public void testNestedProperties() {
		assertEquals("true",event.get("nested1.prop2"));
	}
	
	public void testMappedProperties() {
		assertEquals("SAMPLE_V7",event.get("nested3.nested4('a').prop5[1]")); 
		assertEquals("SAMPLE_V11",event.get("nested3.nested4('c').prop5[2]"));
	}
	
	public void testIndexedProperties() {
		assertEquals("4",event.get("nested1.nested2.prop3[2]"));
		assertEquals(String.class,event.getEventType().getPropertyType("nested1.nested2.prop3[2]"));
	}
	
	public void testCustomProperty() {
		assertEquals(Double.class,event.getEventType().getPropertyType("customProp"));
		assertEquals(new Double(3),event.get("customProp"));
		
	}

}
