package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;

public class TestExprCastNode extends TestCase
{
    private ExprCastNode[] castNodes;

    public void setUp()
    {
        castNodes = new ExprCastNode[2];

        castNodes[0] = new ExprCastNode("long");
        castNodes[0].addChildNode(new SupportExprNode(10, Long.class));

        castNodes[1] = new ExprCastNode("java.lang.Integer");
        castNodes[1].addChildNode(new SupportExprNode(0x10, byte.class));
    }

    public void testGetType() throws Exception
    {
        for (int i = 0; i < castNodes.length; i++)
        {
            castNodes[i].validate(null, null, null, null);
        }

        assertEquals(Long.class, castNodes[0].getType());
        assertEquals(Integer.class, castNodes[1].getType());
    }

    public void testValidate() throws Exception
    {
        ExprCastNode castNode = new ExprCastNode("int");

        // Test too few nodes under this node
        try
        {
            castNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Test too many nodes
        castNode.addChildNode(new SupportExprNode(1));
        castNode.addChildNode(new SupportExprNode("s"));
        try
        {
            castNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        for (int i = 0; i < castNodes.length; i++)
        {
            castNodes[i].validate(null, null, null, null);
        }

        assertEquals(10L, castNodes[0].evaluate(null, false));
        assertEquals(16, castNodes[1].evaluate(null, false));
    }

    public void testEquals() throws Exception
    {
        assertFalse(castNodes[0].equalsNode(new ExprEqualsNode(true)));
        assertFalse(castNodes[0].equalsNode(castNodes[1]));
        assertFalse(castNodes[0].equalsNode(new ExprCastNode("java.lang.Integer")));
    }

    public void testToExpressionString() throws Exception
    {
        castNodes[0].validate(null, null, null, null);
        assertEquals("cast(10, long)", castNodes[0].toExpressionString());
    }
}
