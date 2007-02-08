using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.assemble;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.exec
{
	/// <summary> Execution for a set of lookup instructions and for a set of result assemble instructions to perform
	/// joins and construct a complex result.
	/// </summary>

    public class LookupInstructionExecNode : ExecNode
    {
        private readonly int rootStream;
        private readonly String rootStreamName;
        private readonly int numStreams;
        private readonly bool[] requiredPerStream;
        private readonly LookupInstructionExec[] lookupInstructions;
        private readonly BaseAssemblyNode[] assemblyInstructions;
        private readonly MyResultAssembler myResultAssembler;
        private int requireResultsInstruction;

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

        public LookupInstructionExecNode(int rootStream, String rootStreamName, int numStreams, LookupInstructionExec[] lookupInstructions, bool[] requiredPerStream, BaseAssemblyNode[] assemblyInstructions)
        {
            this.rootStream = rootStream;
            this.rootStreamName = rootStreamName;
            this.numStreams = numStreams;
            this.lookupInstructions = lookupInstructions;
            this.requiredPerStream = requiredPerStream;
            this.assemblyInstructions = assemblyInstructions;

            myResultAssembler = new MyResultAssembler(rootStream);
            assemblyInstructions[assemblyInstructions.Length - 1].ParentAssembler = myResultAssembler;

            // Determine up to which instruction we are dealing with optional results.
            // When dealing with optional results we don't do fast exists if we find no lookup results
            requireResultsInstruction = 1; // we always require results from the very first lookup
            for (int i = 1; i < lookupInstructions.Length; i++)
            {
                int fromStream = lookupInstructions[i].FromStream;
                if (requiredPerStream[fromStream])
                {
                    requireResultsInstruction = i + 1; // require results as long as the from-stream is a required stream
                }
                else
                {
                    break;
                }
            }
        }

        public override void Process(EventBean lookupEvent, EventBean[] prefillPath, IList<EventBean[]> result)
        {
            RepositoryImpl repository = new RepositoryImpl(rootStream, lookupEvent, numStreams);
            Boolean processOptional = true;

            for (int i = 0; i < requireResultsInstruction; i++)
            {
                LookupInstructionExec currentInstruction = lookupInstructions[i];
                Boolean hasResults = currentInstruction.Process(repository);

                // no results, check what to do
                if (!hasResults)
                {
                    // If there was a required stream, we are done.
                    if (currentInstruction.HasRequiredStream)
                    {
                        return;
                    }

                    // If this is the first stream and there are no results, we are done with lookups
                    if (i == 0)
                    {
                        processOptional = false;  // go to result processing
                    }
                }
            }

            if (processOptional)
            {
                for (int i = requireResultsInstruction; i < lookupInstructions.Length; i++)
                {
                    LookupInstructionExec currentInstruction = lookupInstructions[i];
                    currentInstruction.Process(repository);
                }
            }

            // provide a place for the result
            myResultAssembler.setResult(result, lookupEvent);

            // go over the assembly instruction set
            IList<Node>[] results = repository.NodesPerStream;

            // no results - need to execute the very last instruction/top node
            if (results == null)
            {
                BaseAssemblyNode lastAssemblyNode = assemblyInstructions[assemblyInstructions.Length - 1];
                lastAssemblyNode.Init(results);
                lastAssemblyNode.Process(results);
                return;
            }

            // we have results - execute all instructions
            BaseAssemblyNode assemblyNode;
            for (int i = 0; i < assemblyInstructions.Length; i++)
            {
                assemblyNode = assemblyInstructions[i];
                assemblyNode.Init(results);
            }
            for (int i = 0; i < assemblyInstructions.Length; i++)
            {
                assemblyNode = assemblyInstructions[i];
                assemblyNode.Process(results);
            }
        }

        public override void Print(IndentWriter writer)
        {
            writer.WriteLine(
                "LookupInstructionExecNode" +
                " rootStream=" + rootStream + 
                " name=" + rootStreamName +
                " requiredPerStream=" + CollectionHelper.Render(requiredPerStream));

            writer.incrIndent();
            for (int i = 0; i < lookupInstructions.Length; i++)
            {
                writer.WriteLine("lookup inst node " + i);
                writer.incrIndent();
                lookupInstructions[i].Print(writer);
                writer.decrIndent();
            }
            writer.decrIndent();

            writer.incrIndent();
            for (int i = 0; i < assemblyInstructions.Length; i++)
            {
                writer.WriteLine("assembly inst node " + i);
                writer.incrIndent();
                assemblyInstructions[i].Print(writer);
                writer.decrIndent();
            }
            writer.decrIndent();
        }

        /// <summary>
        /// Receives result rows posted by result set assembly nodes.
        /// </summary>

        public class MyResultAssembler : ResultAssembler
        {
            private readonly int m_rootStream;
            private IList<EventBean[]> m_result;
            private EventBean m_rootEvent;

            /// <summary> Ctor.</summary>
            /// <param name="rootStream">is the root stream for which we get results
            /// </param>

            public MyResultAssembler(int rootStream)
            {
				this.m_rootStream = rootStream;
            }

            /// <summary> Supplies the result list to which to add result rows.</summary>
            /// <param name="result">is the list of rows
            /// </param>
            /// <param name="rootEvent">is the event for lookup in other streams
            /// </param>

            public void setResult(IList<EventBean[]> result, EventBean rootEvent)
            {
                this.m_result = result;
                this.m_rootEvent = rootEvent;
            }

            public virtual void Result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode)
            {
                row[m_rootStream] = m_rootEvent;
                m_result.Add(row);
            }
        }
    }
}
