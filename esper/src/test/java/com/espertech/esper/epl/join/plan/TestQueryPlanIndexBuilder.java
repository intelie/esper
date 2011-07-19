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

package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestQueryPlanIndexBuilder extends TestCase
{
    private QueryGraph queryGraph;
    private EventType[] types;

    public void setUp()
    {
        queryGraph = new QueryGraph(5);
        types = new EventType[] {
                SupportEventTypeFactory.createMapType(createType("p00,p01")),
                SupportEventTypeFactory.createMapType(createType("p10")),
                SupportEventTypeFactory.createMapType(createType("p20,p21")),
                SupportEventTypeFactory.createMapType(createType("p30,p31")),
                SupportEventTypeFactory.createMapType(createType("p40,p41,p42")),
            };

        queryGraph.addStrictEquals(0, "p00", null, 1, "p10", null);
        queryGraph.addStrictEquals(0, "p01", null, 2, "p20", null);
        queryGraph.addStrictEquals(4, "p40", null, 3, "p30", null);
        queryGraph.addStrictEquals(4, "p41", null, 3, "p31", null);
        queryGraph.addStrictEquals(4, "p42", null, 2, "p21", null);
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
        queryGraph.addStrictEquals(0, "p00", null, 1, "p10", null);
        queryGraph.addStrictEquals(0, "p00", null, 2, "p20", null);

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
