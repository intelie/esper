package com.espertech.esper.eql.join.plan;

import java.util.Arrays;

import junit.framework.TestCase;
import com.espertech.esper.eql.join.exec.IndexedTableLookupStrategy;
import com.espertech.esper.eql.join.exec.TableLookupStrategy;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.PropertyIndexedEventTable;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;

public class TestIndexedTableLookupPlan extends TestCase
{
    private PropertyIndexedEventTable propertyMapEventIndex;
    private EventType[] types;

    public void setUp()
    {
        types = new EventType[] { SupportEventTypeFactory.createBeanType(SupportBean.class) };

        propertyMapEventIndex = new PropertyIndexedEventTable(1, types[0], new String[] {"intBoxed"});
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
