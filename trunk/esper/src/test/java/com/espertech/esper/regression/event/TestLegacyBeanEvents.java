package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Map;
import java.util.HashMap;

import junit.framework.TestCase;

public class TestLegacyBeanEvents extends TestCase
{
    private SupportLegacyBean legacyBean;
    private EPServiceProvider epService;

    protected void setUp()
    {
        Map<String, String> mappedProperty = new HashMap<String, String>();
        mappedProperty.put("key1", "value1");
        mappedProperty.put("key2", "value2");
        legacyBean = new SupportLegacyBean("leg", new String[] {"a", "b"}, mappedProperty, "nest");
    }

    public void testAddRemoveType()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        ConfigurationOperations configOps = epService.getEPAdministrator().getConfiguration();

        // test remove type with statement used (no force)
        configOps.addEventTypeAlias("MyBeanEvent", SupportBean_A.class);
        EPStatement stmt = epService.getEPAdministrator().createEPL("select id from MyBeanEvent", "stmtOne");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"stmtOne"}, configOps.getEventTypeAliasUsedBy("MyBeanEvent").toArray());

        try {
            configOps.removeEventType("MyBeanEvent", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyBeanEvent"));
        }

        // destroy statement and type
        stmt.destroy();
        assertTrue(configOps.getEventTypeAliasUsedBy("MyBeanEvent").isEmpty());
        assertTrue(configOps.isEventTypeAliasExists("MyBeanEvent"));
        assertTrue(configOps.removeEventType("MyBeanEvent", false));
        assertFalse(configOps.removeEventType("MyBeanEvent", false));    // try double-remove
        assertFalse(configOps.isEventTypeAliasExists("MyBeanEvent"));
        try {
            epService.getEPAdministrator().createEPL("select id from MyBeanEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // add back the type
        configOps.addEventTypeAlias("MyBeanEvent", SupportBean.class);
        assertTrue(configOps.isEventTypeAliasExists("MyBeanEvent"));
        assertTrue(configOps.getEventTypeAliasUsedBy("MyBeanEvent").isEmpty());

        // compile
        epService.getEPAdministrator().createEPL("select boolPrimitive from MyBeanEvent", "stmtTwo");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"stmtTwo"}, configOps.getEventTypeAliasUsedBy("MyBeanEvent").toArray());
        try {
            epService.getEPAdministrator().createEPL("select id from MyBeanEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }

        // remove with force
        try {
            configOps.removeEventType("MyBeanEvent", false);
        }
        catch (ConfigurationException ex) {
            assertTrue(ex.getMessage().contains("MyBeanEvent"));
        }
        assertTrue(configOps.removeEventType("MyBeanEvent", true));
        assertFalse(configOps.isEventTypeAliasExists("MyBeanEvent"));
        assertTrue(configOps.getEventTypeAliasUsedBy("MyBeanEvent").isEmpty());

        // add back the type
        configOps.addEventTypeAlias("MyBeanEvent", SupportMarketDataBean.class);
        assertTrue(configOps.isEventTypeAliasExists("MyBeanEvent"));

        // compile
        epService.getEPAdministrator().createEPL("select feed from MyBeanEvent");
        try {
            epService.getEPAdministrator().createEPL("select boolPrimitive from MyBeanEvent");
            fail();
        }
        catch (EPException ex) {
            // expected
        }
    }

    public void testPublicAccessors()
    {
        tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGeneration.ENABLED);
    }

    public void testPublicAccessorsNoCodeGen()
    {
        tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
    }

    public void testExplicitOnly()
    {
        tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGeneration.ENABLED);
    }

    public void testExplicitOnlyNoCodeGen()
    {
        tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
    }

    public void testJavaBeanAccessor()
    {
        tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGeneration.ENABLED);
    }

    public void testJavaBeanAccessorNoCodeGen()
    {
        tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
    }

    public void testFinalClass()
    {
        tryFinalClass(ConfigurationEventTypeLegacy.CodeGeneration.ENABLED);
    }

    public void testFinalClassNoCodeGen()
    {
        tryFinalClass(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
    }

    private void tryPublicAccessors(ConfigurationEventTypeLegacy.CodeGeneration codeGeneration)
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC);
        legacyDef.setCodeGeneration(codeGeneration);
        legacyDef.addFieldProperty("explicitFSimple", "fieldLegacyVal");
        legacyDef.addFieldProperty("explicitFIndexed", "fieldStringArray");
        legacyDef.addFieldProperty("explicitFNested", "fieldNested");
        legacyDef.addMethodProperty("explicitMSimple", "readLegacyBeanVal");
        legacyDef.addMethodProperty("explicitMArray", "readStringArray");
        legacyDef.addMethodProperty("explicitMIndexed", "readStringIndexed");
        legacyDef.addMethodProperty("explicitMMapped", "readMapByKey");
        config.addEventTypeAlias("MyLegacyEvent", SupportLegacyBean.class.getName(), legacyDef);

        legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC);
        legacyDef.setCodeGeneration(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
        config.addEventTypeAlias("MyLegacyNestedEvent", SupportLegacyBean.LegacyNested.class.getName(), legacyDef);

        epService = EPServiceProviderManager.getProvider(this.getClass().getName() + ".test1" + codeGeneration, config);
        epService.initialize();

        // assert type metadata
        EventTypeSPI type = (EventTypeSPI) ((EPServiceProviderSPI)epService).getEventAdapterService().getExistsTypeByAlias("MyLegacyEvent");
        assertEquals(EventTypeMetadata.ApplicationType.CLASS, type.getMetadata().getOptionalApplicationType());
        assertEquals(1, type.getMetadata().getOptionalSecondaryNames().size());
        assertEquals(SupportLegacyBean.class.getName(), type.getMetadata().getOptionalSecondaryNames().iterator().next());
        assertEquals("MyLegacyEvent", type.getMetadata().getPrimaryName());
        assertEquals("MyLegacyEvent", type.getMetadata().getPublicName());
        assertEquals(EventTypeMetadata.TypeClass.APPLICATION, type.getMetadata().getTypeClass());
        assertEquals(true, type.getMetadata().isApplicationConfigured());

        String statementText = "select " +
                    "fieldLegacyVal as fieldSimple," +
                    "fieldStringArray as fieldArr," +
                    "fieldStringArray[1] as fieldArrIndexed," +
                    "fieldMapped as fieldMap," +
                    "fieldNested as fieldNested," +
                    "fieldNested.readNestedValue as fieldNestedVal," +
                    "readLegacyBeanVal as simple," +
                    "readLegacyNested as nestedObject," +
                    "readLegacyNested.readNestedValue as nested," +
                    "readStringArray[0] as array," +
                    "readStringIndexed[1] as indexed," +
                    "readMapByKey('key1') as mapped," +
                    "readMap as mapItself," +
                    "explicitFSimple, " +
                    "explicitFIndexed[0], " +
                    "explicitFNested, " +
                    "explicitMSimple, " +
                    "explicitMArray[0], " +
                    "explicitMIndexed[1], " +
                    "explicitMMapped('key2')" +
                    " from MyLegacyEvent.win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        EventType eventType = statement.getEventType();
        assertEquals(String.class, eventType.getPropertyType("fieldSimple"));
        assertEquals(String[].class, eventType.getPropertyType("fieldArr"));
        assertEquals(String.class, eventType.getPropertyType("fieldArrIndexed"));
        assertEquals(Map.class, eventType.getPropertyType("fieldMap"));
        assertEquals(SupportLegacyBean.LegacyNested.class, eventType.getPropertyType("fieldNested"));
        assertEquals(String.class, eventType.getPropertyType("fieldNestedVal"));
        assertEquals(String.class, eventType.getPropertyType("simple"));
        assertEquals(SupportLegacyBean.LegacyNested.class, eventType.getPropertyType("nestedObject"));
        assertEquals(String.class, eventType.getPropertyType("nested"));
        assertEquals(String.class, eventType.getPropertyType("array"));
        assertEquals(String.class, eventType.getPropertyType("indexed"));
        assertEquals(String.class, eventType.getPropertyType("mapped"));
        assertEquals(String.class, eventType.getPropertyType("explicitFSimple"));
        assertEquals(String.class, eventType.getPropertyType("explicitFIndexed[0]"));
        assertEquals(SupportLegacyBean.LegacyNested.class, eventType.getPropertyType("explicitFNested"));
        assertEquals(String.class, eventType.getPropertyType("explicitMSimple"));
        assertEquals(String.class, eventType.getPropertyType("explicitMArray[0]"));
        assertEquals(String.class, eventType.getPropertyType("explicitMIndexed[1]"));
        assertEquals(String.class, eventType.getPropertyType("explicitMMapped('key2')"));

        epService.getEPRuntime().sendEvent(legacyBean);

        assertEquals(legacyBean.fieldLegacyVal, listener.getLastNewData()[0].get("fieldSimple"));
        assertEquals(legacyBean.fieldStringArray, listener.getLastNewData()[0].get("fieldArr"));
        assertEquals(legacyBean.fieldStringArray[1], listener.getLastNewData()[0].get("fieldArrIndexed"));
        assertEquals(legacyBean.fieldMapped, listener.getLastNewData()[0].get("fieldMap"));
        assertEquals(legacyBean.fieldNested, listener.getLastNewData()[0].get("fieldNested"));
        assertEquals(legacyBean.fieldNested.readNestedValue(), listener.getLastNewData()[0].get("fieldNestedVal"));

        assertEquals(legacyBean.readLegacyBeanVal(), listener.getLastNewData()[0].get("simple"));
        assertEquals(legacyBean.readLegacyNested(), listener.getLastNewData()[0].get("nestedObject"));
        assertEquals(legacyBean.readLegacyNested().readNestedValue(), listener.getLastNewData()[0].get("nested"));
        assertEquals(legacyBean.readStringIndexed(0), listener.getLastNewData()[0].get("array"));
        assertEquals(legacyBean.readStringIndexed(1), listener.getLastNewData()[0].get("indexed"));
        assertEquals(legacyBean.readMapByKey("key1"), listener.getLastNewData()[0].get("mapped"));
        assertEquals(legacyBean.readMap(), listener.getLastNewData()[0].get("mapItself"));

        assertEquals(legacyBean.readLegacyBeanVal(), listener.getLastNewData()[0].get("explicitFSimple"));
        assertEquals(legacyBean.readLegacyBeanVal(), listener.getLastNewData()[0].get("explicitMSimple"));
        assertEquals(legacyBean.readLegacyNested(), listener.getLastNewData()[0].get("explicitFNested"));
        assertEquals(legacyBean.readStringIndexed(0), listener.getLastNewData()[0].get("explicitFIndexed[0]"));
        assertEquals(legacyBean.readStringIndexed(0), listener.getLastNewData()[0].get("explicitMArray[0]"));
        assertEquals(legacyBean.readStringIndexed(1), listener.getLastNewData()[0].get("explicitMIndexed[1]"));
        assertEquals(legacyBean.readMapByKey("key2"), listener.getLastNewData()[0].get("explicitMMapped('key2')"));

        EventTypeSPI stmtType = (EventTypeSPI) statement.getEventType();
        assertEquals(null, stmtType.getMetadata().getOptionalApplicationType());
        assertEquals(null, stmtType.getMetadata().getOptionalSecondaryNames());
        assertNotNull(stmtType.getMetadata().getPrimaryName());
        assertNull(stmtType.getMetadata().getPublicName());
        assertNull(stmtType.getName());
        assertEquals(EventTypeMetadata.TypeClass.ANONYMOUS, stmtType.getMetadata().getTypeClass());
        assertEquals(false, stmtType.getMetadata().isApplicationConfigured());        
    }

    private void tryExplicitOnlyAccessors(ConfigurationEventTypeLegacy.CodeGeneration codeGeneration)
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.EXPLICIT);
        legacyDef.setCodeGeneration(codeGeneration);
        legacyDef.addFieldProperty("explicitFNested", "fieldNested");
        legacyDef.addMethodProperty("explicitMNested", "readLegacyNested");
        config.addEventTypeAlias("MyLegacyEvent", SupportLegacyBean.class.getName(), legacyDef);

        legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.EXPLICIT);
        legacyDef.setCodeGeneration(codeGeneration);
        legacyDef.addFieldProperty("fieldNestedClassValue", "fieldNestedValue");
        legacyDef.addMethodProperty("readNestedClassValue", "readNestedValue");
        config.addEventTypeAlias("MyLegacyNestedEvent", SupportLegacyBean.LegacyNested.class.getName(), legacyDef);

        legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.EXPLICIT);
        legacyDef.setCodeGeneration(codeGeneration);
        config.addEventTypeAlias("MySupportBean", SupportBean.class.getName(), legacyDef);

        epService = EPServiceProviderManager.getProvider(this.getClass().getName() + ".test2" + codeGeneration, config);
        epService.initialize();

        String statementText = "select " +
                    "explicitFNested.fieldNestedClassValue as fnested, " +
                    "explicitMNested.readNestedClassValue as mnested" +
                    " from MyLegacyEvent.win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        EventType eventType = statement.getEventType();
        assertEquals(String.class, eventType.getPropertyType("fnested"));
        assertEquals(String.class, eventType.getPropertyType("mnested"));

        epService.getEPRuntime().sendEvent(legacyBean);

        assertEquals(legacyBean.fieldNested.readNestedValue(), listener.getLastNewData()[0].get("fnested"));
        assertEquals(legacyBean.fieldNested.readNestedValue(), listener.getLastNewData()[0].get("mnested"));

        try
        {
            // invalid statement, JavaBean-style getters not exposed
            statementText = "select intPrimitive from MySupportBean.win:length(5)";
            epService.getEPAdministrator().createEPL(statementText);
        }
        catch (EPStatementException ex)
        {
            // expected
        }
    }

    public void tryJavaBeanAccessor(ConfigurationEventTypeLegacy.CodeGeneration codeGeneration)
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.JAVABEAN);
        legacyDef.setCodeGeneration(codeGeneration);
        legacyDef.addFieldProperty("explicitFInt", "fieldIntPrimitive");
        legacyDef.addMethodProperty("explicitMGetInt", "getIntPrimitive");
        legacyDef.addMethodProperty("explicitMReadInt", "readIntPrimitive");
        config.addEventTypeAlias("MyLegacyEvent", SupportLegacyBeanInt.class.getName(), legacyDef);

        epService = EPServiceProviderManager.getProvider(this.getClass().getName() + ".test3" + codeGeneration, config);
        epService.initialize();

        String statementText = "select intPrimitive, explicitFInt, explicitMGetInt, explicitMReadInt " +
                    " from MyLegacyEvent.win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);
        EventType eventType = statement.getEventType();

        SupportLegacyBeanInt event = new SupportLegacyBeanInt(10);
        epService.getEPRuntime().sendEvent(event);

        for (String name : new String[] {"intPrimitive", "explicitFInt", "explicitMGetInt", "explicitMReadInt"})
        {
            assertEquals(int.class, eventType.getPropertyType(name));
            assertEquals(10, listener.getLastNewData()[0].get(name));
        }
    }

    private void tryFinalClass(ConfigurationEventTypeLegacy.CodeGeneration codeGeneration)
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.JAVABEAN);
        legacyDef.setCodeGeneration(codeGeneration);
        config.addEventTypeAlias("MyFinalEvent", SupportBeanFinal.class.getName(), legacyDef);

        epService = EPServiceProviderManager.getProvider(this.getClass().getName() + ".test4" + codeGeneration, config);
        epService.initialize();

        String statementText = "select intPrimitive " +
                    "from " + SupportBeanFinal.class.getName() + ".win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        SupportBeanFinal event = new SupportBeanFinal(10);
        epService.getEPRuntime().sendEvent(event);
        assertEquals(10, listener.getLastNewData()[0].get("intPrimitive"));
    }
}
