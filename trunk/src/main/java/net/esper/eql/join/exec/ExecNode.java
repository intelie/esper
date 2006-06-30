package net.esper.eql.join.exec;

import java.util.List;
import java.io.StringWriter;
import java.io.PrintWriter;

import net.esper.event.EventBean;
import net.esper.util.IndentWriter;

/**
 * Interface for an execution node that looks up events and builds a result set contributing to an overall
 * join result set.
 */
public abstract class ExecNode
{
    /**
     * Process single event using the prefill events to compile lookup results.
     * @param lookupEvent - event to look up for or query for
     * @param prefillPath - set of events currently in the example tuple to serve
     * as a prototype for result rows.
     * @param result is the list of tuples to add a result row to
     */
    public abstract void process(EventBean lookupEvent, EventBean[] prefillPath, List<EventBean[]> result);

    /**
     * Output the execution strategy.
     * @param writer to output to
     */
    public abstract void print(IndentWriter writer);

    /**
     * Print in readable format the execution strategy.
     * @param execNode - execution node to print
     * @return readable text with execution nodes constructed for actual streams
     */
    public static String print(ExecNode execNode)
    {
        StringWriter buf = new StringWriter();
        PrintWriter printer = new PrintWriter(buf);
        IndentWriter indentWriter = new IndentWriter(printer, 4, 2);
        execNode.print(indentWriter);

        return buf.toString();
    }


}
