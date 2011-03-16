package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.join.exec.base.ExecNode;
import com.espertech.esper.epl.join.exec.base.FullTableScanLookupStrategy;
import com.espertech.esper.epl.join.exec.base.TableLookupExecNode;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTable;
import com.espertech.esper.view.Viewable;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestTableLookupPlan extends TestCase
{
    public void testMakeExec()
    {
        Map<String,EventTable>[] indexesPerStream = new Map[2];
        indexesPerStream[1] = new HashMap<String,EventTable>();
        indexesPerStream[1].put("idx1", new UnindexedEventTable(0));

        TableLookupNode spec = new TableLookupNode(new FullTableScanLookupPlan(0, 1, "idx1"));
        ExecNode execNode = spec.makeExec(indexesPerStream, null, new Viewable[2], null);
        TableLookupExecNode exec = (TableLookupExecNode) execNode;

        assertSame(indexesPerStream[1].get("idx1"), ((FullTableScanLookupStrategy) exec.getLookupStrategy()).getEventIndex());
        assertEquals(1, exec.getIndexedStream());
    }
}
