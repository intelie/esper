package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.eql.spec.OuterJoinDesc;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean_S0;
import net.esper.support.bean.SupportBean_S1;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportOuterJoinDescFactory;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.type.OuterJoinType;

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
