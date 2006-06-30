package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.type.ArithTypeEnum;
import net.esper.type.RelationalOpEnum;

public class TestExprSumNode extends TestExprAggregateNodeAdapter
{
    private ExprSumNode sumNode;

    public void setUp() throws Exception
    {
        sumNode = new ExprSumNode(false);

        super.validatedNodeToTest = makeNode(5, Integer.class);
    }

    public void testGetType() throws Exception
    {
        sumNode.addChildNode(new SupportExprNode(Integer.class));
        sumNode.validate(null);
        assertEquals(Integer.class, sumNode.getType());

        sumNode = new ExprSumNode(false);
        sumNode.addChildNode(new SupportExprNode(Float.class));
        sumNode.validate(null);
        assertEquals(Float.class, sumNode.getType());

        sumNode = new ExprSumNode(false);
        sumNode.addChildNode(new SupportExprNode(Short.class));
        sumNode.validate(null);
        assertEquals(Integer.class, sumNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(4-2)
        ExprMathNode arithNodeChild = new ExprMathNode(ArithTypeEnum.SUBTRACT);
        arithNodeChild.addChildNode(new SupportExprNode(4));
        arithNodeChild.addChildNode(new SupportExprNode(2));

        sumNode = new ExprSumNode(false);
        sumNode.addChildNode(arithNodeChild);

        assertEquals("sum((4-2))", sumNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have exactly 1 subnodes
        try
        {
            sumNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Must have only number-type subnodes
        sumNode.addChildNode(new SupportExprNode(String.class));
        sumNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
            sumNode.validate(null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testMakeAggregator() throws Exception
    {
        assertTrue(makeNode(5, Integer.class).getPrototypeAggregator() instanceof ExprSumNode.IntegerSum);
        assertTrue(makeNode(5, Float.class).getPrototypeAggregator() instanceof ExprSumNode.FloatSum);
        assertTrue(makeNode(5, Double.class).getPrototypeAggregator() instanceof ExprSumNode.DoubleSum);
        assertTrue(makeNode(5, Short.class).getPrototypeAggregator() instanceof ExprSumNode.NumberIntegerSum);
        assertTrue(makeNode(5, Long.class).getPrototypeAggregator() instanceof ExprSumNode.LongSum);
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(sumNode.equalsNode(sumNode));
        assertFalse(sumNode.equalsNode(new ExprOrNode()));
    }

    private ExprSumNode makeNode(Object value, Class type) throws Exception
    {
        ExprSumNode sumNode = new ExprSumNode(false);
        sumNode.addChildNode(new SupportExprNode(value, type));
        sumNode.validate(null);
        return sumNode;
    }
}
