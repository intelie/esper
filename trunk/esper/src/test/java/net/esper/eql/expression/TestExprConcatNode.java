package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;
import net.esper.type.MathArithTypeEnum;

public class TestExprConcatNode extends TestCase
{
    private ExprConcatNode concatNode;

    public void setUp()
    {
        concatNode = new ExprConcatNode();
    }

    public void testGetType() throws Exception
    {
        assertEquals(String.class, concatNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        concatNode = new ExprConcatNode();
        concatNode.addChildNode(new SupportExprNode("a"));
        concatNode.addChildNode(new SupportExprNode("b"));
        assertEquals("(\"a\"||\"b\")", concatNode.toExpressionString());
        concatNode.addChildNode(new SupportExprNode("c"));
        assertEquals("(\"a\"||\"b\"||\"c\")", concatNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have 2 or more String subnodes
        try
        {
            concatNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Must have only string-type subnodes
        concatNode.addChildNode(new SupportExprNode(String.class));
        concatNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
            concatNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        concatNode.addChildNode(new SupportExprNode("x"));
        concatNode.addChildNode(new SupportExprNode("y"));
        assertEquals("xy", concatNode.evaluate(null, false));
        concatNode.addChildNode(new SupportExprNode("z"));
        assertEquals("xyz", concatNode.evaluate(null, false));
        concatNode.addChildNode(new SupportExprNode(null));
        assertEquals(null, concatNode.evaluate(null, false));
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(concatNode.equalsNode(concatNode));
        assertFalse(concatNode.equalsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
    }
}
