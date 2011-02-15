package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import junit.framework.TestCase;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.HashMap;
import java.util.Map;

public class TestQueryPlanIndexBuilder extends TestCase
{
    private QueryGraph queryGraph;
    private EventType[] types;

    public void setUp()
    {
        queryGraph = new QueryGraph(5);
        queryGraph.add(0, "p00", 1, "p10");
        queryGraph.add(0, "p01", 2, "p20");
        queryGraph.add(4, "p40", 3, "p30");
        queryGraph.add(4, "p41", 3, "p31");
        queryGraph.add(4, "p42", 2, "p21");

        types = new EventType[] {
                SupportEventTypeFactory.createMapType(createType("p00,p01")),
                SupportEventTypeFactory.createMapType(createType("p10")),
                SupportEventTypeFactory.createMapType(createType("p20,p21")),
                SupportEventTypeFactory.createMapType(createType("p30,p31")),
                SupportEventTypeFactory.createMapType(createType("p40,p41,p42")),
            };
    }

    public void testBuildIndexSpec()
    {
        QueryPlanIndex[] indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph, types);

        String[][] expected = new String[][] { {"p00"}, {"p01"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[0].getIndexProps(), expected);

        expected = new String[][] { {"p10"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[1].getIndexProps(), expected);

        expected = new String[][] { {"p20"}, {"p21"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[2].getIndexProps(), expected);

        expected = new String[][] { {"p30", "p31"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[3].getIndexProps(), expected);

        expected = new String[][] { {"p42"}, {"p40", "p41"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[4].getIndexProps(), expected);

        // Test no index, should have a single entry with a zero-length property name array
        queryGraph = new QueryGraph(3);
        indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph, types);
        assertEquals(1, indexes[1].getIndexProps().length);
    }

    public void testIndexAlreadyExists()
    {
        queryGraph = new QueryGraph(5);
        queryGraph.add(0, "p00", 1, "p10");
        queryGraph.add(0, "p00", 2, "p20");

        QueryPlanIndex[] indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph, types);

        String[][] expected = new String[][] { {"p00"} };
        ArrayAssertionUtil.assertEqualsStringArr(indexes[0].getIndexProps(), expected);
    }

    private Map<String, Object> createType(String propCSV) {
        String[] props = propCSV.split(",");
        Map<String, Object> type = new HashMap<String, Object>();
        for (int i = 0; i < props.length; i++) {
            type.put(props[i], String.class);
        }
        return type;
    }
}
