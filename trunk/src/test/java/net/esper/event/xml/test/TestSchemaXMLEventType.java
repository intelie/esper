package net.esper.event.xml.test;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import net.esper.event.EventBean;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.event.xml.XPathPropertyGetter;
import net.esper.event.xml.schema.SchemaXMLEventType;
import net.esper.event.xml.schema.XPathNamespaceContext;
import net.esper.event.xml.simple.SimpleXMLEventType;

import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl;
import com.sun.org.apache.xerces.internal.xs.XSImplementation;
import com.sun.org.apache.xerces.internal.xs.XSLoader;
import com.sun.org.apache.xerces.internal.xs.XSModel;

import junit.framework.TestCase;

public class TestSchemaXMLEventType extends TestCase {

	private Document simpleDoc;
	private SchemaXMLEventType eventType;
	private EventBean event;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		simpleDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("simpleWithSchema.xml"));
		eventType = new SchemaXMLEventType();
		
		XPathFactory factory = XPathFactory.newInstance();
		  
		XPathNamespaceContext ctx = new XPathNamespaceContext();
		ctx.setDefaultNamespace("http://esper.sourceforge.net/samples/schemas/simpleSchema");
		ctx.addPrefix("ss","http://esper.sourceforge.net/samples/schemas/simpleSchema");
		XPath xPath =factory.newXPath();
		xPath.setNamespaceContext(ctx);
		XPathExpression expression = xPath.compile("count(/ss:simpleEvent/ss:nested3/ss:nested4)");
		Map<String,TypedEventPropertyGetter> custom = new HashMap<String,TypedEventPropertyGetter>();
		custom.put("customProp",new XPathPropertyGetter("customProp",expression,XPathConstants.NUMBER));
		eventType.setExplicitProperties(custom);
		eventType.setXPathFactory(factory);
		eventType.setEventName("simpleEvent");
		
		/// Get DOM Implementation using DOM Registry
		System.setProperty(DOMImplementationRegistry.PROPERTY,
				DOMXSImplementationSourceImpl.class.getName());
		
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

		XSImplementation impl =(XSImplementation) registry.getDOMImplementation("XS-Loader");

		XSLoader schemaLoader = impl.createXSLoader(null);
		String uri = ClassLoader.getSystemResource("simpleSchema.xsd").toURI().toString();
		XSModel schema = schemaLoader.loadURI(uri);
		eventType.setXsModel(schema);
		
		event = eventType.newEvent(simpleDoc);
	}

	public void testSimpleProperies() {
		assertEquals("SAMPLE_V6", event.get("prop4"));
	}
	
	public void testNestedProperties() {
		assertEquals(Boolean.TRUE,event.get("nested1.prop2"));
		assertEquals(Boolean.class,event.get("nested1.prop2").getClass());
	}
	
	public void testMappedProperties() {
		assertEquals("SAMPLE_V7",event.get("nested3.nested4('a').prop5[1]")); 
		assertEquals("SAMPLE_V11",event.get("nested3.nested4('c').prop5[2]"));
	}
	
	public void testIndexedProperties() {
		assertEquals(4.0,event.get("nested1.nested2.prop3[2]"));
		assertEquals(Double.class,event.getEventType().getPropertyType("nested1.nested2.prop3[2]"));
	}
	
	public void testCustomProperty() {
		assertEquals(Double.class,event.getEventType().getPropertyType("customProp"));
		assertEquals(new Double(3),event.get("customProp"));
	}
	
	public void testAttrProperty() {
		assertEquals(Boolean.TRUE,event.get("prop4.attr2"));
		assertEquals(Boolean.class,event.getEventType().getPropertyType("prop4.attr2"));
		
		assertEquals("b",event.get("nested3.nested4[2].id"));
		assertEquals(String.class,event.getEventType().getPropertyType("nested3.nested4[1].id"));
		
	}
	
	public void testInvalidCollectionAccess() {
		try {
			String prop = "nested3.nested4.id";
			event.getEventType().getGetter(prop);
			fail("Invalid collection access: " + prop + " acepted");
		} catch (Exception e) {
			//Expected
		}
		try {
			String prop = "nested3.nested4.nested5";
			event.getEventType().getGetter(prop);
			fail("Invalid collection access: " + prop + " acepted");
		} catch (Exception e) {
			//Expected
		}
		
		
	}

}
