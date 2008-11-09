package com.espertech.esper.support.epl;

import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.plan.QueryPlanNode;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.HistoricalStreamIndexList;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.view.Viewable;

public class SupportQueryPlanNode extends QueryPlanNode
{
    private String id;

    public SupportQueryPlanNode(String id)
    {
        this.id = id;
    }

    public ExecNode makeExec(EventTable[][] indexPerStream, EventType[] streamTypes, Viewable[] streamViews, HistoricalStreamIndexList[] historicalStreamIndexLists)
    {
        return new SupportQueryExecNode(id);
    }

    protected void print(IndentWriter writer)
    {
        writer.println(this.getClass().getName());
    }
}
