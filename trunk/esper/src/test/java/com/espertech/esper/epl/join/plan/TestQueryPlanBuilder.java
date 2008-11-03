package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.epl.SupportOuterJoinDescFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.type.OuterJoinType;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TestQueryPlanBuilder extends TestCase
{
    private EventType[] typesPerStream;
    private boolean[] isHistorical;
    private HistoricalDependencyGraph dependencyGraph;

    public void setUp()
    {
        typesPerStream = new EventType[] {
                SupportEventAdapterService.getService().addBeanType(SupportBean_S0.class.getName(), SupportBean_S0.class, true),
                SupportEventAdapterService.getService().addBeanType(SupportBean_S1.class.getName(), SupportBean_S1.class, true)
        };
        dependencyGraph = new HistoricalDependencyGraph(2);
        isHistorical = new boolean[2];
    }

    public void testGetPlan() throws Exception
    {
        List<OuterJoinDesc> descList = new LinkedList<OuterJoinDesc>();
        OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
        descList.add(joinDesc);

        QueryGraph queryGraph = new QueryGraph(2);
        QueryPlan plan = QueryPlanBuilder.getPlan(typesPerStream, new LinkedList<OuterJoinDesc>(), queryGraph, null, false, isHistorical, dependencyGraph, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(typesPerStream, descList, queryGraph, null, false, isHistorical, dependencyGraph, null);
        assertPlan(plan);

        FilterExprAnalyzer.analyze(SupportExprNodeFactory.makeEqualsNode(), queryGraph);
        plan = QueryPlanBuilder.getPlan(typesPerStream, descList, queryGraph, null, false, isHistorical, dependencyGraph, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(typesPerStream, new LinkedList<OuterJoinDesc>(), queryGraph, null, false, isHistorical, dependencyGraph, null);
        assertPlan(plan);
    }

    private void assertPlan(QueryPlan plan)
    {
        assertEquals(2, plan.getExecNodeSpecs().length);
        assertEquals(2, plan.getExecNodeSpecs().length);
    }
}