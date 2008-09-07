package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestMapEvent extends TestCase
{
    private Properties properties;
    private Map<String, Object> map;
    private EPServiceProvider epService;

    protected void setUp()
    {
        properties = new Properties();
        properties.put("myInt", "int");
        properties.put("myString", "string");
        properties.put("beanA", SupportBeanComplexProps.class.getName());

        map = new HashMap<String, Object>();
        map.put("myInt", 3);
        map.put("myString", "some string");
        map.put("beanA", SupportBeanComplexProps.makeDefaultBean());

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("myMapEvent", properties);
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testNestedObjects()
    {
        String statementText = "select beanA.simpleProperty as simple," +
                    "beanA.nested.nestedValue as nested," +
                    "beanA.indexed[1] as indexed," +
                    "beanA.nested.nestedNested.nestedNestedValue as nestednested " +
                    "from myMapEvent.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(map, "myMapEvent");
        assertEquals("nestedValue", listener.getLastNewData()[0].get("nested"));
        assertEquals(2, listener.getLastNewData()[0].get("indexed"));
        assertEquals("nestedNestedValue", listener.getLastNewData()[0].get("nestednested"));
        statement.stop();
    }

    public void testQueryFields()
    {
        String statementText = "select myInt + 2 as intVal, 'x' || myString || 'x' as stringVal from myMapEvent.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        // send Map<String, Object> event
        epService.getEPRuntime().sendEvent(map, "myMapEvent");
        assertEquals(5, listener.getLastNewData()[0].get("intVal"));
        assertEquals("xsome stringx", listener.getLastNewData()[0].get("stringVal"));

        // send Map base event
        Map mapNoType = new HashMap();
        mapNoType.put("myInt", 4);
        mapNoType.put("myString", "string2");
        epService.getEPRuntime().sendEvent(mapNoType, "myMapEvent");
        assertEquals(6, listener.getLastNewData()[0].get("intVal"));
        assertEquals("xstring2x", listener.getLastNewData()[0].get("stringVal"));

        statement.stop();
    }

    public void testPrimitivesTypes()
    {
        properties = new Properties();
        properties.put("myInt", int.class.getName());
        properties.put("byteArr", byte[].class.getName());
        properties.put("myInt2", "int");
        properties.put("double", "double");
        properties.put("boolean", "boolean");
        properties.put("long", "long");
        properties.put("astring", "string");

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("MyPrimMapEvent", properties);
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getProvider("testPrimitivesTypes", configuration);
    }

    public void testInvalidConfig()
    {
        properties = new Properties();
        properties.put("astring", "XXXX");

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("MyInvalidEvent", properties);
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        try
        {
            epService = EPServiceProviderManager.getProvider("testInvalidConfig", configuration);
            fail();
        }
        catch (ConfigurationException ex)
        {
            log.debug(ex);
            // expected
        }
    }

    public void testInvalidStatement()
    {
        tryInvalid("select XXX from myMapEvent.win:length(5)");
        tryInvalid("select myString * 2 from myMapEvent.win:length(5)");
        tryInvalid("select String.trim(myInt) from myMapEvent.win:length(5)");
    }

    public void testSendMapNative()
    {
        String statementText = "select * from myMapEvent.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        // send Map<String, Object> event
        epService.getEPRuntime().sendEvent(map, "myMapEvent");

        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(map, listener.getLastNewData()[0].getUnderlying());
        assertEquals(3, listener.getLastNewData()[0].get("myInt"));
        assertEquals("some string", listener.getLastNewData()[0].get("myString"));

        // send Map base event
        Map mapNoType = new HashMap();
        mapNoType.put("myInt", 4);
        mapNoType.put("myString", "string2");
        epService.getEPRuntime().sendEvent(mapNoType, "myMapEvent");

        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(mapNoType, listener.getLastNewData()[0].getUnderlying());
        assertEquals(4, listener.getLastNewData()[0].get("myInt"));
        assertEquals("string2", listener.getLastNewData()[0].get("myString"));

        Map<String, Object> mapStrings = new HashMap<String, Object>();
        mapStrings.put("myInt", 5);
        mapStrings.put("myString", "string3");
        epService.getEPRuntime().sendEvent(mapStrings, "myMapEvent");

        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(5, listener.getLastNewData()[0].get("myInt"));
        assertEquals("string3", listener.getLastNewData()[0].get("myString"));
    }

    private void tryInvalid(String statementText)
    {
        try
        {
            epService.getEPAdministrator().createEPL(statementText);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (int i = 0; i < entries.length; i++)
        {
            result.put(entries[i][0], entries[i][1]);
        }
        return result;
    }

    private static Log log = LogFactory.getLog(TestMapEvent.class);
}
