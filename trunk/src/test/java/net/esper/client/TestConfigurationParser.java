package net.esper.client;

import junit.framework.TestCase;

import javax.xml.xpath.XPathConstants;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestConfigurationParser extends TestCase
{
    private Configuration config;

    public void setUp()
    {
        config = new Configuration();
    }

    public void testConfigureFromStream() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(TestConfiguration.ESPER_TEST_CONFIG);
        ConfigurationParser.doConfigure(config, url.openStream(), url.toString());
        assertFileConfig(config);
    }

    protected static void assertFileConfig(Configuration config)
    {
        // assert alias for class
        assertEquals(3, config.getEventTypeAliases().size());
        assertEquals("com.mycompany.myapp.MySampleEventOne", config.getEventTypeAliases().get("MySampleEventOne"));
        assertEquals("com.mycompany.myapp.MySampleEventTwo", config.getEventTypeAliases().get("MySampleEventTwo"));
        assertEquals("com.mycompany.package.MyLegacyTypeEvent", config.getEventTypeAliases().get("MyLegacyTypeEvent"));

        // assert auto imports
        assertEquals(2, config.getImports().size());
        assertEquals("com.mycompany.myapp.*", config.getImports().get(0));
        assertEquals("com.mycompany.myapp.ClassOne", config.getImports().get(1));

        // assert XML DOM - no schema
        assertEquals(2, config.getEventTypesXMLDOM().size());
        ConfigurationEventTypeXMLDOM noSchemaDesc = config.getEventTypesXMLDOM().get("MyNoSchemaXMLEventAlias");
        assertEquals("MyNoSchemaEvent", noSchemaDesc.getRootElementName());
        assertEquals("/myevent/element1", noSchemaDesc.getXPathProperties().get("element1").getXpath());
        assertEquals(XPathConstants.NUMBER, noSchemaDesc.getXPathProperties().get("element1").getType());

        // assert XML DOM - with schema
        ConfigurationEventTypeXMLDOM schemaDesc = config.getEventTypesXMLDOM().get("MySchemaXMLEventAlias");
        assertEquals("MySchemaEvent", schemaDesc.getRootElementName());
        assertEquals("MySchemaXMLEvent.xsd", schemaDesc.getSchemaResource());
        assertEquals("samples:schemas:simpleSchema", schemaDesc.getRootElementNamespace());
        assertEquals("default-name-space", schemaDesc.getDefaultNamespace());
        assertEquals("/myevent/element1", schemaDesc.getXPathProperties().get("element1").getXpath());
        assertEquals(XPathConstants.NUMBER, schemaDesc.getXPathProperties().get("element1").getType());
        assertEquals(1, schemaDesc.getNamespacePrefixes().size());
        assertEquals("samples:schemas:simpleSchema", schemaDesc.getNamespacePrefixes().get("ss"));

        // assert mapped events
        assertEquals(1, config.getEventTypesMapEvents().size());
        assertTrue(config.getEventTypesMapEvents().keySet().contains("MyMapEvent"));
        Map<String, String> expectedProps = new HashMap<String, String>();
        expectedProps.put("myInt", "int");
        expectedProps.put("myString", "string");
        assertEquals(expectedProps, config.getEventTypesMapEvents().get("MyMapEvent"));

        // assert legacy type declaration
        assertEquals(1, config.getEventTypesLegacy().size());
        ConfigurationEventTypeLegacy legacy = config.getEventTypesLegacy().get("MyLegacyTypeEvent");
        assertEquals(ConfigurationEventTypeLegacy.CodeGeneration.ENABLED, legacy.getCodeGeneration());
        assertEquals(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC, legacy.getAccessorStyle());
        assertEquals(1, legacy.getFieldProperties().size());
        assertEquals("myFieldName", legacy.getFieldProperties().get(0).getAccessorFieldName());
        assertEquals("myfieldprop", legacy.getFieldProperties().get(0).getName());
        assertEquals(1, legacy.getMethodProperties().size());
        assertEquals("myAccessorMethod", legacy.getMethodProperties().get(0).getAccessorMethodName());
        assertEquals("mymethodprop", legacy.getMethodProperties().get(0).getName());
    }
}