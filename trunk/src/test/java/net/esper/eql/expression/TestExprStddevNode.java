package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.eql.core.Aggregator;

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

    public void testAggregateFunction()
    {
        Aggregator agg = validatedNodeToTest.getAggregationFunction();
        assertEquals(Double.class, agg.getValueType());

        assertNull(agg.getValue());

        agg.enter(10);
        assertNull(agg.getValue());

        agg.enter(8);
        double result = (Double)agg.getValue();
        assertEquals("1.4142", Double.toString(result).substring(0, 6));

        agg.enter(5);
        result = (Double)agg.getValue();
        assertEquals("2.5166", Double.toString(result).substring(0, 6));

        agg.enter(9);
        result = (Double)agg.getValue();
        assertEquals("2.1602", Double.toString(result).substring(0, 6));

        agg.leave(10);
        result = (Double)agg.getValue();
        assertEquals("2.0816", Double.toString(result).substring(0, 6));
    }

    private ExprStddevNode makeNode(Object value, Class type) throws Exception
    {
        ExprStddevNode stddevNode = new ExprStddevNode(false);
        stddevNode.addChildNode(new SupportExprNode(value, type));
        stddevNode.validate(null, null);
        return stddevNode;
    }
}
