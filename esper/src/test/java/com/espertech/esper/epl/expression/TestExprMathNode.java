/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNodeUtil;
import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.type.MathArithTypeEnum;

public class TestExprMathNode extends TestCase
{
    private ExprMathNode arithNode;

    public void setUp()
    {
        arithNode = new ExprMathNode(MathArithTypeEnum.ADD, false, false);
    }

    public void testGetType() throws Exception
    {
        arithNode.addChildNode(new SupportExprNode(Double.class));
        arithNode.addChildNode(new SupportExprNode(Integer.class));
        arithNode.validate(ExprValidationContextFactory.makeEmpty());
        assertEquals(Double.class, arithNode.getType());
    }

    public void testToExpressionString() throws Exception
    {
        // Build (5*(4-2)), not the same as 5*4-2
        ExprMathNode arithNodeChild = new ExprMathNode(MathArithTypeEnum.SUBTRACT, false, false);
        arithNodeChild.addChildNode(new SupportExprNode(4));
        arithNodeChild.addChildNode(new SupportExprNode(2));

        arithNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY, false, false);
        arithNode.addChildNode(new SupportExprNode(5));
        arithNode.addChildNode(arithNodeChild);

        assertEquals("(5*(4-2))", arithNode.toExpressionString());
    }

    public void testValidate()
    {
        // Must have exactly 2 subnodes
        try
        {
            arithNode.validate(ExprValidationContextFactory.makeEmpty());
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
            arithNode.validate(ExprValidationContextFactory.makeEmpty());
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
        ExprNodeUtility.getValidatedSubtree(arithNode, ExprValidationContextFactory.makeEmpty());
        assertEquals(11.5d, arithNode.evaluate(null, false, null));

        arithNode = makeNode(null, Integer.class, 5d, Double.class);
        assertNull(arithNode.evaluate(null, false, null));

        arithNode = makeNode(5, Integer.class, null, Double.class);
        assertNull(arithNode.evaluate(null, false, null));

        arithNode = makeNode(null, Integer.class, null, Double.class);
        assertNull(arithNode.evaluate(null, false, null));
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(arithNode.equalsNode(arithNode));
        assertFalse(arithNode.equalsNode(new ExprMathNode(MathArithTypeEnum.DIVIDE, false, false)));
    }

    private ExprMathNode makeNode(Object valueLeft, Class typeLeft, Object valueRight, Class typeRight) throws Exception
    {
        ExprMathNode mathNode = new ExprMathNode(MathArithTypeEnum.MULTIPLY, false, false);
        mathNode.addChildNode(new SupportExprNode(valueLeft, typeLeft));
        mathNode.addChildNode(new SupportExprNode(valueRight, typeRight));
        SupportExprNodeUtil.validate(mathNode);
        return mathNode;
    }
}
