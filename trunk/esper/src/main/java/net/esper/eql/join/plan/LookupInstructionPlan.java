package net.esper.eql.join.plan;

import net.esper.eql.join.exec.TableLookupStrategy;
import net.esper.eql.join.exec.LookupInstructionExec;
import net.esper.eql.join.table.EventTable;
import net.esper.event.EventType;
import net.esper.util.IndentWriter;

import java.util.Arrays;

/**
 * Plan for lookup using a from-stream event looking up one or more to-streams using a specified lookup plan for each
 * to-stream.
 */
public class LookupInstructionPlan
{
    private final int fromStream;
    private final String fromStreamName;
    private final int[] toStreams;
    private final TableLookupPlan[] lookupPlans;
    private final boolean[] requiredPerStream;

    /**
     * Ctor.
     * @param fromStream - the stream supplying the lookup event
     * @param fromStreamName - the stream name supplying the lookup event
     * @param toStreams - the set of streams to look up in
     * @param lookupPlans - the plan to use for each stream to look up in
     * @param requiredPerStream - indicates which of the lookup streams are required to build a result and which are not
     */
    public LookupInstructionPlan(int fromStream, String fromStreamName, int[] toStreams, TableLookupPlan[] lookupPlans, boolean[] requiredPerStream)
    {
        if (toStreams.length != lookupPlans.length)
        {
            throw new IllegalArgumentException("Invalid number of lookup plans for each stream");
        }
        if (requiredPerStream.length < lookupPlans.length)
        {
            throw new IllegalArgumentException("Invalid required per stream array");
        }
        if ((fromStream < 0) || (fromStream >= requiredPerStream.length))
        {
            throw new IllegalArgumentException("Invalid from stream");
        }

        this.fromStream = fromStream;
        this.fromStreamName = fromStreamName;
        this.toStreams = toStreams;
        this.lookupPlans = lookupPlans;
        this.requiredPerStream = requiredPerStream;
    }

    /**
     * Constructs the executable from the plan.
     * @param indexesPerStream is the index objects for use in lookups
     * @param streamTypes is the types of each stream
     * @return executable instruction
     */
    public LookupInstructionExec makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
    {
        TableLookupStrategy strategies[] = new TableLookupStrategy[lookupPlans.length];
        for (int i = 0; i < lookupPlans.length; i++)
        {
            strategies[i] = lookupPlans[i].makeStrategy(indexesPerStream, streamTypes);
        }
        return new LookupInstructionExec(fromStream, fromStreamName, toStreams, strategies, requiredPerStream);
    }

    /**
     * Output the planned instruction.
     * @param writer to output to
     */
    public void print(IndentWriter writer)
    {
        writer.println("LookupInstructionPlan" +
                " fromStream=" + fromStream +
                " fromStreamName=" + fromStreamName +
                " toStreams=" + Arrays.toString(toStreams)
                );

        writer.incrIndent();
        for (int i = 0; i < lookupPlans.length; i++)
        {
            writer.println("plan " + i + " :" + lookupPlans[i].toString());
        }
        writer.decrIndent();
    }
}
