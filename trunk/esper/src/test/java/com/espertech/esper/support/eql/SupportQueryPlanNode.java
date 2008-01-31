package com.espertech.esper.support.eql;

import com.espertech.esper.eql.join.exec.ExecNode;
import com.espertech.esper.eql.join.plan.QueryPlanNode;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.IndentWriter;

public class SupportQueryPlanNode extends QueryPlanNode
{
    private String id;

    public SupportQueryPlanNode(String id)
    {
        this.id = id;
    }

    public ExecNode makeExec(EventTable[][] indexPerStream, EventType[] streamTypes)
    {
        return new SupportQueryExecNode(id);
    }

    protected void print(IndentWriter writer)
    {
        writer.println(this.getClass().getName());
    }
}
