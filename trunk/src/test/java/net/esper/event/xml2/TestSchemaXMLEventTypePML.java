package net.esper.event.xml2;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import net.esper.event.EventBean;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.event.xml.XPathPropertyGetter;
import net.esper.event.xml.SchemaXMLEventType;
import net.esper.event.xml.XPathNamespaceContext;
import net.esper.client.ConfigurationEventTypeXMLDOM;

import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl;
import com.sun.org.apache.xerces.internal.xs.XSImplementation;
import com.sun.org.apache.xerces.internal.xs.XSLoader;
import com.sun.org.apache.xerces.internal.xs.XSModel;

import junit.framework.TestCase;

public class TestSchemaXMLEventTypePML extends TestCase {

    private EventBean event;
	
	protected void setUp() throws Exception {
		super.setUp();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		Document simpleDoc = builderFactory.newDocumentBuilder().parse(ClassLoader.getSystemResourceAsStream("sensor1.xml"));

		XPathFactory factory = XPathFactory.newInstance();
			
		XPathNamespaceContext ctx = new XPathNamespaceContext();
		ctx.addPrefix("pmlcore","urn:autoid:specification:interchange:PMLCore:xml:schema:1");
		ctx.addPrefix("pmluid","urn:autoid:specification:universal:Identifier:xml:schema:1");
		XPath xPath =factory.newXPath();
		xPath.setNamespaceContext(ctx);
		XPathExpression expression = xPath.compile("count(/pmlcore:Sensor/pmlcore:Observation/pmlcore:Tag)");

		Map<String,TypedEventPropertyGetter> explicitProperties = new HashMap<String, TypedEventPropertyGetter>();
		explicitProperties.put("customProp",new XPathPropertyGetter("customProp",expression,XPathConstants.NUMBER));

		InputSource source = new InputSource(ClassLoader.getSystemResourceAsStream("sensor1.xml"));
		System.out.println(expression.evaluate(source));
		/// Get DOM Implementation using DOM Registry
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        registry.addSource(new DOMXSImplementationSourceImpl());		
		XSImplementation impl =(XSImplementation) registry.getDOMImplementation("XS-Loader");

		XSLoader schemaLoader = impl.createXSLoader(null);
		String uri = ClassLoader.getSystemResource("PmlCore.xsd").toURI().toString();
		XSModel schema = schemaLoader.loadURI(uri);

        ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
        config.setSchemaURI(null);
        config.setRootNodeName("simpleEvent");
        config.addProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);
        SchemaXMLEventType eventType = new SchemaXMLEventType(config);
        //explicitProperties, factory, schema, "Sensor", "urn:autoid:specification:interchange:PMLCore:xml:schema:1");

		event = eventType.newEvent(simpleDoc);
	}

	public void testSimpleProperies() {
		assertEquals("urn:epc:1:4.16.36", event.get("ID"));
		assertEquals("READ_PALLET_TAGS_ONLY", event.get("Observation[1].Command"));
	}
	
	public void testArrayProperties() {
		assertEquals("urn:epc:1:2.24.402",event.get("Observation[1].Tag[3].ID"));
	}
	
	public void testCustomProperties() {
		assertEquals(5.0,event.get("customProp"));
	}
	
	

}
