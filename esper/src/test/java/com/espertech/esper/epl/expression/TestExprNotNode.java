package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportBoolExprNode;
import com.espertech.esper.type.MinMaxTypeEnum;

public class TestExprNotNode extends TestCase
{
    private ExprNotNode notNode;

    public void setUp()
    {
        notNode = new ExprNotNode();
    }

    public void testGetType()
    {
        assertEquals(Boolean.class, notNode.getType());
    }

    public void testValidate() throws Exception
    {
        // fails with zero expressions
        try
        {
            notNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // fails with too many sub-expressions
        notNode.addChildNode(new SupportExprNode(Boolean.class));
        notNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            notNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // test failure, type mismatch
        notNode = new ExprNotNode();
        notNode.addChildNode(new SupportExprNode(String.class));
        try
        {
            notNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // validates
        notNode = new ExprNotNode();
        notNode.addChildNode(new SupportExprNode(Boolean.class));
        notNode.validate(null, null, null, null, null);
    }

    public void testEvaluate()
    {
        notNode.addChildNode(new SupportBoolExprNode(true));
        assertFalse( (Boolean) notNode.evaluate(null, false));

        notNode = new ExprNotNode();
        notNode.addChildNode(new SupportBoolExprNode(false));
        assertTrue( (Boolean) notNode.evaluate(null, false));
    }

    public void testToExpressionString() throws Exception
    {
        notNode.addChildNode(new SupportExprNode(true));
        assertEquals("NOT(true)", notNode.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(notNode.equalsNode(notNode));
        assertFalse(notNode.equalsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
        assertFalse(notNode.equalsNode(new ExprOrNode()));
        assertTrue(notNode.equalsNode(new ExprNotNode()));
    }
}
