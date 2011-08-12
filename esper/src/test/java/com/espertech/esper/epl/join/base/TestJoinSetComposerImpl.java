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

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.join.base.ExecNodeQueryStrategy;
import com.espertech.esper.epl.join.base.JoinSetComposerImpl;
import com.espertech.esper.epl.join.base.QueryStrategy;
import com.espertech.esper.epl.join.exec.base.FullTableScanLookupStrategy;
import com.espertech.esper.epl.join.exec.base.TableLookupExecNode;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestJoinSetComposerImpl extends TestCase
{
    private JoinSetComposerImpl joinSetComposerImpl;
    private EventBean[] indexedEventOne, indexedEventTwo, newEventOne, newEventTwo;
    private UnindexedEventTable indexLeft;
    private UnindexedEventTable indexRight;

    public void setUp()
    {
        indexedEventOne = SupportEventBeanFactory.makeEvents(new String[] { "s1_1", "s1_2"});
        indexedEventTwo = SupportEventBeanFactory.makeEvents(new String[] { "s2_1", "s2_2"});

        newEventOne = SupportEventBeanFactory.makeEvents(new String[] { "s1_3"});
        newEventTwo = SupportEventBeanFactory.makeEvents(new String[] { "s2_3"});

        indexLeft = new UnindexedEventTable(1);
        indexLeft.add(indexedEventOne);
        indexRight = new UnindexedEventTable(1);
        indexRight.add(indexedEventTwo);

        QueryStrategy[] queryStrategies = new QueryStrategy[2];
        TableLookupExecNode lookupLeft = new TableLookupExecNode(1, new FullTableScanLookupStrategy(indexRight));
        TableLookupExecNode lookupRight = new TableLookupExecNode(0, new FullTableScanLookupStrategy(indexLeft));
        queryStrategies[0] = new ExecNodeQueryStrategy(0, 2, lookupLeft);
        queryStrategies[1] = new ExecNodeQueryStrategy(1, 2, lookupRight);

        Map<String,EventTable>[] indexes = new Map[2];
        indexes[0] = new HashMap<String,EventTable>();
        indexes[1] = new HashMap<String,EventTable>();
        indexes[0].put("idxLeft", indexLeft);
        indexes[1].put("idxLeft", indexRight);

        joinSetComposerImpl = new JoinSetComposerImpl(indexes, queryStrategies, false, null);
    }

    public void testJoin()
    {
        // Should return all possible combinations, not matching performed, remember: duplicate pairs have been removed
        UniformPair<Set<MultiKey<EventBean>>> result = joinSetComposerImpl.join(
                new EventBean[][] {newEventOne, newEventTwo},                 // new left and right
                new EventBean[][] {new EventBean[] {indexedEventOne[0]}, new EventBean[] {indexedEventTwo[1]}} // old left and right
                ,null);

        assertEquals(3, result.getFirst().size());      // check old events joined
        String eventStringText = toString(result.getSecond());
        assertTrue(eventStringText.contains("s1_1|s2_1"));
        assertTrue(eventStringText.contains("s1_1|s2_2"));
        assertTrue(eventStringText.contains("s1_2|s2_2"));

        // check new events joined, remember: duplicate pairs have been removed
        assertEquals(3, result.getSecond().size());
        eventStringText = toString(result.getFirst());
        assertTrue(eventStringText.contains("s1_3|s2_1"));
        assertTrue(eventStringText.contains("s1_3|s2_3"));
        assertTrue(eventStringText.contains("s1_2|s2_3"));
    }

    private String toString(Set<MultiKey<EventBean>> events)
    {
        String delimiter = "";
        StringBuffer buf = new StringBuffer();

        for (MultiKey<EventBean> key : events)
        {
            buf.append(delimiter);
            buf.append(toString(key.getArray()));
            delimiter = ",";
        }
        return buf.toString();
    }

    private String toString(EventBean[] events)
    {
        String delimiter = "";
        StringBuffer buf = new StringBuffer();
        for (EventBean event : events)
        {
            buf.append(delimiter);
            buf.append(((SupportBean) event.getUnderlying()).getString());
            delimiter = "|";
        }
        return buf.toString();
    }
}
