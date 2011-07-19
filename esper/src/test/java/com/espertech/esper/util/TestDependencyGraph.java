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

package com.espertech.esper.util;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestDependencyGraph extends TestCase
{
    public void testGetRootNodes()
    {
        // 1 needs 3 and 4; 2 need 0
        DependencyGraph graph = new DependencyGraph(5);
        graph.addDependency(1, 4);
        graph.addDependency(1, 3);
        graph.addDependency(2, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1, 2}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // 2 need 0, 3, 4
        graph = new DependencyGraph(5);
        graph.addDependency(2, 0);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1, 2}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // 2 need 0, 3, 4; 1 needs 2
        graph = new DependencyGraph(5);
        graph.addDependency(2, 0);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        graph.addDependency(1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1}, graph.getRootNodes());
        assertNull(graph.getFirstCircularDependency());

        // circular among 3 nodes
        graph = new DependencyGraph(3);
        graph.addDependency(1, 0);
        graph.addDependency(2, 1);
        graph.addDependency(0, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2, 1}, graph.getFirstCircularDependency().toArray(new Integer[3]));

        // circular among 4 nodes
        graph = new DependencyGraph(4);
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
        graph = new DependencyGraph(3);
        graph.addDependency(1, 0);
        graph.addDependency(0, 1);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 1}, graph.getFirstCircularDependency().toArray(new Integer[2]));

        // circular among 6 nodes
        graph = new DependencyGraph(6);
        graph.addDependency(1, 0);
        graph.addDependency(0, 2);
        graph.addDependency(2, 3);
        graph.addDependency(2, 4);
        graph.addDependency(4, 0);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1,5}, graph.getRootNodes());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0, 2, 4}, graph.getFirstCircularDependency().toArray(new Integer[3]));
    }
}
