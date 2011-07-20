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

import com.espertech.esper.epl.agg.AvgAggregator;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

public class TestExprAvgNode extends TestExprAggregateNodeAdapter
{
    private ExprAvgNode avgNodeDistinct;

    public void setUp() throws Exception
    {
        super.validatedNodeToTest = makeNode(5, Integer.class, false);
        this.avgNodeDistinct = makeNode(6, Integer.class, true);
    }

    public void testAggregation()
    {
        AvgAggregator agg = new AvgAggregator();
        assertEquals(Double.class, agg.getValueType());
        assertEquals(null, agg.getValue());

        agg.enter(5);
        assertEquals(5d, agg.getValue());

        agg.enter(10);
        assertEquals(7.5d, agg.getValue());

        agg.leave(5);
        assertEquals(10d, agg.getValue());
    }

    public void testGetType() throws Exception
    {
        assertEquals(Double.class, validatedNodeToTest.getType());
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("avg(5)", validatedNodeToTest.toExpressionString());
        assertEquals("avg(distinct 6)", avgNodeDistinct.toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        assertTrue(validatedNodeToTest.equalsNode(validatedNodeToTest));
        assertFalse(validatedNodeToTest.equalsNode(new ExprSumNode(false, false)));
    }

    private ExprAvgNode makeNode(Object value, Class type, boolean isDistinct) throws Exception
    {
        ExprAvgNode avgNode = new ExprAvgNode(isDistinct, false);
        avgNode.addChildNode(new SupportExprNode(value, type));
        SupportExprNodeFactory.validate3Stream(avgNode);
        return avgNode;
    }
}
