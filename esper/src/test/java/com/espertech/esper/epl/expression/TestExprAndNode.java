package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNodeUtil;
import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportBoolExprNode;

public class TestExprAndNode extends TestCase
{
    private ExprAndNode andNode;

    public void setUp()
    {
        andNode = new ExprAndNode();
    }

    public void testGetType()
    {
        assertEquals(Boolean.class, andNode.getType());
    }

    public void testValidate() throws Exception
    {
        // test success
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        andNode.validate(null, null, null, null, null, null);

        // test failure, type mismatch
        andNode.addChildNode(new SupportExprNode(String.class));
        try
        {
            andNode.validate(null, null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // test failed - with just one child
        andNode = new ExprAndNode();
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            andNode.validate(null, null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(true));
        SupportExprNodeUtil.validate(andNode);
        assertTrue( (Boolean) andNode.evaluate(null, false, null));

        andNode = new ExprAndNode();
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(false));
        SupportExprNodeUtil.validate(andNode);
        assertFalse( (Boolean) andNode.evaluate(null, false, null));
    }

    public void testToExpressionString() throws Exception
    {
        andNode.addChildNode(new SupportExprNode(true));
        andNode.addChildNode(new SupportExprNode(false));

        assertEquals("(true AND false)", andNode.toExpressionString());
    }

    public void testEqualsNode()
    {
        assertTrue(andNode.equalsNode(new ExprAndNode()));
        assertFalse(andNode.equalsNode(new ExprOrNode()));
    }
}
