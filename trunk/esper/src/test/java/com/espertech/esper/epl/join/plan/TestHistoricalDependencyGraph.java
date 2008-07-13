package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestHistoricalDependencyGraph extends TestCase
{
    public void testGetRootNodes()
    {
        // 1 needs 3 and 4; 2 need 0
        HistoricalDependencyGraph graph = new HistoricalDependencyGraph(5);
        graph.addDependency(1, 4);
        graph.addDependency(1, 3);
        graph.addDependency(2, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1, 2}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // 2 need 0, 3, 4
        graph = new HistoricalDependencyGraph(5);
        graph.addDependency(2, 0);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1, 2}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // 2 need 0, 3, 4; 1 needs 2
        graph = new HistoricalDependencyGraph(5);
        graph.addDependency(2, 0);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        graph.addDependency(1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // circular among 3 nodes
        graph = new HistoricalDependencyGraph(3);
        graph.addDependency(1, 0);
        graph.addDependency(2, 1);
        graph.addDependency(0, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2, 1}, graph.getFirstCircularDependency().toArray(new Integer[3]));

        // circular among 4 nodes
        graph = new HistoricalDependencyGraph(4);
        graph.addDependency(1, 0);
        graph.addDependency(2, 0);
        graph.addDependency(0, 2);
        graph.addDependency(3, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2}, graph.getFirstCircularDependency().toArray(new Integer[2]));

        graph.addDependency(2, 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2}, graph.getFirstCircularDependency().toArray(new Integer[2]));

        // circular among 3 nodes
        graph = new HistoricalDependencyGraph(3);
        graph.addDependency(1, 0);
        graph.addDependency(0, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 1}, graph.getFirstCircularDependency().toArray(new Integer[2]));

        // circular among 6 nodes
        graph = new HistoricalDependencyGraph(6);
        graph.addDependency(1, 0);
        graph.addDependency(0, 2);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        graph.addDependency(4, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1,5}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2, 4}, graph.getFirstCircularDependency().toArray(new Integer[3]));
    }
}
