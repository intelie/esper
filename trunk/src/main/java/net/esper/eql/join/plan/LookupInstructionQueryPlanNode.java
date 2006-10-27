package net.esper.eql.join.plan;

import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.exec.LookupInstructionExecNode;
import net.esper.eql.join.exec.LookupInstructionExec;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.assemble.BaseAssemblyNode;
import net.esper.event.EventType;
import net.esper.util.IndentWriter;

import java.util.List;
import java.util.Arrays;

/**
 * Query plan for executing a set of lookup instructions and assembling an end result via
 * a set of assembly instructions.
 */
public class LookupInstructionQueryPlanNode extends QueryPlanNode
{
    private final int rootStream;
    private final String rootStreamName;
    private final int numStreams;
    private final List<LookupInstructionPlan> lookupInstructions;
    private final boolean[] requiredPerStream;
    private final List<BaseAssemblyNode> assemblyInstructions;

    /**
     * Ctor.
     * @param rootStream is the stream supplying the lookup event
     * @param rootStreamName is the name of the stream supplying the lookup event
     * @param numStreams is the number of streams
     * @param lookupInstructions is a list of lookups to perform
     * @param requiredPerStream indicates which streams are required and which are optional in the lookup
     * @param assemblyInstructions is the bottom-up assembly nodes to assemble a lookup result nodes
     */
    public LookupInstructionQueryPlanNode(int rootStream,
                                          String rootStreamName,
                                    int numStreams,
                                    boolean[] requiredPerStream,
                                    List<LookupInstructionPlan> lookupInstructions,
                                    List<BaseAssemblyNode> assemblyInstructions)
    {
        this.rootStream = rootStream;
        this.rootStreamName = rootStreamName;
        this.lookupInstructions = lookupInstructions;
        this.numStreams = numStreams;
        this.requiredPerStream = requiredPerStream;
        this.assemblyInstructions = assemblyInstructions;
    }

    public ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
    {
        LookupInstructionExec execs[] = new LookupInstructionExec[lookupInstructions.size()];

        int count = 0;
        for (LookupInstructionPlan instruction : lookupInstructions)
        {
            LookupInstructionExec exec = instruction.makeExec(indexesPerStream, streamTypes);
            execs[count] = exec;
            count++;
        }

        LookupInstructionExecNode execNode = new LookupInstructionExecNode(rootStream, rootStreamName,
                numStreams, execs, requiredPerStream, assemblyInstructions.toArray(new BaseAssemblyNode[0]));

        return execNode;
    }

    protected void print(IndentWriter writer)
    {
        writer.println("LookupInstructionQueryPlanNode" +
                " rootStream=" + rootStream +
                " requiredPerStream=" + Arrays.toString(requiredPerStream));

        writer.incrIndent();
        for (int i = 0; i < lookupInstructions.size(); i++)
        {
            writer.println("lookup step " + i);
            writer.incrIndent();
            lookupInstructions.get(i).print(writer);
            writer.decrIndent();
        }
        writer.decrIndent();

        writer.incrIndent();
        for (int i = 0; i < assemblyInstructions.size(); i++)
        {
            writer.println("assembly step " + i);
            writer.incrIndent();
            assemblyInstructions.get(i).print(writer);
            writer.decrIndent();
        }
        writer.decrIndent();
    }
}
