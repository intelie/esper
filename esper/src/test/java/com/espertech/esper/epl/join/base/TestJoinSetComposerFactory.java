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

package com.espertech.esper.epl.join.base;

import com.espertech.esper.client.EventType;
import com.espertech.esper.core.StreamJoinAnalysisResult;
import com.espertech.esper.epl.join.exec.base.TableLookupExecNode;
import com.espertech.esper.epl.join.plan.QueryPlanIndexItem;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.EventTableFactory;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.view.Viewable;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class TestJoinSetComposerFactory extends TestCase
{
    private EventType[] streamTypes;
    private Viewable[] streamViewables;

    public void setUp()
    {
        streamTypes = new EventType[2];
        streamTypes[0] = SupportEventTypeFactory.createBeanType(SupportBean.class);
        streamTypes[1] = SupportEventTypeFactory.createBeanType(SupportBean_A.class);

        streamViewables = new Viewable[2];
    }

    public void testBuildIndex()
    {
        QueryPlanIndexItem item = new QueryPlanIndexItem(new String[] {"intPrimitive", "boolBoxed"}, null, null, null);
        EventTable table = EventTableFactory.buildIndex(0, item, streamTypes[0], false);
        assertTrue(table instanceof PropertyIndexedEventTable);

        item = new QueryPlanIndexItem(new String[0], null, null, null);
        table = EventTableFactory.buildIndex(0, item, streamTypes[0], false);
        assertTrue(table instanceof UnindexedEventTable);

        try
        {
            EventTableFactory.buildIndex(0, null, streamTypes[0], false);
            fail();
        }
        catch (NullPointerException ex)
        {
            // Expected
        }
    }

    public void testBuildComposer() throws Exception
    {
        List<OuterJoinDesc> outerJoins = new LinkedList<OuterJoinDesc>();
        JoinSetComposerDesc desc = (new JoinSetComposerFactoryImpl()).makeComposer(outerJoins, new SupportExprNode(true), streamTypes, new String[]{"a", "b", "c", "d"}, streamViewables, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH, new StreamJoinAnalysisResult(4), null, true, null);
        JoinSetComposerImpl composer = (JoinSetComposerImpl) desc.getJoinSetComposer();

        // verify default indexes build
        assertEquals(2, composer.getTables().length);
        assertTrue(composer.getTables()[0][0] instanceof UnindexedEventTable);
        assertTrue(composer.getTables()[1][0] instanceof UnindexedEventTable);

        // verify default strategies
        assertEquals(2, composer.getQueryStrategies().length);
        ExecNodeQueryStrategy plan = (ExecNodeQueryStrategy) composer.getQueryStrategies()[0];
        assertEquals(0, plan.getForStream());
        assertEquals(2, plan.getNumStreams());
        assertTrue(plan.getExecNode() instanceof TableLookupExecNode);
        plan = (ExecNodeQueryStrategy) composer.getQueryStrategies()[1];
        assertEquals(1, plan.getForStream());
        assertEquals(2, plan.getNumStreams());
        assertTrue(plan.getExecNode() instanceof TableLookupExecNode);
    }


}
