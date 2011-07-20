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
import com.espertech.esper.support.epl.SupportBoolExprNode;

public class TestExprAndNode extends TestCase
{
    private ExprAndNode andNode;

    public void setUp()
    {
        andNode = new ExprAndNodeImpl();
    }

    public void testGetType()
    {
        assertEquals(Boolean.class, andNode.getType());
    }

    public void testValidate() throws Exception
    {
        // test success
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        andNode.validate(ExprValidationContextFactory.makeEmpty());

        // test failure, type mismatch
        andNode.addChildNode(new SupportExprNode(String.class));
        try
        {
            andNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // test failed - with just one child
        andNode = new ExprAndNodeImpl();
        andNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            andNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(true));
        SupportExprNodeUtil.validate(andNode);
        assertTrue( (Boolean) andNode.evaluate(null, false, null));

        andNode = new ExprAndNodeImpl();
        andNode.addChildNode(new SupportBoolExprNode(true));
        andNode.addChildNode(new SupportBoolExprNode(false));
        SupportExprNodeUtil.validate(andNode);
        assertFalse( (Boolean) andNode.evaluate(null, false, null));
    }

    public void testToExpressionString() throws Exception
    {
        andNode.addChildNode(new SupportExprNode(true));
        andNode.addChildNode(new SupportExprNode(false));

        assertEquals("(true AND false)", andNode.toExpressionString());
    }

    public void testEqualsNode()
    {
        assertTrue(andNode.equalsNode(new ExprAndNodeImpl()));
        assertFalse(andNode.equalsNode(new ExprOrNode()));
    }
}
