package com.espertech.esper.util;

import junit.framework.TestCase;

import java.util.*;

import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestGraphUtil extends TestCase
{
    public void testSimple() throws Exception
    {
        Map<String, Set<String>> graph = new LinkedHashMap<String, Set<String>>();
        assertEquals(0, GraphUtil.getTopDownOrder(graph).size());

        add(graph, "1_1", "1");
        ArrayAssertionUtil.assertEqualsExactOrder("1,1_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "1_1_1", "1_1");
        ArrayAssertionUtil.assertEqualsExactOrder("1,1_1,1_1_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "0_1", "0");
        ArrayAssertionUtil.assertEqualsExactOrder("0,0_1,1,1_1,1_1_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "1_2", "1");
        ArrayAssertionUtil.assertEqualsExactOrder("0,0_1,1,1_1,1_1_1,1_2".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "1_1_2", "1_1");
        ArrayAssertionUtil.assertEqualsExactOrder("0,0_1,1,1_1,1_1_1,1_1_2,1_2".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "1_2_1", "1_2");
        ArrayAssertionUtil.assertEqualsExactOrder("0,0_1,1,1_1,1_1_1,1_1_2,1_2,1_2_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "0", "R");
        ArrayAssertionUtil.assertEqualsExactOrder("1,1_1,1_1_1,1_1_2,1_2,1_2_1,R,0,0_1,".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        add(graph, "1", "R");
        ArrayAssertionUtil.assertEqualsExactOrder("R,0,0_1,1,1_1,1_1_1,1_1_2,1_2,1_2_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        // so far each child had 1 parent, add a second parent
    }

    public void testInvalid() throws Exception
    {
        Map<String, Set<String>> graph = new LinkedHashMap<String, Set<String>>();
        add(graph, "1_1", "1");
        add(graph, "1", "1_1");
        tryInvalid(graph, "Circular dependency detected between [1_1, 1]");

        graph = new LinkedHashMap<String, Set<String>>();
        add(graph, "1", "2");
        add(graph, "2", "3");
        add(graph, "3", "1");
        tryInvalid(graph, "Circular dependency detected between [1, 2, 3]");
    }

    private void tryInvalid(Map<String, Set<String>> graph, String msg)
    {
        try
        {
            GraphUtil.getTopDownOrder(graph);
            fail();
        }
        catch (GraphCircularDependencyException ex)
        {
            // expected
            assertEquals(msg, ex.getMessage());
        }

    }

    private void add(Map<String, Set<String>> graph, String child, String parent)
    {
        Set<String> parents = graph.get(child);
        if (parents == null)
        {
            parents = new HashSet<String>();
            graph.put(child, parents);
        }
        parents.add(parent);
    }
}
