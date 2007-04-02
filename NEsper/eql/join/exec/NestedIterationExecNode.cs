using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.exec
{
	/// <summary>
    /// Execution node that performs a nested iteration over all child nodes.
	///
	/// Each child node under this node typically represents a table lookup. The implementation
	/// 'hops' from the first child to the next recursively for each row returned by a child.
	///
	/// It passes a 'prototype' row (prefillPath) to each new child which contains the current partial event set. 
	/// </summary>

    public class NestedIterationExecNode : ExecNode
    {
        private readonly ELinkedList<ExecNode> childNodes;
        private readonly int[] nestedStreams;
        private int nestingOrderLength;

        /// <summary>
        /// Initializes a new instance of the <see cref="NestedIterationExecNode"/> class.
        /// </summary>
        /// <param name="nestedStreams">The nested streams.</param>
        public NestedIterationExecNode(int[] nestedStreams)
        {
            this.nestedStreams = nestedStreams;
            this.childNodes = new ELinkedList<ExecNode>();
        }

        /// <summary>
        /// Adds the child node.
        /// </summary>
        /// <param name="childNode">The child node.</param>
        public void AddChildNode(ExecNode childNode)
        {
            childNodes.Add(childNode);
        }

        /// <summary>
        /// Process single event using the prefill events to compile lookup results.
        /// </summary>
        /// <param name="lookupEvent">event to look up for or query for</param>
        /// <param name="prefillPath">set of events currently in the example tuple to serve
        /// as a prototype for result rows.</param>
        /// <param name="result">is the list of tuples to add a result row to</param>
        public override void Process(EventBean lookupEvent, EventBean[] prefillPath, IList<EventBean[]> result)
        {
            nestingOrderLength = childNodes.Count;
            RecursiveNestedJoin(lookupEvent, 0, prefillPath, result);
        }

        /// <summary>Recursive method to run through all child nodes and, for each result set tuple returnedby a child node, execute the inner child of the child node until there are no inner child nodes.</summary>
        /// <param name="lookupEvent">current event to use for lookup by child node</param>
        /// <param name="nestingOrderIndex">index within the child nodes indicating what nesting level we are at</param>
        /// <param name="currentPath">prototype result row to use by child nodes for generating result rows</param>
        /// <param name="result">result tuple rows to be populated</param>

        protected void RecursiveNestedJoin(EventBean lookupEvent, int nestingOrderIndex, EventBean[] currentPath, IList<EventBean[]> result)
        {
            IList<EventBean[]> nestedResult = new List<EventBean[]>();
            ExecNode nestedExecNode = childNodes[nestingOrderIndex];
            nestedExecNode.Process(lookupEvent, currentPath, nestedResult);
            Boolean isLastStream = (nestingOrderIndex == nestingOrderLength - 1);

            // This is not the last nesting level so no result rows are added. Invoke next nesting level for
            // each event found.
            if (!isLastStream)
            {
                foreach (EventBean[] row in nestedResult)
                {
                    EventBean lookup = row[nestedStreams[nestingOrderIndex]];
                    RecursiveNestedJoin(lookup, nestingOrderIndex + 1, row, result);
                }
                return;
            }

            // Loop to add result rows
            foreach (EventBean[] row in nestedResult)
            {
                result.Add(row);
            }
        }

        /// <summary>
        /// Output the execution strategy.
        /// </summary>
        /// <param name="writer">to output to</param>
        public override void Print(IndentWriter writer)
        {
            writer.WriteLine("NestedIterationExecNode");
            writer.IncrIndent();

            foreach (ExecNode child in childNodes)
            {
                child.Print(writer);
            }
            writer.DecrIndent();
        }
    }
}
