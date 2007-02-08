using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.assemble;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.plan
{
    /// <summary> Query plan for executing a set of lookup instructions and assembling an end result via
    /// a set of assembly instructions.
    /// </summary>

    public class LookupInstructionQueryPlanNode : QueryPlanNode
    {
        private readonly int rootStream;
        private readonly String rootStreamName;
        private readonly int numStreams;
        private readonly IList<LookupInstructionPlan> lookupInstructions;
        private readonly bool[] requiredPerStream;
        private readonly IList<BaseAssemblyNode> assemblyInstructions;

        /// <summary> Ctor.</summary>
        /// <param name="rootStream">is the stream supplying the lookup event
        /// </param>
        /// <param name="rootStreamName">is the name of the stream supplying the lookup event
        /// </param>
        /// <param name="numStreams">is the number of streams
        /// </param>
        /// <param name="lookupInstructions">is a list of lookups to perform
        /// </param>
        /// <param name="requiredPerStream">indicates which streams are required and which are optional in the lookup
        /// </param>
        /// <param name="assemblyInstructions">is the bottom-up assembly nodes to assemble a lookup result nodes
        /// </param>

        public LookupInstructionQueryPlanNode(int rootStream,
                                               String rootStreamName,
                                               int numStreams,
                                               Boolean[] requiredPerStream,
                                               IList<LookupInstructionPlan> lookupInstructions,
                                               IList<BaseAssemblyNode> assemblyInstructions)
        {
            this.rootStream = rootStream;
            this.rootStreamName = rootStreamName;
            this.lookupInstructions = lookupInstructions;
            this.numStreams = numStreams;
            this.requiredPerStream = requiredPerStream;
            this.assemblyInstructions = assemblyInstructions;
        }

        public override ExecNode MakeExec(EventTable[][] indexesPerStream, EventType[] streamTypes)
        {
            LookupInstructionExec[] execs = new LookupInstructionExec[lookupInstructions.Count];

            int count = 0;
            foreach (LookupInstructionPlan instruction in lookupInstructions)
            {
                LookupInstructionExec exec = instruction.MakeExec(indexesPerStream, streamTypes);
                execs[count] = exec;
                count++;
            }

            LookupInstructionExecNode execNode = new LookupInstructionExecNode(
                rootStream,
                rootStreamName,
                numStreams,
                execs,
                requiredPerStream,
                CollectionHelper.ToArray(assemblyInstructions)
                );

            return execNode;
        }

        public override void Print(IndentWriter writer)
        {
            writer.WriteLine(
                "LookupInstructionQueryPlanNode" + 
                " rootStream=" + rootStream + 
                " requiredPerStream=" + CollectionHelper.Render(requiredPerStream));

            writer.incrIndent();
            for (int i = 0; i < lookupInstructions.Count; i++)
            {
                writer.WriteLine("lookup step " + i);
                writer.incrIndent();
                lookupInstructions[i].Print(writer);
                writer.decrIndent();
            }
            writer.decrIndent();

            writer.incrIndent();
            for (int i = 0; i < assemblyInstructions.Count; i++)
            {
                writer.WriteLine("assembly step " + i);
                writer.incrIndent();
                assemblyInstructions[i].Print(writer);
                writer.decrIndent();
            }
            writer.decrIndent();
        }
    }
}
