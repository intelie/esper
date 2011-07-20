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

package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.epl.join.plan.NestedIterationNode;

public class TestNestedIterationNode extends TestCase
{
    public void testMakeExec()
    {
        try
        {
            new NestedIterationNode(new int[] {});
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
