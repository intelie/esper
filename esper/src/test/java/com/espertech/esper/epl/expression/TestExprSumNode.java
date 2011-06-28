package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.type.MathArithTypeEnum;
import com.espertech.esper.epl.agg.*;

public class TestExprSumNode extends TestExprAggregateNodeAdapter
{
    private ExprSumNode sumNode;

    public void setUp() throws Exception
    {
        sumNode = new ExprSumNode(false, false);

        super.validatedNodeToTest = makeNode(5, Integer.class);
    }

    public void testGetType() throws Exception
    {
        sumNode.addChildNode(new SupportExprNode(Integer.class));
        SupportExprNodeFactory.validate3Stream(sumNode);
        assertEquals(Integer.class, sumNode.getType());

        sumNode = new ExprSumNode(false, false);
        sumNode.addChildNode(new SupportExprNode(Float.class));
        SupportExprNodeFactory.validate3Stream(sumNode);
        assertEquals(Float.class, sumNode.getType());

        sumNode = new ExprSumNode(false, false);
        sumNode.addChildNode(new SupportExprNode(Short.class));
        SupportExprNodeFactory.validate3Stream(sumNode);
        assertEquals(Integer.class, sumNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build sum(4-2)
        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT, false, false);
        arithNodeChild.addChildNode(new SupportExprNode(4));
        arithNodeChild.addChildNode(new SupportExprNode(2));

        sumNode = new ExprSumNode(false, false);
        sumNode.addChildNode(arithNodeChild);

        assertEquals("sum((4-2))", sumNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have exactly 1 subnodes
        try
        {
            sumNode.validate(ExprValidationContextFactory.makeEmpty());
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
            sumNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testMakeAggregator() throws Exception
    {
        MethodResolutionService methodSvc = SupportExprNodeFactory.getMethodResService();
        assertTrue(makeNode(5, Integer.class).getFactory().getPrototypeAggregator(methodSvc) instanceof IntegerSumAggregator);
        assertTrue(makeNode(5, Float.class).getFactory().getPrototypeAggregator(methodSvc) instanceof FloatSumAggregator);
        assertTrue(makeNode(5, Double.class).getFactory().getPrototypeAggregator(methodSvc) instanceof DoubleSumAggregator);
        assertTrue(makeNode(5, Short.class).getFactory().getPrototypeAggregator(methodSvc) instanceof NumIntegerSumAggregator);
        assertTrue(makeNode(5, Long.class).getFactory().getPrototypeAggregator(methodSvc) instanceof LongSumAggregator);
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(sumNode.equalsNode(sumNode));
        assertFalse(sumNode.equalsNode(new ExprOrNode()));
    }

    private ExprSumNode makeNode(Object value, Class type) throws Exception
    {
        ExprSumNode sumNode = new ExprSumNode(false, false);
        sumNode.addChildNode(new SupportExprNode(value, type));
        SupportExprNodeFactory.validate3Stream(sumNode);
        return sumNode;
    }
}
