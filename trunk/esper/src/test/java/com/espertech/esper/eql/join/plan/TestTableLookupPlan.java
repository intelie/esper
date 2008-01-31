package com.espertech.esper.eql.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.eql.join.exec.ExecNode;
import com.espertech.esper.eql.join.exec.FullTableScanLookupStrategy;
import com.espertech.esper.eql.join.exec.TableLookupExecNode;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.UnindexedEventTable;

public class TestTableLookupPlan extends TestCase
{
    public void testMakeExec()
    {
        EventTable[][] indexesPerStream = new EventTable[2][0];
        indexesPerStream[1] = new EventTable[1];
        indexesPerStream[1][0] = new UnindexedEventTable(0);

        TableLookupNode spec = new TableLookupNode(new FullTableScanLookupPlan(0, 1, 0));
        ExecNode execNode = spec.makeExec(indexesPerStream, null);
        TableLookupExecNode exec = (TableLookupExecNode) execNode;

        assertSame(indexesPerStream[1][0], ((FullTableScanLookupStrategy) exec.getLookupStrategy()).getEventIndex());
        assertEquals(1, exec.getIndexedStream());
    }
}
