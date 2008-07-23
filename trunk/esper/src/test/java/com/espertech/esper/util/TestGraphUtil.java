package com.espertech.esper.util;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

import com.espertech.esper.support.util.ArrayAssertionUtil;

public class TestGraphUtil extends TestCase
{
    public void testSimple()
    {
        Map<String, String> graph = new HashMap<String, String>();
        assertEquals(0, GraphUtil.getTopDownOrder(graph).size());

        graph.put("1_1", "1");
        ArrayAssertionUtil.assertEqualsExactOrder("1,1_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());

        graph.put("1_1_1", "1_1");
        ArrayAssertionUtil.assertEqualsExactOrder("1,1_1,1_1_1".split(","), GraphUtil.getTopDownOrder(graph).toArray());
    }
}
