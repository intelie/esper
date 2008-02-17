package com.espertech.esper.epl.join.exec;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.epl.join.table.UnindexedEventTable;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import junit.framework.TestCase;

public class TestTableOuterLookupExecNode extends TestCase
{
    private TableOuterLookupExecNode exec;
    private UnindexedEventTable index;

    public void setUp()
    {
        index = new UnindexedEventTable(0);
        exec = new TableOuterLookupExecNode(1, new FullTableScanLookupStrategy(index));
    }

    public void testFlow()
    {
        EventBean[] lookupEvents = SupportEventBeanFactory.makeMarketDataEvents(new String[] {"a2"});
        List<EventBean[]> result = new LinkedList<EventBean[]>();
        EventBean[] prefill = new EventBean[] {lookupEvents[0], null};

        // Test lookup on empty index, expect 1 row
        exec.process(lookupEvents[0], prefill, result);
        assertEquals(1, result.size());
        EventBean[] events = result.iterator().next();
        assertNull(events[1]);
        assertSame(lookupEvents[0], events[0]);
        result.clear();

        // Test lookup on filled index, expect row2
        EventBean[] indexEvents = SupportEventBeanFactory.makeEvents(new String[] {"a1", "a2"});
        index.add(indexEvents);
        exec.process(lookupEvents[0], prefill, result);
        assertEquals(2, result.size());

        Iterator<EventBean[]> it = result.iterator();

        events = it.next();
        assertSame(lookupEvents[0], events[0]);
        assertTrue((indexEvents[0] == events[1]) || (indexEvents[1] == events[1]));

        events = it.next();
        assertSame(lookupEvents[0], events[0]);
        assertTrue((indexEvents[0] == events[1]) || (indexEvents[1] == events[1]));
    }
}
