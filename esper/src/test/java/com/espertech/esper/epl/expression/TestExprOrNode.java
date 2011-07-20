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
import com.espertech.esper.type.MinMaxTypeEnum;

public class TestExprOrNode extends TestCase
{
    private ExprOrNode orNode;

    public void setUp()
    {
        orNode = new ExprOrNode();
    }

    public void testGetType()
    {
        assertEquals(Boolean.class, orNode.getType());
    }

    public void testValidate() throws Exception
    {
        // test success
        orNode.addChildNode(new SupportExprNode(Boolean.class));
        orNode.addChildNode(new SupportExprNode(Boolean.class));
        orNode.validate(ExprValidationContextFactory.makeEmpty());

        // test failure, type mismatch
        orNode.addChildNode(new SupportExprNode(String.class));
        try
        {
            orNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // test failed - with just one child
        orNode = new ExprOrNode();
        orNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            orNode.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        orNode.addChildNode(new SupportBoolExprNode(true));
        orNode.addChildNode(new SupportBoolExprNode(false));
        SupportExprNodeUtil.validate(orNode);
        assertTrue( (Boolean) orNode.evaluate(null, false, null));

        orNode = new ExprOrNode();
        orNode.addChildNode(new SupportBoolExprNode(false));
        orNode.addChildNode(new SupportBoolExprNode(false));
        SupportExprNodeUtil.validate(orNode);
        assertFalse( (Boolean) orNode.evaluate(null, false, null));

        orNode = new ExprOrNode();
        orNode.addChildNode(new SupportExprNode(null, Boolean.class));
        orNode.addChildNode(new SupportExprNode(false));
        SupportExprNodeUtil.validate(orNode);
        assertNull(orNode.evaluate(null, false, null));
    }

    public void testToExpressionString() throws Exception
    {
        orNode.addChildNode(new SupportExprNode(true));
        orNode.addChildNode(new SupportExprNode(false));
        assertEquals("(true OR false)", orNode.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(orNode.equalsNode(orNode));
        assertFalse(orNode.equalsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
        assertTrue(orNode.equalsNode(new ExprOrNode()));
    }
}
