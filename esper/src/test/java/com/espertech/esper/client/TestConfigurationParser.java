/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.client;

import com.espertech.esper.client.annotation.Name;
import com.espertech.esper.client.soda.StreamSelector;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.StringPatternSet;
import com.espertech.esper.type.StringPatternSetLike;
import com.espertech.esper.type.StringPatternSetRegex;
import junit.framework.TestCase;

import javax.xml.xpath.XPathConstants;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        assertEquals(100, config.getEngineDefaults().getThreading().getInsertIntoDispatchTimeout());
        assertTrue(config.getEngineDefaults().getThreading().isListenerDispatchPreserveOrder());
        assertEquals(1000, config.getEngineDefaults().getThreading().getListenerDispatchTimeout());
        assertTrue(config.getEngineDefaults().getThreading().isInternalTimerEnabled());
        assertEquals(100, config.getEngineDefaults().getThreading().getInternalTimerMsecResolution());
        assertEquals(ConfigurationEngineDefaults.Threading.Locking.SPIN, config.getEngineDefaults().getThreading().getInsertIntoDispatchLocking());
        assertEquals(ConfigurationEngineDefaults.Threading.Locking.SPIN, config.getEngineDefaults().getThreading().getListenerDispatchLocking());
        assertFalse(config.getEngineDefaults().getThreading().isThreadPoolInbound());
        assertFalse(config.getEngineDefaults().getThreading().isThreadPoolOutbound());
        assertFalse(config.getEngineDefaults().getThreading().isThreadPoolRouteExec());
        assertFalse(config.getEngineDefaults().getThreading().isThreadPoolTimerExec());
        assertEquals(2, config.getEngineDefaults().getThreading().getThreadPoolInboundNumThreads());
        assertEquals(2, config.getEngineDefaults().getThreading().getThreadPoolOutboundNumThreads());
        assertEquals(2, config.getEngineDefaults().getThreading().getThreadPoolRouteExecNumThreads());
        assertEquals(2, config.getEngineDefaults().getThreading().getThreadPoolTimerExecNumThreads());
        assertEquals(null, config.getEngineDefaults().getThreading().getThreadPoolInboundCapacity());
        assertEquals(null, config.getEngineDefaults().getThreading().getThreadPoolOutboundCapacity());
        assertEquals(null, config.getEngineDefaults().getThreading().getThreadPoolRouteExecCapacity());
        assertEquals(null, config.getEngineDefaults().getThreading().getThreadPoolTimerExecCapacity());

        assertEquals(Configuration.PropertyResolutionStyle.CASE_SENSITIVE, config.getEngineDefaults().getEventMeta().getClassPropertyResolutionStyle());
        assertEquals(ConfigurationEventTypeLegacy.AccessorStyle.JAVABEAN, config.getEngineDefaults().getEventMeta().getDefaultAccessorStyle());

        assertTrue(config.getEngineDefaults().getViewResources().isShareViews());
        assertFalse(config.getEngineDefaults().getViewResources().isAllowMultipleExpiryPolicies());
        assertFalse(config.getEngineDefaults().getLogging().isEnableExecutionDebug());
        assertTrue(config.getEngineDefaults().getLogging().isEnableTimerDebug());
        assertFalse(config.getEngineDefaults().getLogging().isEnableQueryPlan());
        assertFalse(config.getEngineDefaults().getLogging().isEnableJDBC());
        assertEquals(15000, config.getEngineDefaults().getVariables().getMsecVersionRelease());
        assertEquals(ConfigurationEngineDefaults.TimeSourceType.MILLI, config.getEngineDefaults().getTimeSource().getTimeSourceType());
        assertFalse(config.getEngineDefaults().getExecution().isPrioritized());
        assertFalse(config.getEngineDefaults().getExecution().isFairlock());

        assertEquals(StreamSelector.ISTREAM_ONLY, config.getEngineDefaults().getStreamSelection().getDefaultStreamSelector());
        assertFalse(config.getEngineDefaults().getLanguage().isSortUsingCollator());
        assertFalse(config.getEngineDefaults().getExpression().isIntegerDivision());
        assertFalse(config.getEngineDefaults().getExpression().isDivisionByZeroReturnsNull());
        assertTrue(config.getEngineDefaults().getExpression().isSelfSubselectPreeval());
        assertTrue(config.getEngineDefaults().getExpression().isUdfCache());
        assertTrue(config.getEngineDefaults().getExpression().isExtendedAggregation());
        assertFalse(config.getEngineDefaults().getExpression().isDuckTyping());
        assertNull(config.getEngineDefaults().getExceptionHandling().getHandlerFactories());
        assertNull(config.getEngineDefaults().getConditionHandling().getHandlerFactories());

        ConfigurationEventTypeXMLDOM domType = new ConfigurationEventTypeXMLDOM();
        assertFalse(domType.isXPathPropertyExpr());
        assertTrue(domType.isXPathResolvePropertiesAbsolute());
        assertTrue(domType.isEventSenderValidatesRoot());
        assertTrue(domType.isAutoFragment());
    }

    protected static void assertFileConfig(Configuration config) throws Exception
    {
        // assert name for class
        assertEquals(2, config.getEventTypeAutoNamePackages().size());
        assertEquals("com.mycompany.eventsone", config.getEventTypeAutoNamePackages().toArray()[0]);
        assertEquals("com.mycompany.eventstwo", config.getEventTypeAutoNamePackages().toArray()[1]);

        // assert name for class
        assertEquals(3, config.getEventTypeNames().size());
        assertEquals("com.mycompany.myapp.MySampleEventOne", config.getEventTypeNames().get("MySampleEventOne"));
        assertEquals("com.mycompany.myapp.MySampleEventTwo", config.getEventTypeNames().get("MySampleEventTwo"));
        assertEquals("com.mycompany.package.MyLegacyTypeEvent", config.getEventTypeNames().get("MyLegacyTypeEvent"));

        // assert auto imports
        assertEquals(3, config.getImports().size());
        assertEquals(Name.class.getPackage().getName() + ".*", config.getImports().get(0));
        assertEquals("com.mycompany.myapp.*", config.getImports().get(1));
        assertEquals("com.mycompany.myapp.ClassOne", config.getImports().get(2));

        // assert XML DOM - no schema
        assertEquals(2, config.getEventTypesXMLDOM().size());
        ConfigurationEventTypeXMLDOM noSchemaDesc = config.getEventTypesXMLDOM().get("MyNoSchemaXMLEventName");
        assertEquals("MyNoSchemaEvent", noSchemaDesc.getRootElementName());
        assertEquals("/myevent/element1", noSchemaDesc.getXPathProperties().get("element1").getXpath());
        assertEquals(XPathConstants.NUMBER, noSchemaDesc.getXPathProperties().get("element1").getType());
        assertEquals(null, noSchemaDesc.getXPathProperties().get("element1").getOptionalCastToType());
        assertNull(noSchemaDesc.getXPathFunctionResolver());
        assertNull(noSchemaDesc.getXPathVariableResolver());
        assertFalse(noSchemaDesc.isXPathPropertyExpr());

        // assert XML DOM - with schema
        ConfigurationEventTypeXMLDOM schemaDesc = config.getEventTypesXMLDOM().get("MySchemaXMLEventName");
        assertEquals("MySchemaEvent", schemaDesc.getRootElementName());
        assertEquals("MySchemaXMLEvent.xsd", schemaDesc.getSchemaResource());
        assertEquals("actual-xsd-text-here", schemaDesc.getSchemaText());
        assertEquals("samples:schemas:simpleSchema", schemaDesc.getRootElementNamespace());
        assertEquals("default-name-space", schemaDesc.getDefaultNamespace());
        assertEquals("/myevent/element2", schemaDesc.getXPathProperties().get("element2").getXpath());
        assertEquals(XPathConstants.STRING, schemaDesc.getXPathProperties().get("element2").getType());
        assertEquals(Long.class, schemaDesc.getXPathProperties().get("element2").getOptionalCastToType());
        assertEquals("/bookstore/book", schemaDesc.getXPathProperties().get("element3").getXpath());
        assertEquals(XPathConstants.NODESET, schemaDesc.getXPathProperties().get("element3").getType());
        assertEquals(null, schemaDesc.getXPathProperties().get("element3").getOptionalCastToType());
        assertEquals("MyOtherXMLNodeEvent", schemaDesc.getXPathProperties().get("element3").getOptionaleventTypeName());
        assertEquals(1, schemaDesc.getNamespacePrefixes().size());
        assertEquals("samples:schemas:simpleSchema", schemaDesc.getNamespacePrefixes().get("ss"));
        assertFalse(schemaDesc.isXPathResolvePropertiesAbsolute());
        assertEquals("com.mycompany.OptionalFunctionResolver", schemaDesc.getXPathFunctionResolver());
        assertEquals("com.mycompany.OptionalVariableResolver", schemaDesc.getXPathVariableResolver());
        assertTrue(schemaDesc.isXPathPropertyExpr());
        assertFalse(schemaDesc.isEventSenderValidatesRoot());
        assertFalse(schemaDesc.isAutoFragment());
        assertEquals("startts", schemaDesc.getStartTimestampPropertyName());
        assertEquals("endts", schemaDesc.getEndTimestampPropertyName());

        // assert mapped events
        assertEquals(1, config.getEventTypesMapEvents().size());
        assertTrue(config.getEventTypesMapEvents().keySet().contains("MyMapEvent"));
        Map<String, String> expectedProps = new HashMap<String, String>();
        expectedProps.put("myInt", "int");
        expectedProps.put("myString", "string");
        assertEquals(expectedProps, config.getEventTypesMapEvents().get("MyMapEvent"));
        assertEquals(1, config.getMapTypeConfigurations().size());
        Set<String> superTypes = config.getMapTypeConfigurations().get("MyMapEvent").getSuperTypes();
        ArrayAssertionUtil.assertEqualsExactOrder(superTypes.toArray(), new Object[] {"MyMapSuperType1", "MyMapSuperType2"});
        assertEquals("startts", config.getMapTypeConfigurations().get("MyMapEvent").getStartTimestampPropertyName());
        assertEquals("endts", config.getMapTypeConfigurations().get("MyMapEvent").getEndTimestampPropertyName());

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
        assertEquals("com.mycompany.myapp.MySampleEventFactory.createMyLegacyTypeEvent", legacy.getFactoryMethod());
        assertEquals("myCopyMethod", legacy.getCopyMethod());
        assertEquals("startts", legacy.getStartTimestampPropertyName());
        assertEquals("endts", legacy.getEndTimestampPropertyName());

        // assert database reference - data source config
        assertEquals(3, config.getDatabaseReferences().size());
        ConfigurationDBRef configDBRef = config.getDatabaseReferences().get("mydb1");
        ConfigurationDBRef.DataSourceConnection dsDef = (ConfigurationDBRef.DataSourceConnection) configDBRef.getConnectionFactoryDesc();
        assertEquals("java:comp/env/jdbc/mydb", dsDef.getContextLookupName());
        assertEquals("{java.naming.provider.url=iiop://localhost:1050, java.naming.factory.initial=com.myclass.CtxFactory}", dsDef.getEnvProperties().toString());
        assertEquals(ConfigurationDBRef.ConnectionLifecycleEnum.POOLED, configDBRef.getConnectionLifecycleEnum());
        assertNull(configDBRef.getConnectionSettings().getAutoCommit());
        assertNull(configDBRef.getConnectionSettings().getCatalog());
        assertNull(configDBRef.getConnectionSettings().getReadOnly());
        assertNull(configDBRef.getConnectionSettings().getTransactionIsolation());
        ConfigurationLRUCache lruCache = (ConfigurationLRUCache) configDBRef.getDataCacheDesc();
        assertEquals(10, lruCache.getSize());
        assertEquals(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE, configDBRef.getColumnChangeCase());
        assertEquals(ConfigurationDBRef.MetadataOriginEnum.SAMPLE, configDBRef.getMetadataRetrievalEnum());
        assertEquals(2, configDBRef.getSqlTypesMapping().size());
        assertEquals("int", configDBRef.getSqlTypesMapping().get(2));
        assertEquals("float", configDBRef.getSqlTypesMapping().get(6));

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
        ConfigurationExpiryTimeCache expCache = (ConfigurationExpiryTimeCache) configDBRef.getDataCacheDesc();
        assertEquals(60.5, expCache.getMaxAgeSeconds());
        assertEquals(120.1, expCache.getPurgeIntervalSeconds());
        assertEquals(ConfigurationCacheReferenceType.HARD, expCache.getCacheReferenceType());
        assertEquals(ConfigurationDBRef.ColumnChangeCaseEnum.UPPERCASE, configDBRef.getColumnChangeCase());
        assertEquals(ConfigurationDBRef.MetadataOriginEnum.METADATA, configDBRef.getMetadataRetrievalEnum());
        assertEquals(1, configDBRef.getSqlTypesMapping().size());
        assertEquals("java.lang.String", configDBRef.getSqlTypesMapping().get(99));

        // assert database reference - data source factory and DBCP config
        configDBRef = config.getDatabaseReferences().get("mydb3");
        ConfigurationDBRef.DataSourceFactory dsFactory = (ConfigurationDBRef.DataSourceFactory) configDBRef.getConnectionFactoryDesc();
        assertEquals("org.apache.commons.dbcp.BasicDataSourceFactory", dsFactory.getFactoryClassname());
        assertEquals("jdbc:mysql://localhost/test", dsFactory.getProperties().getProperty("url"));
        assertEquals("myusername", dsFactory.getProperties().getProperty("username"));
        assertEquals("mypassword", dsFactory.getProperties().getProperty("password"));
        assertEquals("com.mysql.jdbc.Driver", dsFactory.getProperties().getProperty("driverClassName"));
        assertEquals("2", dsFactory.getProperties().getProperty("initialSize"));

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

        // assert custom virtual data window implementations
        List<ConfigurationPlugInVirtualDataWindow> configVDW = config.getPlugInVirtualDataWindows();
        assertEquals(2, configVDW.size());
        for (int i = 0; i < configVDW.size(); i++)
        {
            ConfigurationPlugInVirtualDataWindow entry = configVDW.get(i);
            assertEquals("vdw" + i, entry.getNamespace());
            assertEquals("myvdw" + i, entry.getName());
            assertEquals("com.mycompany.MyVdwFactory" + i, entry.getFactoryClassName());
            if (i == 1) {
                assertEquals("abc", entry.getConfig());
            }
        }

        // assert adapter loaders parsed
        List<ConfigurationPluginLoader> plugins = config.getPluginLoaders();
        assertEquals(2, plugins.size());
        ConfigurationPluginLoader pluginOne = plugins.get(0);
        assertEquals("Loader1", pluginOne.getLoaderName());
        assertEquals("com.espertech.esper.support.plugin.SupportLoaderOne", pluginOne.getClassName());
        assertEquals(2, pluginOne.getConfigProperties().size());
        assertEquals("val1", pluginOne.getConfigProperties().get("name1"));
        assertEquals("val2", pluginOne.getConfigProperties().get("name2"));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><sample-initializer><some-any-xml-can-be-here>This section for use by a plugin loader.</some-any-xml-can-be-here></sample-initializer>", pluginOne.getConfigurationXML());

        ConfigurationPluginLoader pluginTwo = plugins.get(1);
        assertEquals("Loader2", pluginTwo.getLoaderName());
        assertEquals("com.espertech.esper.support.plugin.SupportLoaderTwo", pluginTwo.getClassName());
        assertEquals(0, pluginTwo.getConfigProperties().size());

        // assert plug-in aggregation function loaded
        assertEquals(2, config.getPlugInAggregationFunctions().size());
        ConfigurationPlugInAggregationFunction pluginAgg = config.getPlugInAggregationFunctions().get(0);
        assertEquals("com.mycompany.MyMatrixAggregationMethod0", pluginAgg.getFunctionClassName());
        assertEquals("func1", pluginAgg.getName());
        pluginAgg = config.getPlugInAggregationFunctions().get(1);
        assertEquals("com.mycompany.MyMatrixAggregationMethod1", pluginAgg.getFunctionClassName());
        assertEquals("func2", pluginAgg.getName());

        // assert plug-in singlerow function loaded
        assertEquals(2, config.getPlugInSingleRowFunctions().size());
        ConfigurationPlugInSingleRowFunction pluginSingleRow = config.getPlugInSingleRowFunctions().get(0);
        assertEquals("com.mycompany.MyMatrixSingleRowMethod0", pluginSingleRow.getFunctionClassName());
        assertEquals("method1", pluginSingleRow.getFunctionMethodName());
        assertEquals("func3", pluginSingleRow.getName());
        pluginSingleRow = config.getPlugInSingleRowFunctions().get(1);
        assertEquals("com.mycompany.MyMatrixSingleRowMethod1", pluginSingleRow.getFunctionClassName());
        assertEquals("func4", pluginSingleRow.getName());
        assertEquals("method2", pluginSingleRow.getFunctionMethodName());

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
        assertEquals(3000, config.getEngineDefaults().getThreading().getInsertIntoDispatchTimeout());
        assertEquals(ConfigurationEngineDefaults.Threading.Locking.SUSPEND, config.getEngineDefaults().getThreading().getInsertIntoDispatchLocking());

        assertFalse(config.getEngineDefaults().getThreading().isListenerDispatchPreserveOrder());
        assertEquals(2000, config.getEngineDefaults().getThreading().getListenerDispatchTimeout());
        assertEquals(ConfigurationEngineDefaults.Threading.Locking.SUSPEND, config.getEngineDefaults().getThreading().getListenerDispatchLocking());
        assertTrue(config.getEngineDefaults().getThreading().isThreadPoolInbound());
        assertTrue(config.getEngineDefaults().getThreading().isThreadPoolOutbound());
        assertTrue(config.getEngineDefaults().getThreading().isThreadPoolRouteExec());
        assertTrue(config.getEngineDefaults().getThreading().isThreadPoolTimerExec());
        assertEquals(1, config.getEngineDefaults().getThreading().getThreadPoolInboundNumThreads());
        assertEquals(2, config.getEngineDefaults().getThreading().getThreadPoolOutboundNumThreads());
        assertEquals(3, config.getEngineDefaults().getThreading().getThreadPoolTimerExecNumThreads());
        assertEquals(4, config.getEngineDefaults().getThreading().getThreadPoolRouteExecNumThreads());
        assertEquals(1000, (int) config.getEngineDefaults().getThreading().getThreadPoolInboundCapacity());
        assertEquals(1500, (int) config.getEngineDefaults().getThreading().getThreadPoolOutboundCapacity());
        assertEquals(null, config.getEngineDefaults().getThreading().getThreadPoolTimerExecCapacity());
        assertEquals(2000, (int) config.getEngineDefaults().getThreading().getThreadPoolRouteExecCapacity());

        assertFalse(config.getEngineDefaults().getThreading().isInternalTimerEnabled());
        assertEquals(1234567, config.getEngineDefaults().getThreading().getInternalTimerMsecResolution());
        assertFalse(config.getEngineDefaults().getViewResources().isShareViews());
        assertTrue(config.getEngineDefaults().getViewResources().isAllowMultipleExpiryPolicies());
        assertEquals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE, config.getEngineDefaults().getEventMeta().getClassPropertyResolutionStyle());
        assertEquals(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC, config.getEngineDefaults().getEventMeta().getDefaultAccessorStyle());
        assertTrue(config.getEngineDefaults().getLogging().isEnableExecutionDebug());
        assertFalse(config.getEngineDefaults().getLogging().isEnableTimerDebug());
        assertTrue(config.getEngineDefaults().getLogging().isEnableQueryPlan());
        assertTrue(config.getEngineDefaults().getLogging().isEnableJDBC());
        assertEquals(30000, config.getEngineDefaults().getVariables().getMsecVersionRelease());
        assertEquals(StreamSelector.RSTREAM_ISTREAM_BOTH, config.getEngineDefaults().getStreamSelection().getDefaultStreamSelector());

        assertEquals(ConfigurationEngineDefaults.TimeSourceType.NANO, config.getEngineDefaults().getTimeSource().getTimeSourceType());
        assertTrue(config.getEngineDefaults().getExecution().isPrioritized());
        assertTrue(config.getEngineDefaults().getExecution().isFairlock());

        ConfigurationMetricsReporting metrics = config.getEngineDefaults().getMetricsReporting();
        assertTrue(metrics.isEnableMetricsReporting());
        assertEquals(4000L, metrics.getEngineInterval());
        assertEquals(500L, metrics.getStatementInterval());
        assertFalse(metrics.isThreading());
        assertEquals(2, metrics.getStatementGroups().size());
        ConfigurationMetricsReporting.StmtGroupMetrics def = metrics.getStatementGroups().get("MyStmtGroup");
        assertEquals(5000, def.getInterval());
        assertTrue(def.isDefaultInclude());
        assertEquals(50, def.getNumStatements());
        assertTrue(def.isReportInactive());
        assertEquals(5, def.getPatterns().size());
        assertEquals(def.getPatterns().get(0), new Pair<StringPatternSet, Boolean>(new StringPatternSetRegex(".*"), true));
        assertEquals(def.getPatterns().get(1), new Pair<StringPatternSet, Boolean>(new StringPatternSetRegex(".*test.*"), false));
        assertEquals(def.getPatterns().get(2), new Pair<StringPatternSet, Boolean>(new StringPatternSetLike("%MyMetricsStatement%"), false));
        assertEquals(def.getPatterns().get(3), new Pair<StringPatternSet, Boolean>(new StringPatternSetLike("%MyFraudAnalysisStatement%"), true));
        assertEquals(def.getPatterns().get(4), new Pair<StringPatternSet, Boolean>(new StringPatternSetLike("%SomerOtherStatement%"), true));
        def = metrics.getStatementGroups().get("MyStmtGroupTwo");
        assertEquals(200, def.getInterval());
        assertFalse(def.isDefaultInclude());
        assertEquals(100, def.getNumStatements());
        assertFalse(def.isReportInactive());
        assertEquals(0, def.getPatterns().size());
        assertTrue(config.getEngineDefaults().getLanguage().isSortUsingCollator());
        assertTrue(config.getEngineDefaults().getExpression().isIntegerDivision());
        assertTrue(config.getEngineDefaults().getExpression().isDivisionByZeroReturnsNull());
        assertFalse(config.getEngineDefaults().getExpression().isSelfSubselectPreeval());
        assertFalse(config.getEngineDefaults().getExpression().isUdfCache());
        assertFalse(config.getEngineDefaults().getExpression().isExtendedAggregation());
        assertTrue(config.getEngineDefaults().getExpression().isDuckTyping());
        assertEquals(2, config.getEngineDefaults().getExceptionHandling().getHandlerFactories().size());
        assertEquals("my.company.cep.LoggingExceptionHandlerFactory", config.getEngineDefaults().getExceptionHandling().getHandlerFactories().get(0));
        assertEquals("my.company.cep.AlertExceptionHandlerFactory", config.getEngineDefaults().getExceptionHandling().getHandlerFactories().get(1));
        assertEquals(2, config.getEngineDefaults().getConditionHandling().getHandlerFactories().size());
        assertEquals("my.company.cep.LoggingConditionHandlerFactory", config.getEngineDefaults().getConditionHandling().getHandlerFactories().get(0));
        assertEquals("my.company.cep.AlertConditionHandlerFactory", config.getEngineDefaults().getConditionHandling().getHandlerFactories().get(1));

        // variables
        assertEquals(2, config.getVariables().size());
        ConfigurationVariable variable = config.getVariables().get("var1");
        assertEquals(Integer.class.getName(), variable.getType());
        assertEquals("1", variable.getInitializationValue());
        variable = config.getVariables().get("var2");
        assertEquals(String.class.getName(), variable.getType());
        assertEquals(null, variable.getInitializationValue());

        // method references
        assertEquals(2, config.getMethodInvocationReferences().size());
        ConfigurationMethodRef ref = config.getMethodInvocationReferences().get("abc");
        expCache = (ConfigurationExpiryTimeCache) ref.getDataCacheDesc();
        assertEquals(91.0, expCache.getMaxAgeSeconds());
        assertEquals(92.2, expCache.getPurgeIntervalSeconds());
        assertEquals(ConfigurationCacheReferenceType.WEAK, expCache.getCacheReferenceType());

        ref = config.getMethodInvocationReferences().get("def");
        lruCache = (ConfigurationLRUCache) ref.getDataCacheDesc();
        assertEquals(20, lruCache.getSize());

        // plug-in event representations
        assertEquals(2, config.getPlugInEventRepresentation().size());
        ConfigurationPlugInEventRepresentation rep = config.getPlugInEventRepresentation().get(new URI("type://format/rep/name"));
        assertEquals("com.mycompany.MyPlugInEventRepresentation", rep.getEventRepresentationClassName());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><anyxml>test string event rep init</anyxml>", rep.getInitializer());
        rep = config.getPlugInEventRepresentation().get(new URI("type://format/rep/name2"));
        assertEquals("com.mycompany.MyPlugInEventRepresentation2", rep.getEventRepresentationClassName());
        assertEquals(null, rep.getInitializer());

        // plug-in event types
        assertEquals(2, config.getPlugInEventTypes().size());
        ConfigurationPlugInEventType type = config.getPlugInEventTypes().get("MyEvent");
        assertEquals(2, type.getEventRepresentationResolutionURIs().length);
        assertEquals("type://format/rep", type.getEventRepresentationResolutionURIs()[0].toString());
        assertEquals("type://format/rep2", type.getEventRepresentationResolutionURIs()[1].toString());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><anyxml>test string event type init</anyxml>", type.getInitializer());
        type = config.getPlugInEventTypes().get("MyEvent2");
        assertEquals(1, type.getEventRepresentationResolutionURIs().length);
        assertEquals("type://format/rep2", type.getEventRepresentationResolutionURIs()[0].toString());
        assertEquals(null, type.getInitializer());

        // plug-in event representation resolution URIs when using a new name in a statement
        assertEquals(2, config.getPlugInEventTypeResolutionURIs().length);
        assertEquals("type://format/rep", config.getPlugInEventTypeResolutionURIs()[0].toString());
        assertEquals("type://format/rep2", config.getPlugInEventTypeResolutionURIs()[1].toString());

        // revision types
        assertEquals(1, config.getRevisionEventTypes().size());
        ConfigurationRevisionEventType configRev = config.getRevisionEventTypes().get("MyRevisionEvent");
        assertEquals(1, configRev.getNameBaseEventTypes().size());
        assertTrue(configRev.getNameBaseEventTypes().contains("MyBaseEventName"));
        assertTrue(configRev.getNameDeltaEventTypes().contains("MyDeltaEventNameOne"));
        assertTrue(configRev.getNameDeltaEventTypes().contains("MyDeltaEventNameTwo"));
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"id", "id2"}, configRev.getKeyPropertyNames());
        assertEquals(ConfigurationRevisionEventType.PropertyRevision.MERGE_NON_NULL, configRev.getPropertyRevision());

        // variance types
        assertEquals(1, config.getVariantStreams().size());
        ConfigurationVariantStream configVStream = config.getVariantStreams().get("MyVariantStream");
        assertEquals(2, configVStream.getVariantTypeNames().size());
        assertTrue(configVStream.getVariantTypeNames().contains("MyEvenTypetNameOne"));
        assertTrue(configVStream.getVariantTypeNames().contains("MyEvenTypetNameTwo"));
        assertEquals(ConfigurationVariantStream.TypeVariance.ANY, configVStream.getTypeVariance());
    }
}
