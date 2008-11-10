package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.type.MathArithTypeEnum;
import com.espertech.esper.epl.agg.*;

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
        SupportExprNodeFactory.validate(sumNode);
        assertEquals(Integer.class, sumNode.getType());

        sumNode = new ExprSumNode(false);
        sumNode.addChildNode(new SupportExprNode(Float.class));
        SupportExprNodeFactory.validate(sumNode);
        assertEquals(Float.class, sumNode.getType());

        sumNode = new ExprSumNode(false);
        sumNode.addChildNode(new SupportExprNode(Short.class));
        SupportExprNodeFactory.validate(sumNode);
        assertEquals(Integer.class, sumNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(4-2)
        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
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
            sumNode.validate(null, null, null, null, null);
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
            sumNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testMakeAggregator() throws Exception
    {
        assertTrue(makeNode(5, Integer.class).getPrototypeAggregator() instanceof IntegerSumAggregator);
        assertTrue(makeNode(5, Float.class).getPrototypeAggregator() instanceof FloatSumAggregator);
        assertTrue(makeNode(5, Double.class).getPrototypeAggregator() instanceof DoubleSumAggregator);
        assertTrue(makeNode(5, Short.class).getPrototypeAggregator() instanceof NumIntegerSumAggregator);
        assertTrue(makeNode(5, Long.class).getPrototypeAggregator() instanceof LongSumAggregator);
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
        SupportExprNodeFactory.validate(sumNode);
        return sumNode;
    }
}
