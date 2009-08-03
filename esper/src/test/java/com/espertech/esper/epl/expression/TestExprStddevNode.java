package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

public class TestExprStddevNode extends TestExprAggregateNodeAdapter
{
    public void setUp() throws Exception
    {
        super.validatedNodeToTest = makeNode(5, Integer.class);
    }

    public void testGetType() throws Exception
    {
        assertEquals(Double.class, validatedNodeToTest.getType());
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("stddev(5)", validatedNodeToTest.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprSumNode(false)));
    }

    private ExprStddevNode makeNode(Object value, Class type) throws Exception
    {
        ExprStddevNode stddevNode = new ExprStddevNode(false);
        stddevNode.addChildNode(new SupportExprNode(value, type));
        SupportExprNodeFactory.validate3Stream(stddevNode);
        return stddevNode;
    }
}