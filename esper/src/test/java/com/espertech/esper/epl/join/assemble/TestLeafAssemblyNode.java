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
import com.espertech.esper.support.epl.join.SupportJoinResultNodeFactory;
import com.espertech.esper.epl.join.rep.Node;

import java.util.List;

import junit.framework.TestCase;

public class TestLeafAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private LeafAssemblyNode leafNode;

    public void setUp()
    {
        leafNode = new LeafAssemblyNode(1, 4);
        parentNode = new SupportJoinProcNode(-1, 4);
        parentNode.addChild(leafNode);
    }

    public void testProcess()
    {
        List<Node>[] result = SupportJoinResultNodeFactory.makeOneStreamResult(4, 1, 2, 2);

        leafNode.process(result);

        assertEquals(4, parentNode.getRowsList().size());
        assertEquals(result[1].get(0).getEvents().iterator().next(), parentNode.getRowsList().get(0)[1]);   // compare event
    }

    public void testChildResult()
    {
        try
        {
            leafNode.result(null, 0, null, null);
            fail();
        }
        catch (UnsupportedOperationException ex)
        {
            // expected
        }
    }
}
