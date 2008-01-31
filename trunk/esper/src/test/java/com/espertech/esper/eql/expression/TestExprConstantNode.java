package com.espertech.esper.eql.expression;

import junit.framework.TestCase;

public class TestExprConstantNode extends TestCase
{
    private ExprConstantNode constantNode;

    public void setUp()
    {
        constantNode = new ExprConstantNode("5");
    }

    public void testGetType() throws Exception
    {
        assertEquals(String.class, constantNode.getType());

        constantNode = new ExprConstantNode(null);
        assertNull(constantNode.getType());
    }

    public void testValidate() throws Exception
    {
        constantNode.validate(null, null, null, null, null);
    }

    public void testEvaluate()
    {
        assertEquals("5", constantNode.evaluate(null, false));
    }

    public void testToExpressionString() throws Exception
    {
        constantNode = new ExprConstantNode("5");
        assertEquals("\"5\"", constantNode.toExpressionString());

        constantNode = new ExprConstantNode(10);
        assertEquals("10", constantNode.toExpressionString());        
    }

    public void testEqualsNode()
    {
        assertTrue(constantNode.equalsNode(new ExprConstantNode("5")));
        assertFalse(constantNode.equalsNode(new ExprOrNode()));
        assertFalse(constantNode.equalsNode(new ExprConstantNode(null)));
        assertFalse(constantNode.equalsNode(new ExprConstantNode(3)));

        constantNode = new ExprConstantNode(null);
        assertTrue(constantNode.equalsNode(new ExprConstantNode(null)));
    }
}
