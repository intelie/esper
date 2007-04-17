package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.eql.expression.ExprEqualsNode;
import net.esper.eql.expression.ExprAndNode;
import net.esper.eql.join.plan.FilterExprAnalyzer;
import net.esper.eql.join.plan.QueryGraph;

public class TestFilterExprAnalyzer extends TestCase
{
    public void testAnalyzeEquals() throws Exception
    {
        ExprEqualsNode equalsNode = SupportExprNodeFactory.makeEqualsNode();
        equalsNode.dumpDebug("node...");

        QueryGraph graph = new QueryGraph(2);
        FilterExprAnalyzer.analyzeEqualsNode(equalsNode, graph, false);

        assertTrue(graph.isNavigable(0, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getKeyProperties(0, 1), new String[] {"intPrimitive"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getIndexProperties(1, 0), new String[] {"intPrimitive"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getKeyProperties(1, 0), new String[] {"intBoxed"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getIndexProperties(0, 1), new String[] {"intBoxed"});
    }

    public void testAnalyzeAnd() throws Exception
    {
        ExprAndNode andNode = SupportExprNodeFactory.make2SubNodeAnd();
        andNode.dumpDebug("node...");

        QueryGraph graph = new QueryGraph(2);
        FilterExprAnalyzer.analyzeAndNode(andNode, graph, false);

        assertTrue(graph.isNavigable(0, 1));
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getKeyProperties(0, 1), new String[] {"intPrimitive","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getIndexProperties(1, 0), new String[] {"intPrimitive","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getKeyProperties(1, 0), new String[] {"intBoxed","string"});
        ArrayAssertionUtil.assertEqualsExactOrder(graph.getIndexProperties(0, 1), new String[] {"intBoxed","string"});
    }
}
