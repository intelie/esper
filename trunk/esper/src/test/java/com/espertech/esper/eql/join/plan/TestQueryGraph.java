package com.espertech.esper.eql.join.plan;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

import com.espertech.esper.eql.join.plan.QueryGraph;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestQueryGraph extends TestCase
{
    private QueryGraph queryGraph;

    public void setUp()
    {
        queryGraph = new QueryGraph(3);
    }

    public void testFillEquivalency()
    {
        // test with just 3 streams
        queryGraph.add(0, "p00", 1, "p10");
        queryGraph.add(1, "p10", 2, "p20");

        assertFalse(queryGraph.isNavigable(0, 2));
        assertNull(queryGraph.getKeyProperties(0, 2));
        assertNull(queryGraph.getIndexProperties(0, 2));

        QueryGraph.fillEquivalentNav(queryGraph);

        assertTrue(queryGraph.isNavigable(0, 2));
        String[] expectedOne = new String[] {"p00"};
        String[] expectedTwo = new String[] {"p20"};
        assertTrue(Arrays.equals(expectedOne, queryGraph.getKeyProperties(0, 2)));
        assertTrue(Arrays.equals(expectedTwo, queryGraph.getIndexProperties(0, 2)));

        // test with 5 streams, connect all streams to all streams
        queryGraph = new QueryGraph(5);
        queryGraph.add(0, "p0", 1, "p1");
        queryGraph.add(3, "p3", 4, "p4");
        queryGraph.add(2, "p2", 3, "p3");
        queryGraph.add(1, "p1", 2, "p2");

        QueryGraph.fillEquivalentNav(queryGraph);

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (i == j)
                {
                    continue;
                }
                assertTrue("Not navigable: i=" + i + " j=" + j, queryGraph.isNavigable(i, j));
            }
        }
    }

    public void testAdd()
    {
        // Try invalid add
        try
        {
            queryGraph.add(1, null, 2, null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }

        // Try invalid add
        try
        {
            queryGraph.add(1, "a", 1, "b");
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }

        // Try :        s1.p11 = s2.p21  and  s2.p22 = s3.p31
        assertTrue(queryGraph.add(1, "p11", 2, "p21"));
        assertTrue(queryGraph.add(2, "p22", 3, "p31"));
        assertFalse(queryGraph.add(2, "p22", 3, "p31"));
        log.debug(queryGraph.toString());
    }

    public void testIsNavigable()
    {
        assertFalse(queryGraph.isNavigable(0, 1));
        assertFalse(queryGraph.isNavigable(0, 2));
        assertFalse(queryGraph.isNavigable(1, 2));

        queryGraph.add(0, "p1", 1, "p2");
        assertTrue(queryGraph.isNavigable(0, 1));
        assertFalse(queryGraph.isNavigable(0, 2));
        assertFalse(queryGraph.isNavigable(1, 2));

        queryGraph.add(2, "p1", 1, "p2");
        assertTrue(queryGraph.isNavigable(0, 1));
        assertFalse(queryGraph.isNavigable(0, 2));
        assertTrue(queryGraph.isNavigable(1, 2));

        queryGraph.add(2, "p1", 0, "p2");
        assertTrue(queryGraph.isNavigable(0, 1));
        assertTrue(queryGraph.isNavigable(0, 2));
        assertTrue(queryGraph.isNavigable(1, 2));
    }

    public void testGetNavigableStreams()
    {
        queryGraph = new QueryGraph(5);
        queryGraph.add(3, "p3", 4, "p4");
        queryGraph.add(2, "p2", 3, "p3");
        queryGraph.add(1, "p1", 2, "p2");

        assertEquals(0, queryGraph.getNavigableStreams(0).size());
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2}, queryGraph.getNavigableStreams(1));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1,3}, queryGraph.getNavigableStreams(2));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2,4}, queryGraph.getNavigableStreams(3));
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, queryGraph.getNavigableStreams(4));
    }

    public void testGetProperties()
    {
        // s1.p11 = s0.p01 and s0.p02 = s1.p12
        queryGraph.add(1, "p11", 0, "p01");
        queryGraph.add(0, "p02", 1, "p12");
        log.debug(queryGraph.toString());

        String[] expectedOne = new String[] {"p11", "p12"};
        String[] expectedTwo = new String[] {"p01", "p02"};
        assertTrue(Arrays.equals(expectedTwo, queryGraph.getIndexProperties(1, 0)));
        assertTrue(Arrays.equals(expectedOne, queryGraph.getIndexProperties(0, 1)));
        assertTrue(Arrays.equals(expectedOne, queryGraph.getKeyProperties(1, 0)));
        assertTrue(Arrays.equals(expectedTwo, queryGraph.getKeyProperties(0, 1)));
    }

    private static Log log = LogFactory.getLog(TestQueryGraph.class);
}
