package net.esper.eql.join.plan;

import java.io.PrintWriter;
import java.io.StringWriter;
import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.table.EventTable;
import net.esper.event.EventType;
import net.esper.util.IndentWriter;

/**
 * Specification node for a query execution plan to be extended by specific execution specification nodes.
 */
public abstract class QueryPlanNode
{
    /**
     * Make execution node from this specification.
     * @param indexesPerStream - tables build for each stream
     * @param streamTypes - event type of each stream
     * @return execution node matching spec
     */
    public abstract ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes);

    /**
     * Print a long readable format of the query node to the supplied PrintWriter.
     * @param writer is the indentation writer to print to
     */
    protected abstract void print(IndentWriter writer);

    /**
     * Print in readable format the execution plan spec.
     * @param execNodeSpecs - plans to print
     * @return readable text with plans
     */
    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend", "StringContatenationInLoop"})
    public static String print(QueryPlanNode[] execNodeSpecs)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("QueryPlanNode[]\n");

        for (int i = 0; i < execNodeSpecs.length; i++)
        {
            buffer.append("  node spec " + i + " :\n");

            StringWriter buf = new StringWriter();
            PrintWriter printer = new PrintWriter(buf);
            IndentWriter indentWriter = new IndentWriter(printer, 4, 2);

            execNodeSpecs[i].print(indentWriter);

            buffer.append(buf.toString());
        }

        return buffer.toString();
    }
}
