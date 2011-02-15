package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.epl.join.exec.FullTableScanLookupStrategy;
import com.espertech.esper.epl.join.exec.TableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;

import java.util.HashMap;
import java.util.Map;

public class TestFullTableScanLookupPlan extends TestCase
{
    private UnindexedEventTable unindexedEventIndex;

    public void setUp()
    {
        unindexedEventIndex = new UnindexedEventTable(0);
    }

    public void testLookup()
    {
        FullTableScanLookupPlan spec = new FullTableScanLookupPlan(0, 1, "idx2");

        Map<String,EventTable>[] indexes = new Map[2];
        indexes[0] = new HashMap<String,EventTable>();
        indexes[1] = new HashMap<String,EventTable>();
        indexes[1].put("idx2", unindexedEventIndex);

        TableLookupStrategy lookupStrategy = spec.makeStrategy(indexes, null);

        FullTableScanLookupStrategy strategy = (FullTableScanLookupStrategy) lookupStrategy;
        assertEquals(unindexedEventIndex, strategy.getEventIndex());
    }
}
