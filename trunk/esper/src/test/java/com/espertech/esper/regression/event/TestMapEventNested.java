package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class TestMapEventNested extends TestCase
{
    public void testEscapedDot()
    {
        Map<String, Object> definition = makeMap(new Object[][] {
                {"a.b", int.class},
                {"a.b.c", int.class},
                {"nes.", int.class},
                {"nes.nes2", makeMap(new Object[][] {{"x.y", int.class}}) }
        });
        EPServiceProvider epService = getEngineInitialized("DotMap", definition);

        String statementText = "select a\\.b, a\\.b\\.c, nes\\., nes\\.nes2.x\\.y from DotMap";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Map<String, Object> data = makeMap(new Object[][] {
                {"a.b", 10},
                {"a.b.c", 20},
                {"nes.", 30},
                {"nes.nes2", makeMap(new Object[][] {{"x.y", 40}}) }
        });
        epService.getEPRuntime().sendEvent(data, "DotMap");

        String[] fields = "a.b,a.b.c,nes.,nes.nes2.x.y".split(",");
        EventBean received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {10, 20, 30, 40});
    }

    public void testNestedMapRuntime()
    {
        EPServiceProvider epService = getEngineInitialized(null, null);
        epService.getEPAdministrator().getConfiguration().addNestableEventTypeAlias("NestedMap", getTestDefinition());
        runAssertion(epService);
    }

    public void testNestedConfigEngine()
    {
        EPServiceProvider epService = getEngineInitialized("NestedMap", getTestDefinition());
        runAssertion(epService);
    }

    public void testInsertInto()
    {
        EPServiceProvider epService = getEngineInitialized("NestedMap", getTestDefTwo());

        String statementText = "insert into MyStream select " +
                                "map.mapOne as val1" +
                                " from NestedMap.win:length(5)";
        epService.getEPAdministrator().createEPL(statementText);

        statementText = "select val1 as a from MyStream";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Map<String, Object> testdata = getTestDataTwo();
        epService.getEPRuntime().sendEvent(testdata, "NestedMap");

        // test all properties exist
        String[] fields = "a".split(",");
        EventBean received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields, new Object[] {getNestedKey(testdata, "map", "mapOne")});
    }

    public void testAddIdenticalMapTypes()
    {
        EPServiceProvider epService = getEngineInitialized(null, null);

        Map<String, Object> levelOne_1 = makeMap(new Object[][] {{"simpleOne", Integer.class}});
        Map<String, Object> levelOne_2 = makeMap(new Object[][] {{"simpleOne", Long.class}});
        Map<String, Object> levelZero_1 = makeMap(new Object[][] {{"map", levelOne_1}});
        Map<String, Object> levelZero_2 = makeMap(new Object[][] {{"map", levelOne_2}});

        // can add the same nested type twice
        epService.getEPAdministrator().getConfiguration().addNestableEventTypeAlias("ABC", levelZero_1);
        epService.getEPAdministrator().getConfiguration().addNestableEventTypeAlias("ABC", levelZero_1);
        try
        {
            // changing the definition however stops the compatibility
            epService.getEPAdministrator().getConfiguration().addNestableEventTypeAlias("ABC", levelZero_2);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testNestedPojo()
    {
        EPServiceProvider epService = getEngineInitialized("NestedMap", getTestDefTwo());

        String statementText = "select " +
                                "simple, object, nodefmap, map, " +
                                "object.id as a1, nodefmap.key1? as a2, nodefmap.key2? as a3, nodefmap.key3?.key4 as a4, " +
                                "map.objectOne as b1, map.simpleOne as b2, map.nodefmapOne.key2? as b3, map.mapOne.simpleTwo? as b4, " +
                                "map.objectOne.indexed[1] as c1, map.objectOne.nested.nestedValue as c2," +
                                "map.mapOne.simpleTwo as d1, map.mapOne.objectTwo as d2, map.mapOne.nodefmapTwo as d3, " +
                                "map.mapOne.mapTwo as e1, map.mapOne.mapTwo.simpleThree as e2, map.mapOne.mapTwo.objectThree as e3, " +
                                "map.mapOne.objectTwo.array[1].mapped('1ma').value as f1, map.mapOne.mapTwo.objectThree.id as f2" +
                                " from NestedMap.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Map<String, Object> testdata = getTestDataTwo();
        epService.getEPRuntime().sendEvent(testdata, "NestedMap");

        // test all properties exist
        EventBean received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, "simple,object,nodefmap,map".split(","),
                new Object[] {"abc", new SupportBean_A("A1"), testdata.get("nodefmap"), testdata.get("map")});
        ArrayAssertionUtil.assertProps(received, "a1,a2,a3,a4".split(","),
                new Object[] {"A1", "val1", null, null});
        ArrayAssertionUtil.assertProps(received, "b1,b2,b3,b4".split(","),
                new Object[] {getNestedKey(testdata, "map", "objectOne"), 10, "val2", 300});
        ArrayAssertionUtil.assertProps(received, "c1,c2".split(","), new Object[] {2, "nestedValue"});
        ArrayAssertionUtil.assertProps(received, "d1,d2,d3".split(","),
                new Object[] {300, getNestedKey(testdata, "map", "mapOne", "objectTwo"),  getNestedKey(testdata, "map", "mapOne", "nodefmapTwo")});
        ArrayAssertionUtil.assertProps(received, "e1,e2,e3".split(","),
                new Object[] {getNestedKey(testdata, "map", "mapOne", "mapTwo"), 4000L, new SupportBean_B("B1")});
        ArrayAssertionUtil.assertProps(received, "f1,f2".split(","),
                new Object[] {"1ma0", "B1"});

        // test partial properties exist
        testdata = getTestDataThree();
        epService.getEPRuntime().sendEvent(testdata, "NestedMap");

        received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, "simple,object,nodefmap,map".split(","),
                new Object[] {"abc", new SupportBean_A("A1"), testdata.get("nodefmap"), testdata.get("map")});
        ArrayAssertionUtil.assertProps(received, "a1,a2,a3,a4".split(","),
                new Object[] {"A1", "val1", null, null});
        ArrayAssertionUtil.assertProps(received, "b1,b2,b3,b4".split(","),
                new Object[] {getNestedKey(testdata, "map", "objectOne"), null, null, null});
        ArrayAssertionUtil.assertProps(received, "c1,c2".split(","), new Object[] {null, null});
        ArrayAssertionUtil.assertProps(received, "d1,d2,d3".split(","),
                new Object[] {null, getNestedKey(testdata, "map", "mapOne", "objectTwo"),  getNestedKey(testdata, "map", "mapOne", "nodefmapTwo")});
        ArrayAssertionUtil.assertProps(received, "e1,e2,e3".split(","),
                new Object[] {getNestedKey(testdata, "map", "mapOne", "mapTwo"), 4000L, null});
        ArrayAssertionUtil.assertProps(received, "f1,f2".split(","),
                new Object[] {"1ma0", null});
    }

    public void testIsExists()
    {
        EPServiceProvider epService = getEngineInitialized("NestedMap", getTestDefTwo());

        String statementText = "select " +
                                "exists(map.mapOne?) as a," +
                                "exists(map.mapOne?.simpleOne) as b," +
                                "exists(map.mapOne?.simpleTwo) as c," +
                                "exists(map.mapOne?.mapTwo) as d," +
                                "exists(map.mapOne.mapTwo?) as e," +
                                "exists(map.mapOne.mapTwo.simpleThree?) as f," +
                                "exists(map.mapOne.mapTwo.objectThree?) as g " +
                                " from NestedMap.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Map<String, Object> testdata = getTestDataTwo();
        epService.getEPRuntime().sendEvent(testdata, "NestedMap");

        // test all properties exist
        String[] fields = "a,b,c,d,e,f,g".split(",");
        EventBean received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields,
                new Object[] {true, false, true, true, true, true, true});

        // test partial properties exist
        testdata = getTestDataThree();
        epService.getEPRuntime().sendEvent(testdata, "NestedMap");

        received = listener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, fields,
                new Object[] {true, false, false, true, true, true, false});
    }

    private void runAssertion(EPServiceProvider epService)
    {
        String statementText = "select nested as a, " +
                    "nested.n1 as b," +
                    "nested.n2 as c," +
                    "nested.n2.n1n1 as d " +
                    "from NestedMap.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        Map<String, Object> mapEvent = getTestData();
        epService.getEPRuntime().sendEvent(mapEvent, "NestedMap");
        
        EventBean event = listener.assertOneGetNewAndReset();
        assertSame(mapEvent.get("nested"), event.get("a"));
        assertSame("abc", event.get("b"));
        assertSame(((Map)mapEvent.get("nested")).get("n2"), event.get("c"));
        assertSame("def", event.get("d"));
        statement.stop();
    }

    public void testEventType()
    {
        EPServiceProvider epService = getEngineInitialized("NestedMap", getTestDefTwo());
        EPStatement stmt = epService.getEPAdministrator().createEPL(("select * from NestedMap"));
        EventType eventType = stmt.getEventType();
        
        String[] propertiesReceived = eventType.getPropertyNames();
        String[] propertiesExpected = new String[] {"simple", "object", "nodefmap", "map"};
        ArrayAssertionUtil.assertEqualsAnyOrder(propertiesReceived, propertiesExpected);
        assertEquals(String.class, eventType.getPropertyType("simple"));
        assertEquals(Map.class, eventType.getPropertyType("map"));
        assertEquals(Map.class, eventType.getPropertyType("nodefmap"));
        assertEquals(SupportBean_A.class, eventType.getPropertyType("object"));

        assertNull(eventType.getPropertyType("map.mapOne.simpleOne"));
    }

    public void testInvalidType()
    {
        EPServiceProvider epService = getEngineInitialized(null, null);

        Map<String, Object> invalid = makeMap(new Object[][] {{new SupportBean(), null} });
        tryInvalid(epService, invalid, "Invalid map type configuration: property name is not a String-type value");

        invalid = makeMap(new Object[][] {{"abc", new SupportBean()} });
        tryInvalid(epService, invalid, "Nestable map type configuration encountered an unexpected property type of 'SupportBean' for property 'abc', expected java.lang.Class or java.util.Map");
    }

    private void tryInvalid(EPServiceProvider epService, Map<String, Object> config, String message)
    {
        try
        {
            epService.getEPAdministrator().getConfiguration().addNestableEventTypeAlias("NestedMap", config);
            fail();
        }
        catch (Exception ex)
        {
            assertTrue("expected '" + message + "' but received '" + ex.getMessage(), ex.getMessage().contains(message));
        }
    }

    private Map<String, Object> getTestDefinition()
    {
        Map<String, Object> propertiesNestedNested = new HashMap<String, Object>();
        propertiesNestedNested.put("n1n1", String.class);

        Map<String, Object> propertiesNested = new HashMap<String, Object>();
        propertiesNested.put("n1", String.class);
        propertiesNested.put("n2", propertiesNestedNested);

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("nested", propertiesNested);

        return root;
    }

    private Map<String, Object> getTestData()
    {
        Map nestedNested = new HashMap<String, Object>();
        nestedNested.put("n1n1", "def");

        Map nested = new HashMap<String, Object>();
        nested.put("n1", "abc");
        nested.put("n2", nestedNested);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nested", nested);

        return map;
    }

    private Map<String, Object> getTestDefTwo()
    {
        Map<String, Object> levelThree= makeMap(new Object[][] {
                {"simpleThree", Long.class},
                {"objectThree", SupportBean_B.class},
        });

        Map<String, Object> levelTwo= makeMap(new Object[][] {
                {"simpleTwo", Integer.class},
                {"objectTwo", SupportBeanCombinedProps.class},
                {"nodefmapTwo", Map.class},
                {"mapTwo", levelThree},
        });

        Map<String, Object> levelOne = makeMap(new Object[][] {
                {"simpleOne", Integer.class},
                {"objectOne", SupportBeanComplexProps.class},
                {"nodefmapOne", Map.class},
                {"mapOne", levelTwo}
        });

        Map<String, Object> levelZero = makeMap(new Object[][] {
                {"simple", String.class},
                {"object", SupportBean_A.class},
                {"nodefmap", Map.class},
                {"map", levelOne}
        });

        return levelZero;
    }

    private Map<String, Object> getTestDataTwo()
    {
        Map<String, Object> levelThree = makeMap(new Object[][] {
                {"simpleThree", 4000L},
                {"objectThree", new SupportBean_B("B1")},
        });

        Map<String, Object> levelTwo = makeMap(new Object[][] {
                {"simpleTwo", 300},
                {"objectTwo", SupportBeanCombinedProps.makeDefaultBean()},
                {"nodefmapTwo", makeMap(new Object[][] {{"key3", "val3"}})},
                {"mapTwo", levelThree},
        });

        Map<String, Object> levelOne = makeMap(new Object[][] {
                {"simpleOne", 10},
                {"objectOne", SupportBeanComplexProps.makeDefaultBean()},
                {"nodefmapOne", makeMap(new Object[][] {{"key2", "val2"}})},
                {"mapOne", levelTwo}
        });

        Map<String, Object> levelZero = makeMap(new Object[][] {
                {"simple", "abc"},
                {"object", new SupportBean_A("A1")},
                {"nodefmap", makeMap(new Object[][] {{"key1", "val1"}})},
                {"map", levelOne}
        });

        return levelZero;
    }

    private Map<String, Object> getTestDataThree()
    {
        Map<String, Object> levelThree = makeMap(new Object[][] {
                {"simpleThree", 4000L},
        });

        Map<String, Object> levelTwo = makeMap(new Object[][] {
                {"objectTwo", SupportBeanCombinedProps.makeDefaultBean()},
                {"nodefmapTwo", makeMap(new Object[][] {{"key3", "val3"}})},
                {"mapTwo", levelThree},
        });

        Map<String, Object> levelOne = makeMap(new Object[][] {
                {"simpleOne", null},
                {"objectOne", null},
                {"mapOne", levelTwo}
        });

        Map<String, Object> levelZero = makeMap(new Object[][] {
                {"simple", "abc"},
                {"object", new SupportBean_A("A1")},
                {"nodefmap", makeMap(new Object[][] {{"key1", "val1"}})},
                {"map", levelOne}
        });

        return levelZero;
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

    private Object getNestedKey(Map<String, Object> root, String keyOne, String keyTwo)
    {
        Map map = (Map) root.get(keyOne);
        return map.get(keyTwo);
    }

    private Object getNestedKey(Map<String, Object> root, String keyOne, String keyTwo, String keyThree)
    {
        Map map = (Map) root.get(keyOne);
        map = (Map) map.get(keyTwo);
        return map.get(keyThree);
    }

    private EPServiceProvider getEngineInitialized(String name, Map<String, Object> definition)
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        if (name != null)
        {
            configuration.addNestableEventTypeAlias(name, definition);
        }
        
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        return epService;
    }

    private static Log log = LogFactory.getLog(TestMapEvent.class);
}
