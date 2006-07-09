package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.type.ArithTypeEnum;
import net.esper.type.MinMaxTypeEnum;

public class TestExprMinMaxAggrNode extends TestExprAggregateNodeAdapter
{
    private ExprMinMaxAggrNode maxNode;
    private ExprMinMaxAggrNode minNode;

    public void setUp() throws Exception
    {
        maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
        minNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MIN);

        super.validatedNodeToTest = makeNode(MinMaxTypeEnum.MAX, 5, Integer.class);
    }

    public void testGetType() throws Exception
    {
        maxNode.addChildNode(new SupportExprNode(Integer.class));
        maxNode.validate(null, null);
        assertEquals(Integer.class, maxNode.getType());

        minNode.addChildNode(new SupportExprNode(Float.class));
        minNode.validate(null, null);
        assertEquals(Float.class, minNode.getType());

        maxNode = new ExprMinMaxAggrNode(false, MinMaxTypeEnum.MAX);
        maxNode.addChildNode(new SupportExprNode(Short.class));
        maxNode.validate(null, null);
        assertEquals(Short.class, maxNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(4-2)
        ExprMathNode arithNodeChild = new ExprMathNode(ArithTypeEnum.SUBTRACT);
        arithNodeChild.addChildNode(new SupportExprNode(4));
        arithNodeChild.addChildNode(new SupportExprNode(2));

        maxNode.addChildNode(arithNodeChild);
        assertEquals("max((4-2))", maxNode.toExpressionString());
        minNode.addChildNode(arithNodeChild);
        assertEquals("min((4-2))", minNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have exactly 1 subnodes
        try
        {
            minNode.validate(null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Must have only number-type subnodes
        minNode.addChildNode(new SupportExprNode(String.class));
        minNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
            minNode.validate(null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testMakeAggregator() throws Exception
    {
        MinMaxTypeEnum type = MinMaxTypeEnum.MAX;
        assertTrue(makeNode(type, 5, Integer.class).getPrototypeAggregator() instanceof ExprMinMaxAggrNode.MinMaxAggregator);
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(minNode.equalsNode(minNode));
        assertFalse(maxNode.equalsNode(minNode));
        assertFalse(minNode.equalsNode(new ExprSumNode(false)));
    }

    private ExprMinMaxAggrNode makeNode(MinMaxTypeEnum minMaxType, Object value, Class type) throws Exception
    {
        ExprMinMaxAggrNode minMaxNode = new ExprMinMaxAggrNode(false, minMaxType);
        minMaxNode.addChildNode(new SupportExprNode(value, type));
        minMaxNode.validate(null, null);
        return minMaxNode;
    }
}
