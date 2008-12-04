package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.OuterJoinType;
import junit.framework.TestCase;

public class TestTwoStreamQueryPlanBuilder extends TestCase
{
    private EventType[] typesPerStream;

    public void setUp()
    {
        typesPerStream = new EventType[] {
                SupportEventAdapterService.getService().addBeanType(SupportBean_S0.class.getName(), SupportBean_S0.class, true),
                SupportEventAdapterService.getService().addBeanType(SupportBean_S1.class.getName(), SupportBean_S1.class, true)
        };
    }

    public void testBuildNoOuter()
    {
        QueryGraph graph = makeQueryGraph();
        QueryPlan spec = TwoStreamQueryPlanBuilder.build(typesPerStream, graph, null);

        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[0].getIndexProps()[0], new String[] {"p01", "p02"});
        ArrayAssertionUtil.assertEqualsExactOrder(spec.getIndexSpecs()[1].getIndexProps()[0], new String[] {"p11", "p12"});
        assertEquals(2, spec.getExecNodeSpecs().length);
    }

    public void testBuildOuter()
    {
        QueryGraph graph = makeQueryGraph();
        QueryPlan spec = TwoStreamQueryPlanBuilder.build(typesPerStream, graph, OuterJoinType.LEFT);

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
