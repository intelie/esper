package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.base.ExecNode;
import com.espertech.esper.epl.join.exec.base.ExecNodeNoOp;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.HistoricalStreamIndexList;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.epl.virtualdw.VirtualDWView;
import com.espertech.esper.view.Viewable;

import java.util.HashSet;
import java.util.Map;

public class QueryPlanNodeNoOp extends QueryPlanNode {

    private static final ExecNodeNoOp NOOP = new ExecNodeNoOp();

    public ExecNode makeExec(Map<String, EventTable>[] indexesPerStream, EventType[] streamTypes, Viewable[] streamViews, HistoricalStreamIndexList[] historicalStreamIndexLists, VirtualDWView[] viewExternal) {
        return NOOP;
    }

    public void addIndexes(HashSet<String> usedIndexes) {
    }

    @Override
    protected void print(IndentWriter writer) {
        writer.println("No-Op Execution");
    }
}
