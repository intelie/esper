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

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

public class TestExprVariableNode extends TestCase
{
    private ExprVariableNode varNode;

    public void setUp() throws Exception
    {
        varNode = new ExprVariableNode("var1");
    }

    public void testGetType()  throws Exception
    {
        SupportExprNodeFactory.validate3Stream(varNode);
        assertEquals(String.class, varNode.getType());
    }

    public void testEvaluate() throws Exception
    {
        SupportExprNodeFactory.validate3Stream(varNode);
        assertEquals("my_variable_value", varNode.evaluate(null, true, null));
    }

    public void testValidate() throws Exception
    {
        // variable doesn't exists
        tryInvalidValidate(new ExprVariableNode("dummy"));

        // variable and property are ambigours
        tryInvalidValidate(new ExprVariableNode("intPrimitive"));
    }

    public void testEquals()  throws Exception
    {
        ExprInNode otherInNode = SupportExprNodeFactory.makeInSetNode(false);
        ExprVariableNode otherVarOne = new ExprVariableNode("dummy");
        ExprVariableNode otherVarTwo = new ExprVariableNode("var1");
        ExprVariableNode otherVarThree = new ExprVariableNode("var1.abc");

        assertTrue(varNode.equalsNode(varNode));
        assertTrue(varNode.equalsNode(otherVarTwo));
        assertFalse(varNode.equalsNode(otherVarOne));
        assertFalse(varNode.equalsNode(otherInNode));
        assertFalse(otherVarTwo.equalsNode(otherVarThree));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("var1", varNode.toExpressionString());
    }

    private void tryInvalidValidate(ExprVariableNode varNode) throws Exception
    {
        try {
            SupportExprNodeFactory.validate3Stream(varNode);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
