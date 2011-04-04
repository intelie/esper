package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.epl.expression.ExprEqualsNode;
import com.espertech.esper.epl.expression.ExprAndNode;

public class TestFilterExprAnalyzer extends TestCase
{
    public void testAnalyzeEquals() throws Exception
    {
        // s0.intPrimitive = s1.intBoxed
        ExprEqualsNode equalsNode = SupportExprNodeFactory.makeEqualsNode();

        QueryGraph graph = new QueryGraph(2);
        FilterExprAnalyzer.analyzeEqualsNode(equalsNode, graph, false);

        assertTrue(graph.isNavigableAtAll(0, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getStrictKeyProperties(graph, 0, 1), new String[] {"intPrimitive"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getIndexProperties(graph, 1, 0), new String[] {"intPrimitive"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getStrictKeyProperties(graph, 1, 0), new String[] {"intBoxed"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getIndexProperties(graph, 0, 1), new String[] {"intBoxed"});
    }

    public void testAnalyzeAnd() throws Exception
    {
        ExprAndNode andNode = SupportExprNodeFactory.make2SubNodeAnd();

        QueryGraph graph = new QueryGraph(2);
        FilterExprAnalyzer.analyzeAndNode(andNode, graph, false);

        assertTrue(graph.isNavigableAtAll(0, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getStrictKeyProperties(graph, 0, 1), new String[] {"intPrimitive","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getIndexProperties(graph, 1, 0), new String[] {"intPrimitive","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getStrictKeyProperties(graph, 1, 0), new String[] {"intBoxed","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(QueryGraphTestUtil.getIndexProperties(graph, 0, 1), new String[] {"intBoxed","string"});
    }
}
