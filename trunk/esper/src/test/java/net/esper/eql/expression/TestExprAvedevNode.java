package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.eql.agg.AggregationMethod;

public class TestExprAvedevNode extends TestExprAggregateNodeAdapter
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
        assertEquals("avedev(5)", validatedNodeToTest.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprStddevNode(false)));
    }

    public void testAggregateFunction()
    {
        AggregationMethod agg = validatedNodeToTest.getPrototypeAggregator();
        assertEquals(Double.class, agg.getValueType());

        assertNull(agg.getValue());

        agg.enter(82);
        assertEquals(0D, agg.getValue());

        agg.enter(78);
        assertEquals(2D, agg.getValue());

        agg.enter(70);
        double result = (Double)agg.getValue();
        assertEquals("4.4444", Double.toString(result).substring(0, 6));

        agg.enter(58);
        assertEquals(8D, agg.getValue());

        agg.enter(42);
        assertEquals(12.8D, agg.getValue());

        agg.leave(82);
        assertEquals(12D, agg.getValue());

        agg.leave(58);
        result = (Double)agg.getValue();
        assertEquals("14.2222", Double.toString(result).substring(0, 7));
    }

    private ExprAvedevNode makeNode(Object value, Class type) throws Exception
    {
        ExprAvedevNode avedevNode = new ExprAvedevNode(false);
        avedevNode.addChildNode(new SupportExprNode(value, type));
        SupportExprNodeFactory.validate(avedevNode);
        return avedevNode;
    }
}
