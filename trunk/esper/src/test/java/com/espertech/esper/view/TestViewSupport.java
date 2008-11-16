package com.espertech.esper.view;

import junit.framework.TestCase;
import com.espertech.esper.support.view.SupportSchemaNeutralView;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprConstantNode;

import java.util.List;
import java.util.ArrayList;

public class TestViewSupport extends TestCase
{
    private SupportSchemaNeutralView top;

    private SupportSchemaNeutralView child_1;
    private SupportSchemaNeutralView child_2;

    private SupportSchemaNeutralView child_2_1;
    private SupportSchemaNeutralView child_2_2;

    private SupportSchemaNeutralView child_2_1_1;
    private SupportSchemaNeutralView child_2_2_1;
    private SupportSchemaNeutralView child_2_2_2;

    public void setUp()
    {
        top = new SupportSchemaNeutralView("top");

        child_1 = new SupportSchemaNeutralView("1");
        child_2 = new SupportSchemaNeutralView("2");
        top.addView(child_1);
        top.addView(child_2);

        child_2_1 = new SupportSchemaNeutralView("2_1");
        child_2_2 = new SupportSchemaNeutralView("2_2");
        child_2.addView(child_2_1);
        child_2.addView(child_2_2);

        child_2_1_1 = new SupportSchemaNeutralView("2_1_1");
        child_2_2_1 = new SupportSchemaNeutralView("2_2_1");
        child_2_2_2 = new SupportSchemaNeutralView("2_2_2");
        child_2_1.addView(child_2_1_1);
        child_2_2.addView(child_2_2_1);
        child_2_2.addView(child_2_2_2);
    }

    public void testFindDescendent()
    {
        // Test a deep find
        List<View> descendents = ViewSupport.findDescendent(top, child_2_2_1);
        assertEquals(2, descendents.size());
        assertEquals(child_2, descendents.get(0));
        assertEquals(child_2_2, descendents.get(1));

        descendents = ViewSupport.findDescendent(top, child_2_1_1);
        assertEquals(2, descendents.size());
        assertEquals(child_2, descendents.get(0));
        assertEquals(child_2_1, descendents.get(1));

        descendents = ViewSupport.findDescendent(top, child_2_1);
        assertEquals(1, descendents.size());
        assertEquals(child_2, descendents.get(0));

        // Test a shallow find
        descendents = ViewSupport.findDescendent(top, child_2);
        assertEquals(0, descendents.size());

        // Test a no find
        descendents = ViewSupport.findDescendent(top, new SupportSchemaNeutralView());
        assertEquals(null, descendents);
    }

    public static List<ExprNode> toExprList(Object[] constants)
    {
        List<ExprNode> expr = new ArrayList<ExprNode>();
        for (int i = 0; i < constants.length; i++)
        {
            expr.add(new ExprConstantNode(constants[i]));
        }
        return expr;
    }

    public static List<ExprNode> toExprList(Object constant)
    {
        return toExprList(new Object[] {constant});
    }
}
