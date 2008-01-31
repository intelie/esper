package com.espertech.esper.eql.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.eql.join.exec.FullTableScanLookupStrategy;
import com.espertech.esper.eql.join.exec.TableLookupStrategy;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.UnindexedEventTable;

public class TestFullTableScanLookupPlan extends TestCase
{
    private UnindexedEventTable unindexedEventIndex;

    public void setUp()
    {
        unindexedEventIndex = new UnindexedEventTable(0);
    }

    public void testLookup()
    {
        FullTableScanLookupPlan spec = new FullTableScanLookupPlan(0, 1, 2);

        EventTable[][] indexes = new EventTable[2][];
        indexes[1] = new EventTable[] {null, null, unindexedEventIndex};

        TableLookupStrategy lookupStrategy = spec.makeStrategy(indexes, null);

        FullTableScanLookupStrategy strategy = (FullTableScanLookupStrategy) lookupStrategy;
        assertEquals(unindexedEventIndex, strategy.getEventIndex());
    }
}
