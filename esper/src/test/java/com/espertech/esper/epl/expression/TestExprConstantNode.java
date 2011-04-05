package com.espertech.esper.epl.expression;

import junit.framework.TestCase;

public class TestExprConstantNode extends TestCase
{
    private ExprConstantNode constantNode;

    public void setUp()
    {
        constantNode = new ExprConstantNodeImpl("5");
    }

    public void testGetType() throws Exception
    {
        assertEquals(String.class, constantNode.getType());

        constantNode = new ExprConstantNodeImpl(null);
        assertNull(constantNode.getType());
    }

    public void testValidate() throws Exception
    {
        constantNode.validate(ExprValidationContextFactory.makeEmpty());
    }

    public void testEvaluate()
    {
        assertEquals("5", constantNode.evaluate(null, false, null));
    }

    public void testToExpressionString() throws Exception
    {
        constantNode = new ExprConstantNodeImpl("5");
        assertEquals("\"5\"", constantNode.toExpressionString());

        constantNode = new ExprConstantNodeImpl(10);
        assertEquals("10", constantNode.toExpressionString());        
    }

    public void testEqualsNode()
    {
        assertTrue(constantNode.equalsNode(new ExprConstantNodeImpl("5")));
        assertFalse(constantNode.equalsNode(new ExprOrNode()));
        assertFalse(constantNode.equalsNode(new ExprConstantNodeImpl(null)));
        assertFalse(constantNode.equalsNode(new ExprConstantNodeImpl(3)));

        constantNode = new ExprConstantNodeImpl(null);
        assertTrue(constantNode.equalsNode(new ExprConstantNodeImpl(null)));
    }
}
