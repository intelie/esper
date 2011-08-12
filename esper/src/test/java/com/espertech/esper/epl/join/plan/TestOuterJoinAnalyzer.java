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

import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.support.epl.SupportOuterJoinDescFactory;
import com.espertech.esper.type.OuterJoinType;
import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;

public class TestOuterJoinAnalyzer extends TestCase
{
    public void testAnalyze() throws Exception
    {
        List<OuterJoinDesc> descList = new LinkedList<OuterJoinDesc>();
        descList.add(SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT));
        descList.add(SupportOuterJoinDescFactory.makeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.LEFT));
        // simpleProperty in s2

        QueryGraph graph = new QueryGraph(3);
        OuterJoinAnalyzer.analyze(descList, graph);
        assertEquals(3, graph.getNumStreams());

        assertTrue(graph.isNavigableAtAll(0, 1));
        assertEquals(1, QueryGraphTestUtil.getStrictKeyProperties(graph, 0, 1).length);
        assertEquals("intPrimitive", QueryGraphTestUtil.getStrictKeyProperties(graph, 0, 1)[0]);
        assertEquals(1, QueryGraphTestUtil.getStrictKeyProperties(graph, 1, 0).length);
        assertEquals("intBoxed", QueryGraphTestUtil.getStrictKeyProperties(graph, 1, 0)[0]);

        assertTrue(graph.isNavigableAtAll(1, 2));
        assertEquals("string", QueryGraphTestUtil.getStrictKeyProperties(graph, 1, 2)[0]);
        assertEquals("simpleProperty", QueryGraphTestUtil.getStrictKeyProperties(graph, 2, 1)[0]);
    }
}
