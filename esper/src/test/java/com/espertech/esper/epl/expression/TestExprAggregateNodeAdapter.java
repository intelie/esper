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
import com.espertech.esper.support.epl.SupportAggregationResultFuture;

public abstract class TestExprAggregateNodeAdapter extends TestCase
{
    protected ExprAggregateNode validatedNodeToTest;

    public void testEvaluate() throws Exception
    {
        SupportAggregationResultFuture future = new SupportAggregationResultFuture(new Object[] {10, 20});
        validatedNodeToTest.setAggregationResultFuture(future, 1);

        assertEquals(20, validatedNodeToTest.evaluate(null, false, null));
    }
}

