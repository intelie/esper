package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;

public class TestExprAvgNode extends TestExprAggregateNodeAdapter
{
    private ExprAvgNode avgNodeDistinct;

    public void setUp() throws Exception
    {
        super.validatedNodeToTest = makeNode(5, Integer.class, false);
        this.avgNodeDistinct = makeNode(6, Integer.class, true);
    }

    public void testAggregation()
    {
        ExprAvgNode.DoubleAvg agg = new ExprAvgNode.DoubleAvg();
        assertEquals(Double.class, agg.getValueType());
        assertEquals(null, agg.getValue());
        assertTrue(agg.newAggregator() instanceof ExprAvgNode.DoubleAvg);

        agg.enter(5);
        assertEquals(5d, agg.getValue());

        agg.enter(10);
        assertEquals(7.5d, agg.getValue());

        agg.leave(5);
        assertEquals(10d, agg.getValue());
    }

    public void testGetType() throws Exception
    {
        assertEquals(Double.class, validatedNodeToTest.getType());
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("avg(5)", validatedNodeToTest.toExpressionString());
        assertEquals("avg(distinct 6)", avgNodeDistinct.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprSumNode(false)));
    }

    private ExprAvgNode makeNode(Object value, Class type, boolean isDistinct) throws Exception
    {
        ExprAvgNode avgNode = new ExprAvgNode(isDistinct);
        avgNode.addChildNode(new SupportExprNode(value, type));
        avgNode.validate(null, null, null);
        return avgNode;
    }
}
