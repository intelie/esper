package net.esper.support.eql;

import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.plan.QueryPlanNode;
import net.esper.eql.join.table.EventTable;
import net.esper.event.EventType;
import net.esper.util.IndentWriter;

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
