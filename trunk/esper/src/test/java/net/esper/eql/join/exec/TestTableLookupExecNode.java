package net.esper.eql.join.exec;

import net.esper.event.BeanEventAdapter;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.eql.join.table.PropertyIndexedEventTable;
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
        exec.process(lookupEvents[0], prefill, result);

        // Test lookup found 1 row
        assertEquals(1, result.size());
        EventBean[] events = result.iterator().next();
        assertSame(indexEvents[1], events[1]);
        assertSame(lookupEvents[0], events[0]);

        // Test lookup found no rows
        result.clear();
        exec.process(lookupEvents[1], prefill, result);
        assertEquals(0, result.size());
    }
}
