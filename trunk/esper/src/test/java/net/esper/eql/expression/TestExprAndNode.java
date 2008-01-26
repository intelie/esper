package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportBoolExprNode;

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
        andNode.validate(null, null, null, null, null);

        // test failure, type mismatch
        andNode.addChildNode(new SupportExprNode(String.class));
        try
        {
            andNode.validate(null, null, null, null, null);
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
            andNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate()
    {
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(true));
        assertTrue( (Boolean) andNode.evaluate(null, false));

        andNode = new ExprAndNode();
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(false));
        assertFalse( (Boolean) andNode.evaluate(null, false));
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
