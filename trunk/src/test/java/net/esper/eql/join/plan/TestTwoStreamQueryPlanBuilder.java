package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.eql.join.plan.QueryGraph;
import net.esper.eql.join.plan.QueryPlan;
import net.esper.eql.join.plan.TwoStreamQueryPlanBuilder;
import net.esper.type.OuterJoinType;

public class TestTwoStreamQueryPlanBuilder extends TestCase
{
    public void testBuildNoOuter()
    {
        QueryGraph graph = makeQueryGraph();
        QueryPlan spec = TwoStreamQueryPlanBuilder.build(graph, null);

        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[0].getIndexProps()[0], new String[] {"p01", "p02"});
        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[1].getIndexProps()[0], new String[] {"p11", "p12"});
        assertEquals(2, spec.getExecNodeSpecs().length);
    }

    public void testBuildOuter()
    {
        QueryGraph graph = makeQueryGraph();
        QueryPlan spec = TwoStreamQueryPlanBuilder.build(graph, OuterJoinType.LEFT);

        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[0].getIndexProps()[0], new String[] {"p01", "p02"});
        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[1].getIndexProps()[0], new String[] {"p11", "p12"});
        assertEquals(2, spec.getExecNodeSpecs().length);
        assertEquals(TableOuterLookupNode.class, spec.getExecNodeSpecs()[0].getClass());
        assertEquals(TableLookupNode.class, spec.getExecNodeSpecs()[1].getClass());
    }

    private QueryGraph makeQueryGraph()
    {
        QueryGraph graph = new QueryGraph(2);
        graph.add(0, "p01", 1, "p11");
        graph.add(0, "p02", 1, "p12");
        return graph;
    }
}
