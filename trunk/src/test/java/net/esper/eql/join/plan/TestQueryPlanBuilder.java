package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportOuterJoinDescFactory;
import net.esper.eql.expression.OuterJoinDesc;
import net.esper.type.OuterJoinType;

import java.util.List;
import java.util.LinkedList;

public class TestQueryPlanBuilder extends TestCase
{
    public void testGetPlan() throws Exception
    {
        List<OuterJoinDesc> descList = new LinkedList<OuterJoinDesc>();
        OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
        descList.add(joinDesc);

        QueryPlan plan = QueryPlanBuilder.getPlan(2, new LinkedList<OuterJoinDesc>(), null, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(2, descList, null, null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(2, descList, SupportExprNodeFactory.makeEqualsNode(), null);
        assertPlan(plan);

        plan = QueryPlanBuilder.getPlan(2, new LinkedList<OuterJoinDesc>(), SupportExprNodeFactory.makeEqualsNode(), null);
        assertPlan(plan);
    }

    private void assertPlan(QueryPlan plan)
    {
        assertEquals(2, plan.getExecNodeSpecs().length);
        assertEquals(2, plan.getExecNodeSpecs().length);
    }
}
