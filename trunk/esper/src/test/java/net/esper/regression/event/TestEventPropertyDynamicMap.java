package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestEventPropertyDynamicMap extends TestCase
{
    private SupportUpdateListener listener;
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testMapWithinMap()
    {
        Properties properties = new Properties();
        properties.put("inner", Map.class.getName());
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyLevel2", properties);

        String statementText = "select " +
                               "inner.int? as t0, " +
                               "inner.innerTwo?.nested as t1, " +
                               "inner.innerTwo?.innerThree.nestedTwo as t2, " +
                               "dynamicOne? as t3, " +
                               "dynamicTwo? as t4, " +
                               "indexed[1]? as t5, " +
                               "mapped('keyOne')? as t6, " +
                               "inner.indexedTwo[0]? as t7, " +
                               "inner.mappedTwo('keyTwo')? as t8 " +
                    "from MyLevel2.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        statement.addListener(listener);

        HashMap map = new HashMap<String, Object>();
        map.put("dynamicTwo", 20l);
        map.put("inner", makeMap(
                "int", 10,
                "indexedTwo", new int[] {-10},
                "mappedTwo", makeMap("keyTwo", "def"),
                "innerTwo", makeMap("nested", 30d,
                    "innerThree", makeMap("nestedTwo", 99))));
        map.put("indexed", new float[] {-1, -2, -3});
        map.put("mapped", makeMap("keyOne", "abc"));
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {10, 30d, 99, null, 20l, -2.0f, "abc", -10, "def"});

        map = new HashMap<String, Object>();
        map.put("inner", makeMap(
                "indexedTwo", new int[] {},
                "mappedTwo", makeMap("yyy", "xxx"),
                "innerTwo", null));
        map.put("indexed", new float[] {});
        map.put("mapped", makeMap("xxx", "yyy"));
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {null, null, null, null, null, null, null, null, null});

        epService.getEPRuntime().sendEvent(new HashMap<String, Object>(), "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {null, null, null, null, null, null, null, null, null});

        map = new HashMap<String, Object>();
        map.put("inner", "xxx");
        map.put("indexed", null);
        map.put("mapped", "xxx");
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {null, null, null, null, null, null, null, null, null});
    }

    public void testMapWithinMapExists()
    {
        Properties properties = new Properties();
        properties.put("inner", Map.class.getName());
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyLevel2", properties);

        String statementText = "select " +
                               "exists(inner.int?) as t0, " +
                               "exists(inner.innerTwo?.nested) as t1, " +
                               "exists(inner.innerTwo?.innerThree.nestedTwo) as t2, " +
                               "exists(dynamicOne?) as t3, " +
                               "exists(dynamicTwo?) as t4, " +
                               "exists(indexed[1]?) as t5, " +
                               "exists(mapped('keyOne')?) as t6, " +
                               "exists(inner.indexedTwo[0]?) as t7, " +
                               "exists(inner.mappedTwo('keyTwo')?) as t8 " +
                    "from MyLevel2.win:length(5)";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        statement.addListener(listener);

        HashMap map = new HashMap<String, Object>();
        map.put("dynamicTwo", 20l);
        map.put("inner", makeMap(
                "int", 10,
                "indexedTwo", new int[] {-10},
                "mappedTwo", makeMap("keyTwo", "def"),
                "innerTwo", makeMap("nested", 30d,
                    "innerThree", makeMap("nestedTwo", 99))));
        map.put("indexed", new float[] {-1, -2, -3});
        map.put("mapped", makeMap("keyOne", "abc"));
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {true, true,true,false,true,true,true,true,true});

        map = new HashMap<String, Object>();
        map.put("inner", makeMap(
                "indexedTwo", new int[] {},
                "mappedTwo", makeMap("yyy", "xxx"),
                "innerTwo", null));
        map.put("indexed", new float[] {});
        map.put("mapped", makeMap("xxx", "yyy"));
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {false, false,false,false,false,false,false,false,false});

        epService.getEPRuntime().sendEvent(new HashMap<String, Object>(), "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {false, false,false,false,false,false,false,false,false});

        map = new HashMap<String, Object>();
        map.put("inner", "xxx");
        map.put("indexed", null);
        map.put("mapped", "xxx");
        epService.getEPRuntime().sendEvent(map, "MyLevel2");
        assertResults(listener.assertOneGetNewAndReset(), new Object[] {false, false,false,false,false,false,false,false,false});
    }

    public void testMapWithinMap2LevelsInvalid()
    {
        Properties properties = new Properties();
        properties.put("inner", Map.class.getName());
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyLevel2", properties);

        String statementText = "select inner.int as t0 from MyLevel2.win:length(5)";
        try
        {
            epService.getEPAdministrator().createEQL(statementText);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }

        statementText = "select inner.int.inner2? as t0 from MyLevel2.win:length(5)";
        try
        {
            epService.getEPAdministrator().createEQL(statementText);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }

        statementText = "select inner.int.inner2? as t0 from MyLevel2.win:length(5)";
        try
        {
            epService.getEPAdministrator().createEQL(statementText);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    private void assertResults(EventBean event, Object[] result)
    {
        for (int i = 0; i < result.length; i++)
        {
            assertEquals("failed for index " + i, result[i], event.get("t" + i));
        }
    }

    private Map makeMap(Object... keysAndValues)
    {
        if (keysAndValues.length % 2 != 0)
        {
            throw new IllegalArgumentException();
        }
        Object[][] pairs = new Object[keysAndValues.length / 2][2];
        for (int i = 0; i < keysAndValues.length; i++)
        {
            int index = i / 2;
            if (i % 2 == 0)
            {
                pairs[index][0] = keysAndValues[i];
            }
            else
            {
                pairs[index][1] = keysAndValues[i];
            }
        }
        return makeMap(pairs);
    }

    private Map makeMap(Object[][] pairs)
    {
        Map map = new HashMap();
        for (int i = 0; i < pairs.length; i++)
        {
            map.put(pairs[i][0], pairs[i][1]);
        }
        return map;
    }
}
