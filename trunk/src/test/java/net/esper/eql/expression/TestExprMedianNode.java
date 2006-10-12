package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;

public class TestExprMedianNode extends TestExprAggregateNodeAdapter
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
        assertEquals("median(5)", validatedNodeToTest.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprSumNode(false)));
    }

    public void testAggregator()
    {
        ExprMedianNode.DoubleMedian median = new ExprMedianNode.DoubleMedian();
        assertEquals(null, median.getValue());
        median.enter(10);
        assertEquals(10D, median.getValue());
        median.enter(20);
        assertEquals(15D, median.getValue());
        median.enter(10);
        assertEquals(10D, median.getValue());

        median.leave(10);
        assertEquals(15D, median.getValue());
        median.leave(10);
        assertEquals(20D, median.getValue());
        median.leave(20);
        assertEquals(null, median.getValue());
    }

    private ExprMedianNode makeNode(Object value, Class type) throws Exception
    {
        ExprMedianNode medianNode = new ExprMedianNode(false);
        medianNode.addChildNode(new SupportExprNode(value, type));
        medianNode.validate(null, null);
        return medianNode;
    }
}
