package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.type.MinMaxTypeEnum;

public class TestExprMinMaxRowNode extends TestCase
{
    private ExprMinMaxRowNode minMaxNode;

    public void setUp()
    {
        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
    }

    public void testGetType() throws Exception
    {
        minMaxNode.addChildNode(new SupportExprNode(Double.class));
        minMaxNode.addChildNode(new SupportExprNode(Integer.class));
        minMaxNode.validate(null, null, null, null, null, null);
        assertEquals(Double.class, minMaxNode.getType());

        minMaxNode.addChildNode(new SupportExprNode(Double.class));
        minMaxNode.validate(null, null, null, null, null, null);
        assertEquals(Double.class, minMaxNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        minMaxNode.addChildNode(new SupportExprNode(9d));
        minMaxNode.addChildNode(new SupportExprNode(6));
        assertEquals("max(9.0,6)", minMaxNode.toExpressionString());
        minMaxNode.addChildNode(new SupportExprNode(0.5d));
        assertEquals("max(9.0,6,0.5)", minMaxNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have 2 or more subnodes
        try
        {
            minMaxNode.validate(null, null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Must have only number-type subnodes
        minMaxNode.addChildNode(new SupportExprNode(String.class));
        minMaxNode.addChildNode(new SupportExprNode(Integer.class));
        try
        {
            minMaxNode.validate(null, null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        setupNode(minMaxNode, 10, 1.5, null);
        assertEquals(10d, minMaxNode.evaluate(null, false, null));

        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        setupNode(minMaxNode, 1, 1.5, null);
        assertEquals(1.5d, minMaxNode.evaluate(null, false, null));

        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
        setupNode(minMaxNode, 1, 1.5, null);
        assertEquals(1d, minMaxNode.evaluate(null, false, null));

        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        setupNode(minMaxNode, 1, 1.5, 2.0f);
        assertEquals(2.0d, minMaxNode.evaluate(null, false, null));

        minMaxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MIN);
        setupNode(minMaxNode, 6, 3.5, 2.0f);
        assertEquals(2.0d, minMaxNode.evaluate(null, false, null));

        minMaxNode = makeNode(null, Integer.class, 5, Integer.class, 6, Integer.class);
        assertNull(minMaxNode.evaluate(null, false, null));
        minMaxNode = makeNode(7, Integer.class, null, Integer.class, 6, Integer.class);
        assertNull(minMaxNode.evaluate(null, false, null));
        minMaxNode = makeNode(3, Integer.class, 5, Integer.class, null, Integer.class);
        assertNull(minMaxNode.evaluate(null, false, null));
        minMaxNode = makeNode(null, Integer.class, null, Integer.class, null, Integer.class);
        assertNull(minMaxNode.evaluate(null, false, null));
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(minMaxNode.equalsNode(minMaxNode));
        assertFalse(minMaxNode.equalsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
        assertFalse(minMaxNode.equalsNode(new ExprOrNode()));
    }

    private static void setupNode(ExprMinMaxRowNode nodeMin, int intValue, double doubleValue, Float floatValue) throws Exception
    {
        nodeMin.addChildNode(new SupportExprNode(new Integer(intValue)));
        nodeMin.addChildNode(new SupportExprNode(new Double(doubleValue)));
        if (floatValue != null)
        {
            nodeMin.addChildNode(new SupportExprNode(new Float(floatValue)));
        }
        nodeMin.validate(null, null, null, null, null, null);
    }

    private ExprMinMaxRowNode makeNode(Object valueOne, Class typeOne,
                                       Object valueTwo, Class typeTwo,
                                       Object valueThree, Class typeThree) throws Exception
    {
        ExprMinMaxRowNode maxNode = new ExprMinMaxRowNode(MinMaxTypeEnum.MAX);
        maxNode.addChildNode(new SupportExprNode(valueOne, typeOne));
        maxNode.addChildNode(new SupportExprNode(valueTwo, typeTwo));
        maxNode.addChildNode(new SupportExprNode(valueThree, typeThree));
        maxNode.validate(null, null, null, null, null, null);
        return maxNode;
    }

}
