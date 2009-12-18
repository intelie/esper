package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportAggregateExprNode;
import com.espertech.esper.support.epl.SupportExprNode;

import java.util.List;
import java.util.LinkedList;

public class TestExprAggregateNode extends TestCase
{
    public void testGetAggregatesBottomUp()
    {
        /*
                                    top (ag)
                  c1                            c2
           c1_1 (ag)   c1_2 (ag)            c2_1     c2_2
                                    c2_1_1 (ag)    c2_1_2 (ag)

        */

        ExprNode top = new SupportAggregateExprNode(null);
        ExprNode c1 = new SupportExprNode(null);
        ExprNode c2 = new SupportExprNode(null);
        top.addChildNode(c1);
        top.addChildNode(c2);

        ExprNode c1_1 = new SupportAggregateExprNode(null);
        ExprNode c1_2 = new SupportAggregateExprNode(null);
        c1.addChildNode(c1_1);
        c1.addChildNode(c1_2);
        c1_1.addChildNode(new SupportExprNode(null));
        c1_2.addChildNode(new SupportExprNode(null));

        ExprNode c2_1 = new SupportExprNode(null);
        ExprNode c2_2 = new SupportExprNode(null);
        c2.addChildNode(c2_1);
        c2.addChildNode(c2_2);
        c2_2.addChildNode(new SupportExprNode(null));

        ExprNode c2_1_1 = new SupportAggregateExprNode(null);
        ExprNode c2_1_2 = new SupportAggregateExprNode(null);
        c2_1.addChildNode(c2_1_1);
        c2_1.addChildNode(c2_1_2);

        List<ExprAggregateNode> aggregates = new LinkedList<ExprAggregateNode>();
        ExprAggregateNode.getAggregatesBottomUp(top, aggregates);

        assertEquals(5, aggregates.size());
        assertSame(c2_1_1, aggregates.get(0));
        assertSame(c2_1_2, aggregates.get(1));
        assertSame(c1_1, aggregates.get(2));
        assertSame(c1_2, aggregates.get(3));
        assertSame(top, aggregates.get(4));

        // Test no aggregates
        aggregates.clear();
        ExprAggregateNode.getAggregatesBottomUp(new SupportExprNode(null), aggregates);
        assertTrue(aggregates.isEmpty());
    }
}
