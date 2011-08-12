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

package com.espertech.esper.epl.join.assemble;

import com.espertech.esper.support.epl.join.SupportJoinProcNode;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestBaseAssemblyNode extends TestCase
{
    public void testGetSubstreams()
    {
        SupportJoinProcNode top = new SupportJoinProcNode(2, 3);

        SupportJoinProcNode child_1 = new SupportJoinProcNode(5, 3);
        SupportJoinProcNode child_2 = new SupportJoinProcNode(1, 3);
        top.addChild(child_1);
        top.addChild(child_2);

        SupportJoinProcNode child_1_1 = new SupportJoinProcNode(6, 3);
        SupportJoinProcNode child_1_2 = new SupportJoinProcNode(7, 3);
        child_1.addChild(child_1_1);
        child_1.addChild(child_1_2);

        SupportJoinProcNode child_1_1_1 = new SupportJoinProcNode(0, 3);
        child_1_1.addChild(child_1_1_1);

        int[] result = top.getSubstreams();
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2, 5, 1, 6, 7, 0}, result);
    }
}
