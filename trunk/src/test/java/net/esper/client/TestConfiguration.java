package net.esper.client;

import junit.framework.TestCase;

import javax.xml.xpath.XPathConstants;
import java.net.URL;
import java.io.File;


public class TestConfiguration extends TestCase
{
    protected static final String ESPER_TEST_CONFIG = "esper.test.cfg.xml";
    private final static String SAMPLE_EVENT_NAME = "MySampleEvent";
    private final static String SAMPLE_EVENT_CLASS = "com.mycompany.myapp.MySampleEvent";
    private final static String SAMPLE_IMPORT_PACKAGE = "com.mycompany.myapp.*";

    private Configuration config;

    public void setUp()
    {
        config = new Configuration();
    }

    public void testString() throws Exception
    {
        config.configure(ESPER_TEST_CONFIG);
        assertFileConfig();
    }

    public void testURL() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(ESPER_TEST_CONFIG);
        config.configure(url);
        assertFileConfig();
    }

    public void testFile() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(ESPER_TEST_CONFIG);
        File file = new File(url.toURI());
        config.configure(file);
        assertFileConfig();
    }

    public void testAddEventTypeAlias()
    {
        config.addEventTypeAlias("AEventType", "BClassName");

        assertEquals(1, config.getEventTypeAliases().size());
        assertEquals("BClassName", config.getEventTypeAliases().get("AEventType"));
        assertDefaultConfig();
    }
    
    private void assertFileConfig()
    {
        assertEquals(1, config.getEventTypeAliases().size());
        assertEquals(SAMPLE_EVENT_CLASS, config.getEventTypeAliases().get(SAMPLE_EVENT_NAME));

        assertEquals(1, config.getImports().size());
        assertEquals(SAMPLE_IMPORT_PACKAGE, config.getImports().get(0));

        assertEquals(2, config.getEventTypesXMLDOM().size());

        ConfigurationEventTypeXMLDOM noSchemaDesc = config.getEventTypesXMLDOM().get("MyNoSchemaXMLEventAlias");
        assertEquals("MyNoSchemaEvent", noSchemaDesc.getRootElementName());
        assertEquals("/myevent/element1", noSchemaDesc.getProperties().get("element1").getXpath());
        assertEquals(XPathConstants.NUMBER, noSchemaDesc.getProperties().get("element1").getType());

        ConfigurationEventTypeXMLDOM schemaDesc = config.getEventTypesXMLDOM().get("MySchemaXMLEventAlias");
        assertEquals("MySchemaEvent", schemaDesc.getRootElementName());
        assertEquals("MySchemaXMLEvent.xsd", schemaDesc.getSchemaURI());
        assertEquals("http://esper.codehaus.net/samples/schemas/simpleSchema", schemaDesc.getRootElementNamespace());
        assertEquals("default-name-space", schemaDesc.getDefaultNamespace());
        assertEquals("/myevent/element1", schemaDesc.getProperties().get("element1").getXpath());
        assertEquals(XPathConstants.NUMBER, schemaDesc.getProperties().get("element1").getType());
        assertEquals(1, schemaDesc.getNamespacePrefixes().size());
        assertEquals("http://esper.codehaus.net/samples/schemas/simpleSchema", schemaDesc.getNamespacePrefixes().get("ss"));
    }

    private void assertDefaultConfig()
    {
        assertEquals(4, config.getImports().size());
        assertEquals("java.lang.*", config.getImports().get(0));
        assertEquals("java.math.*", config.getImports().get(1));
        assertEquals("java.text.*", config.getImports().get(2));
        assertEquals("java.util.*", config.getImports().get(3));
    }
}
