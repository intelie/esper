package com.espertech.esper.eql.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.eql.spec.OuterJoinDesc;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean_S1;
import com.espertech.esper.support.eql.SupportExprNodeFactory;
import com.espertech.esper.support.eql.SupportOuterJoinDescFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.type.OuterJoinType;

import java.util.LinkedList;
import java.util.List;

public class TestQueryPlanBuilder extends TestCase
{
    private EventType[] typesPerStream;

    public void setUp()
    {
        typesPerStream = new EventType[] {
                SupportEventAdapterService.getService().addBeanType(SupportBean_S0.class.getName(), SupportBean_S0.class),
                SupportEventAdapterService.getService().addBeanType(SupportBean_S1.class.getName(), SupportBean_S1.class)
        };
    }

    public void testGetPlan() throws Exception
    {
        List<OuterJoinDesc> descList = new LinkedList<OuterJoinDesc>();
        OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
        descList.add(joinDesc);

        QueryPlan plan = QueryPlanBuilder.getPlan(typesPerStream, new LinkedList<OuterJoinDesc>(), null, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(typesPerStream, descList, null, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(typesPerStream, descList, SupportExprNodeFactory.makeEqualsNode(), null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(typesPerStream, new LinkedList<OuterJoinDesc>(), SupportExprNodeFactory.makeEqualsNode(), null);
        assertPlan(plan);
    }

    private void assertPlan(QueryPlan plan)
    {
        assertEquals(2, plan.getExecNodeSpecs().length);
        assertEquals(2, plan.getExecNodeSpecs().length);
    }
}
