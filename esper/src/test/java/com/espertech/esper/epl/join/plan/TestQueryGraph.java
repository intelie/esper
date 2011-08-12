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
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestQueryGraph extends TestCase
{
    private QueryGraph queryGraph;
    private EventType[] types;

    public void setUp()
    {
        queryGraph = new QueryGraph(3);
        types = new EventType[] {
                SupportEventTypeFactory.createMapType(createType("p0,p00,p01")),
                SupportEventTypeFactory.createMapType(createType("p1,p10")),
                SupportEventTypeFactory.createMapType(createType("p2,p20,p21")),
                SupportEventTypeFactory.createMapType(createType("p3,p30,p31")),
                SupportEventTypeFactory.createMapType(createType("p4,p40,p41,p42")),
            };
    }

    public void testFillEquivalency()
    {
        // test with just 3 streams
        queryGraph.addStrictEquals(0, "p00", null, 1, "p10", null);
        queryGraph.addStrictEquals(1, "p10", null, 2, "p20", null);

        assertFalse(queryGraph.isNavigableAtAll(0, 2));
        assertEquals(0, QueryGraphTestUtil.getStrictKeyProperties(queryGraph, 0, 2).length);
        assertEquals(0, QueryGraphTestUtil.getIndexProperties(queryGraph, 0, 2).length);

        QueryGraph.fillEquivalentNav(types, queryGraph);

        assertTrue(queryGraph.isNavigableAtAll(0, 2));
        String[] expectedOne = new String[] {"p00"};
        String[] expectedTwo = new String[] {"p20"};
        assertTrue(Arrays.equals(expectedOne, QueryGraphTestUtil.getStrictKeyProperties(queryGraph, 0, 2)));
        assertTrue(Arrays.equals(expectedTwo, QueryGraphTestUtil.getIndexProperties(queryGraph, 0, 2)));

        // test with 5 streams, connect all streams to all streams
        queryGraph = new QueryGraph(5);
        queryGraph.addStrictEquals(0, "p0", null, 1, "p1", null);
        queryGraph.addStrictEquals(3, "p3", null, 4, "p4", null);
        queryGraph.addStrictEquals(2, "p2", null, 3, "p3", null);
        queryGraph.addStrictEquals(1, "p1", null, 2, "p2", null);

        QueryGraph.fillEquivalentNav(types, queryGraph);

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (i == j)
                {
                    continue;
                }
                assertTrue("Not navigable: i=" + i + " j=" + j, queryGraph.isNavigableAtAll(i, j));
            }
        }
    }

    public void testAdd()
    {
        // Try invalid add
        try
        {
            queryGraph.addStrictEquals(1, null, null, 2, null, null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }

        // Try invalid add
        try
        {
            queryGraph.addStrictEquals(1, "a", null, 1, "b", null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }

        // Try :        s1.p11 = s2.p21  and  s2.p22 = s3.p31
        assertTrue(queryGraph.addStrictEquals(1, "p11", null, 2, "p21", null));

        try {
            queryGraph.addStrictEquals(2, "p22", null, 3, "p31", null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            // success
        }

        try {
            queryGraph.addStrictEquals(2, "p22", null, 3, "p31", null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            // success
        }

        log.debug(queryGraph.toString());
    }

    public void testIsNavigable()
    {
        assertFalse(queryGraph.isNavigableAtAll(0, 1));
        assertFalse(queryGraph.isNavigableAtAll(0, 2));
        assertFalse(queryGraph.isNavigableAtAll(1, 2));

        queryGraph.addStrictEquals(0, "p1", null, 1, "p2", null);
        assertTrue(queryGraph.isNavigableAtAll(0, 1));
        assertFalse(queryGraph.isNavigableAtAll(0, 2));
        assertFalse(queryGraph.isNavigableAtAll(1, 2));

        queryGraph.addStrictEquals(2, "p1", null, 1, "p2", null);
        assertTrue(queryGraph.isNavigableAtAll(0, 1));
        assertFalse(queryGraph.isNavigableAtAll(0, 2));
        assertTrue(queryGraph.isNavigableAtAll(1, 2));

        queryGraph.addStrictEquals(2, "p1", null, 0, "p2", null);
        assertTrue(queryGraph.isNavigableAtAll(0, 1));
        assertTrue(queryGraph.isNavigableAtAll(0, 2));
        assertTrue(queryGraph.isNavigableAtAll(1, 2));
    }

    public void testGetNavigableStreams()
    {
        queryGraph = new QueryGraph(5);
        queryGraph.addStrictEquals(3, "p3", null, 4, "p4", null);
        queryGraph.addStrictEquals(2, "p2", null, 3, "p3", null);
        queryGraph.addStrictEquals(1, "p1", null, 2, "p2", null);

        assertEquals(0, queryGraph.getNavigableStreams(0).size());
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2}, queryGraph.getNavigableStreams(1));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1,3}, queryGraph.getNavigableStreams(2));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2,4}, queryGraph.getNavigableStreams(3));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, queryGraph.getNavigableStreams(4));
    }

    public void testGetProperties()
    {
        // s1.p11 = s0.p01 and s0.p02 = s1.p12
        queryGraph.addStrictEquals(1, "p11", null, 0, "p01", null);
        queryGraph.addStrictEquals(0, "p02", null, 1, "p12", null);
        log.debug(queryGraph.toString());

        String[] expectedOne = new String[] {"p11", "p12"};
        String[] expectedTwo = new String[] {"p01", "p02"};
        assertTrue(Arrays.equals(expectedTwo, QueryGraphTestUtil.getIndexProperties(queryGraph, 1, 0)));
        assertTrue(Arrays.equals(expectedOne, QueryGraphTestUtil.getIndexProperties(queryGraph, 0, 1)));
        assertTrue(Arrays.equals(expectedOne, QueryGraphTestUtil.getStrictKeyProperties(queryGraph, 1, 0)));
        assertTrue(Arrays.equals(expectedTwo, QueryGraphTestUtil.getStrictKeyProperties(queryGraph, 0, 1)));
    }

    private Map<String, Object> createType(String propCSV) {
        String[] props = propCSV.split(",");
        Map<String, Object> type = new HashMap<String, Object>();
        for (int i = 0; i < props.length; i++) {
            type.put(props[i], String.class);
        }
        return type;
    }

    private static Log log = LogFactory.getLog(TestQueryGraph.class);
}
