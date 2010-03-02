package com.espertech.esper.epl.join.plan;

import java.util.Arrays;

import junit.framework.TestCase;
import com.espertech.esper.epl.join.exec.IndexedTableLookupStrategy;
import com.espertech.esper.epl.join.exec.TableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;

public class TestIndexedTableLookupPlan extends TestCase
{
    private PropertyIndexedEventTable propertyMapEventIndex;
    private EventType[] types;

    public void setUp()
    {
        types = new EventType[] { SupportEventTypeFactory.createBeanType(SupportBean.class) };

        propertyMapEventIndex = new PropertyIndexedEventTable(1, types[0], new String[] {"intBoxed"}, null);
    }

    public void testLookup()
    {
        IndexedTableLookupPlan spec = new IndexedTableLookupPlan(0, 1, 0, new String[] {"intBoxed"});

        EventTable[][] indexes = new EventTable[2][];
        indexes[1] = new EventTable[] {propertyMapEventIndex};

        TableLookupStrategy lookupStrategy = spec.makeStrategy(indexes, types);

        IndexedTableLookupStrategy strategy = (IndexedTableLookupStrategy) lookupStrategy;
        assertEquals(types[0], strategy.getEventType());
        assertEquals(propertyMapEventIndex, strategy.getIndex());
        assertTrue(Arrays.equals(new String[] {"intBoxed"}, strategy.getProperties()));
    }
}
