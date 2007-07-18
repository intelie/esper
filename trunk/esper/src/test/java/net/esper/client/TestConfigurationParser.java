package net.esper.client;

import junit.framework.TestCase;

import javax.xml.xpath.XPathConstants;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

    public void testEngineDefaults()
    {
        config = new Configuration();
        
        assertTrue(config.getEngineDefaults().getThreading().isInsertIntoDispatchPreserveOrder());
        assertTrue(config.getEngineDefaults().getThreading().isListenerDispatchPreserveOrder());
        assertEquals(1000, config.getEngineDefaults().getThreading().getListenerDispatchTimeout());
        assertTrue(config.getEngineDefaults().getThreading().isInternalTimerEnabled());
        assertEquals(100, config.getEngineDefaults().getThreading().getInternalTimerMsecResolution());

        assertEquals(Configuration.PropertyResolutionStyle.CASE_SENSITIVE, config.getEngineDefaults().getEventMeta().getClassPropertyResolutionStyle());

        assertTrue(config.getEngineDefaults().getViewResources().isShareViews());

        assertFalse(config.getEngineDefaults().getLogging().isEnableExecutionDebug());
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
        assertEquals(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE, legacy.getPropertyResolutionStyle());

        // assert database reference - data source config
        assertEquals(2, config.getDatabaseReferences().size());
        ConfigurationDBRef configDBRef = config.getDatabaseReferences().get("mydb1");
        ConfigurationDBRef.DataSourceConnection dsDef = (ConfigurationDBRef.DataSourceConnection) configDBRef.getConnectionFactoryDesc();
        assertEquals("java:comp/env/jdbc/mydb", dsDef.getContextLookupName());
        assertEquals("{java.naming.provider.url=iiop://localhost:1050, java.naming.factory.initial=com.myclass.CtxFactory}", dsDef.getEnvProperties().toString());
        assertEquals(ConfigurationDBRef.ConnectionLifecycleEnum.POOLED, configDBRef.getConnectionLifecycleEnum());
        assertNull(configDBRef.getConnectionSettings().getAutoCommit());
        assertNull(configDBRef.getConnectionSettings().getCatalog());
        assertNull(configDBRef.getConnectionSettings().getReadOnly());
        assertNull(configDBRef.getConnectionSettings().getTransactionIsolation());
        ConfigurationDBRef.LRUCacheDesc lruCache = (ConfigurationDBRef.LRUCacheDesc) configDBRef.getDataCacheDesc();
        assertEquals(10, lruCache.getSize());

        // assert database reference - driver manager config
        configDBRef = config.getDatabaseReferences().get("mydb2");
        ConfigurationDBRef.DriverManagerConnection dmDef = (ConfigurationDBRef.DriverManagerConnection) configDBRef.getConnectionFactoryDesc();
        assertEquals("my.sql.Driver", dmDef.getClassName());
        assertEquals("jdbc:mysql://localhost", dmDef.getUrl());
        assertEquals("myuser1", dmDef.getOptionalUserName());
        assertEquals("mypassword1", dmDef.getOptionalPassword());
        assertEquals("{user=myuser2, password=mypassword2, somearg=someargvalue}", dmDef.getOptionalProperties().toString());
        assertEquals(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN, configDBRef.getConnectionLifecycleEnum());
        assertEquals((Boolean) false, configDBRef.getConnectionSettings().getAutoCommit());
        assertEquals("test", configDBRef.getConnectionSettings().getCatalog());
        assertEquals(Boolean.TRUE, configDBRef.getConnectionSettings().getReadOnly());
        assertEquals(new Integer(3), configDBRef.getConnectionSettings().getTransactionIsolation());
        ConfigurationDBRef.ExpiryTimeCacheDesc expCache = (ConfigurationDBRef.ExpiryTimeCacheDesc) configDBRef.getDataCacheDesc();
        assertEquals(60.5, expCache.getMaxAgeSeconds());
        assertEquals(120.1, expCache.getPurgeIntervalSeconds());

        // assert custom view implementations
        List<ConfigurationPlugInView> configViews = config.getPlugInViews();
        assertEquals(2, configViews.size());
        for (int i = 0; i < configViews.size(); i++)
        {
            ConfigurationPlugInView entry = configViews.get(i);
            assertEquals("ext" + i, entry.getNamespace());
            assertEquals("myview" + i, entry.getName());
            assertEquals("com.mycompany.MyViewFactory" + i, entry.getFactoryClassName());
        }

        // assert adapter loaders parsed
        List<ConfigurationAdapterLoader> adapters = config.getAdapterLoaders();
        assertEquals(2, adapters.size());
        ConfigurationAdapterLoader adapterOne = adapters.get(0);
        assertEquals("Loader1", adapterOne.getLoaderName());
        assertEquals("net.esper.support.adapter.SupportLoaderOne", adapterOne.getClassName());
        assertEquals(2, adapterOne.getConfigProperties().size());
        assertEquals("val1", adapterOne.getConfigProperties().get("name1"));
        assertEquals("val2", adapterOne.getConfigProperties().get("name2"));

        ConfigurationAdapterLoader adapterTwo = adapters.get(1);
        assertEquals("Loader2", adapterTwo.getLoaderName());
        assertEquals("net.esper.support.adapter.SupportLoaderTwo", adapterTwo.getClassName());
        assertEquals(0, adapterTwo.getConfigProperties().size());

        // assert plug-in aggregation function loaded
        assertEquals(2, config.getPlugInAggregationFunctions().size());
        ConfigurationPlugInAggregationFunction pluginAgg = config.getPlugInAggregationFunctions().get(0);
        assertEquals("com.mycompany.MyMatrixAggregationMethod0", pluginAgg.getFunctionClassName());
        assertEquals("func1", pluginAgg.getName());
        pluginAgg = config.getPlugInAggregationFunctions().get(1);
        assertEquals("com.mycompany.MyMatrixAggregationMethod1", pluginAgg.getFunctionClassName());
        assertEquals("func2", pluginAgg.getName());

        // assert plug-in guard objects loaded
        assertEquals(4, config.getPlugInPatternObjects().size());
        ConfigurationPlugInPatternObject pluginPattern = config.getPlugInPatternObjects().get(0);
        assertEquals("com.mycompany.MyGuardFactory0", pluginPattern.getFactoryClassName());
        assertEquals("ext0", pluginPattern.getNamespace());
        assertEquals("guard1", pluginPattern.getName());
        assertEquals(ConfigurationPlugInPatternObject.PatternObjectType.GUARD, pluginPattern.getPatternObjectType());
        pluginPattern = config.getPlugInPatternObjects().get(1);
        assertEquals("com.mycompany.MyGuardFactory1", pluginPattern.getFactoryClassName());
        assertEquals("ext1", pluginPattern.getNamespace());
        assertEquals("guard2", pluginPattern.getName());
        assertEquals(ConfigurationPlugInPatternObject.PatternObjectType.GUARD, pluginPattern.getPatternObjectType());
        pluginPattern = config.getPlugInPatternObjects().get(2);
        assertEquals("com.mycompany.MyObserverFactory0", pluginPattern.getFactoryClassName());
        assertEquals("ext0", pluginPattern.getNamespace());
        assertEquals("observer1", pluginPattern.getName());
        assertEquals(ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER, pluginPattern.getPatternObjectType());
        pluginPattern = config.getPlugInPatternObjects().get(3);
        assertEquals("com.mycompany.MyObserverFactory1", pluginPattern.getFactoryClassName());
        assertEquals("ext1", pluginPattern.getNamespace());
        assertEquals("observer2", pluginPattern.getName());
        assertEquals(ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER, pluginPattern.getPatternObjectType());

        // assert engine defaults
        assertFalse(config.getEngineDefaults().getThreading().isInsertIntoDispatchPreserveOrder());
        assertFalse(config.getEngineDefaults().getThreading().isListenerDispatchPreserveOrder());
        assertEquals(2000, config.getEngineDefaults().getThreading().getListenerDispatchTimeout());
        assertFalse(config.getEngineDefaults().getThreading().isInternalTimerEnabled());
        assertEquals(1234567, config.getEngineDefaults().getThreading().getInternalTimerMsecResolution());
        assertFalse(config.getEngineDefaults().getViewResources().isShareViews());
        assertEquals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE, config.getEngineDefaults().getEventMeta().getClassPropertyResolutionStyle());
        assertTrue(config.getEngineDefaults().getLogging().isEnableExecutionDebug());
    }
}