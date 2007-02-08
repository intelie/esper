using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.util;

namespace net.esper.eql.join.plan
{
    /// <summary>
    /// Plan to perform a nested iteration over child nodes.
    /// </summary>

    public class NestedIterationNode : QueryPlanNode
    {
        private readonly List<QueryPlanNode> childNodes;
        private readonly int[] nestingOrder;

        /// <summary> Ctor.</summary>
        /// <param name="nestingOrder">- order of streams in nested iteration
        /// </param>

        public NestedIterationNode(int[] nestingOrder)
        {
            this.nestingOrder = nestingOrder;
            this.childNodes = new List<QueryPlanNode>();

            if (nestingOrder.Length == 0)
            {
                throw new ArgumentException("Invalid empty nesting order");
            }
        }

        /// <summary> Adds a child node.</summary>
        /// <param name="childNode">is the child evaluation tree node to add
        /// </param>

        public void AddChildNode(QueryPlanNode childNode)
        {
            childNodes.Add(childNode);
        }

        /// <summary> Returns list of child nodes.</summary>
        /// <returns> list of child nodes
        /// </returns>

        public IList<QueryPlanNode> ChildNodes
        {
            get { return childNodes; }
        }

        public override ExecNode MakeExec(EventTable[][] indexPerStream, EventType[] streamTypes)
        {
            if (childNodes.Count == 0)
            {
                throw new SystemException("Zero child nodes for nested iteration");
            }

            NestedIterationExecNode execNode = new NestedIterationExecNode(nestingOrder);
            foreach (QueryPlanNode child in childNodes)
            {
                ExecNode childExec = child.MakeExec(indexPerStream, streamTypes);
                execNode.AddChildNode(childExec);
            }

            return execNode;
        }

        public override void Print(IndentWriter indentWriter)
        {
            indentWriter.WriteLine("NestedIterationNode with nesting order " + CollectionHelper.Render(nestingOrder));
            indentWriter.incrIndent();

            foreach (QueryPlanNode child in childNodes)
            {
                child.Print(indentWriter);
            }

            indentWriter.decrIndent();
        }
    }
}