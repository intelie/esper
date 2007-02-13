using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.exec
{
	/// <summary>
    /// Execution node that performs a nested iteration over all child nodes.
	/// <p>
	/// Each child node under this node typically represents a table lookup. The implementation
	/// 'hops' from the first child to the next recursively for each row returned by a child.
	/// <p>
	/// It passes a 'prototype' row (prefillPath) to each new child which contains the current partial event set. 
	/// </summary>

    public class NestedIterationExecNode : ExecNode
    {
        private readonly ELinkedList<ExecNode> childNodes;
        private readonly int[] nestedStreams;
        private int nestingOrderLength;

        /**
         * Ctor.
         * @param nestedStreams - array of integers defining order of streams in nested join.
         */
        public NestedIterationExecNode(int[] nestedStreams)
        {
            this.nestedStreams = nestedStreams;
            this.childNodes = new ELinkedList<ExecNode>();
        }

        /**
         * Add a child node.
         * @param childNode to add
         */
        public void AddChildNode(ExecNode childNode)
        {
            childNodes.Add(childNode);
        }

        public override void Process(EventBean lookupEvent, EventBean[] prefillPath, IList<EventBean[]> result)
        {
            nestingOrderLength = childNodes.Count;
            recursiveNestedJoin(lookupEvent, 0, prefillPath, result);
        }

        /**
         * Recursive method to run through all child nodes and, for each result set tuple returned
         * by a child node, execute the inner child of the child node until there are no inner child nodes.
         * @param lookupEvent - current event to use for lookup by child node
         * @param nestingOrderIndex - index within the child nodes indicating what nesting level we are at
         * @param currentPath - prototype result row to use by child nodes for generating result rows
         * @param result - result tuple rows to be populated   
         */
        protected void recursiveNestedJoin(EventBean lookupEvent, int nestingOrderIndex, EventBean[] currentPath, IList<EventBean[]> result)
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
                    recursiveNestedJoin(lookup, nestingOrderIndex + 1, row, result);
                }
                return;
            }

            // Loop to add result rows
            foreach (EventBean[] row in nestedResult)
            {
                result.Add(row);
            }
        }

        public override void Print(IndentWriter writer)
        {
            writer.WriteLine("NestedIterationExecNode");
            writer.incrIndent();

            foreach (ExecNode child in childNodes)
            {
                child.Print(writer);
            }
            writer.decrIndent();
        }
    }
}
