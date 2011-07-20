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

package com.espertech.esper.epl.join.exec;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.exec.base.IndexedTableLookupStrategy;
import com.espertech.esper.epl.join.exec.base.TableLookupExecNode;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;

import java.util.List;
import java.util.LinkedList;

import junit.framework.TestCase;

public class TestTableLookupExecNode extends TestCase
{
    private TableLookupExecNode exec;
    private PropertyIndexedEventTable index;

    public void setUp()
    {
        EventType eventTypeIndex = SupportEventTypeFactory.createBeanType(SupportBean.class);
        index = new PropertyIndexedEventTable(0, eventTypeIndex, new String[] {"string"});

        EventType eventTypeKeyGen = SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class);

        exec = new TableLookupExecNode(1, new IndexedTableLookupStrategy(eventTypeKeyGen, new String[] {"symbol"}, index));
    }

    public void testFlow()
    {
        EventBean[] indexEvents = SupportEventBeanFactory.makeEvents(new String[] {"a1", "a2"});
        index.add(indexEvents);

        EventBean[] lookupEvents = SupportEventBeanFactory.makeMarketDataEvents(new String[] {"a2", "a3"});

        List<EventBean[]> result = new LinkedList<EventBean[]>();
        EventBean[] prefill = new EventBean[] {lookupEvents[0], null};
        exec.process(lookupEvents[0], prefill, result, null);

        // Test lookup found 1 row
        assertEquals(1, result.size());
        EventBean[] events = result.iterator().next();
        assertSame(indexEvents[1], events[1]);
        assertSame(lookupEvents[0], events[0]);

        // Test lookup found no rows
        result.clear();
        exec.process(lookupEvents[1], prefill, result, null);
        assertEquals(0, result.size());
    }
}
