package com.espertech.esper.eql.expression;

import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.support.eql.SupportExprNodeFactory;

public class TestExprCountNode extends TestExprAggregateNodeAdapter
{
    private ExprCountNode wildcardCount;

    public void setUp() throws Exception
    {
        super.validatedNodeToTest = makeNode(5, Integer.class);

        wildcardCount = new ExprCountNode(false);
        SupportExprNodeFactory.validate(wildcardCount);
    }

    public void testGetType() throws Exception
    {
        assertEquals(Long.class, validatedNodeToTest.getType());
        assertEquals(Long.class, wildcardCount.getType());
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("count(5)", validatedNodeToTest.toExpressionString());
        assertEquals("count(*)", wildcardCount.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprSumNode(false)));
        assertTrue(wildcardCount.equalsNode(wildcardCount));
    }

    private ExprCountNode makeNode(Object value, Class type) throws Exception
    {
        ExprCountNode countNode = new ExprCountNode(false);
        countNode.addChildNode(new SupportExprNode(value, type));
        SupportExprNodeFactory.validate(countNode);
        return countNode;
    }
}
