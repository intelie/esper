package com.espertech.esper.eql.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.type.MathArithTypeEnum;

public class TestExprMathNode extends TestCase
{
    private ExprMathNode arithNode;

    public void setUp()
    {
        arithNode = new ExprMathNode(MathArithTypeEnum.ADD);
    }

    public void testGetType() throws Exception
    {
        arithNode.addChildNode(new SupportExprNode(Double.class));
        arithNode.addChildNode(new SupportExprNode(Integer.class));
        arithNode.validate(null, null, null, null, null);
        assertEquals(Double.class, arithNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build (5*(4-2)), not the same as 5*4-2
        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT);
        arithNodeChild.addChildNode(new SupportExprNode(4));
        arithNodeChild.addChildNode(new SupportExprNode(2));

        arithNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
        arithNode.addChildNode(new SupportExprNode(5));
        arithNode.addChildNode(arithNodeChild);

        assertEquals("(5*(4-2))", arithNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have exactly 2 subnodes
        try
        {
            arithNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Must have only number-type subnodes
        arithNode.addChildNode(new SupportExprNode(String.class));
        arithNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
            arithNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        arithNode.addChildNode(new SupportExprNode(new Integer(10)));
        arithNode.addChildNode(new SupportExprNode(new Double(1.5)));
        arithNode.getValidatedSubtree(null, null, null, null, null);
        assertEquals(11.5d, arithNode.evaluate(null, false));

        arithNode = makeNode(null, Integer.class, 5d, Double.class);
        assertNull(arithNode.evaluate(null, false));

        arithNode = makeNode(5, Integer.class, null, Double.class);
        assertNull(arithNode.evaluate(null, false));

        arithNode = makeNode(null, Integer.class, null, Double.class);
        assertNull(arithNode.evaluate(null, false));
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(arithNode.equalsNode(arithNode));
        assertFalse(arithNode.equalsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE)));
    }

    private ExprMathNode makeNode(Object valueLeft, Class typeLeft, Object valueRight, Class typeRight)
    {
        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY);
        mathNode.addChildNode(new SupportExprNode(valueLeft, typeLeft));
        mathNode.addChildNode(new SupportExprNode(valueRight, typeRight));
        return mathNode;
    }
}
