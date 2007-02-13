using System;
using System.Collections.Generic;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary> Superclass of all nodes in an evaluation tree representing an event pattern expression.
    /// Follows the Composite pattern. Child nodes do not carry references to parent nodes, the tree
    /// is unidirectional.
    /// </summary>

    public abstract class EvalNode
    {
        private readonly ELinkedList<EvalNode> childNodes;

        /// <summary> Create the evaluation state node containing the truth value state for each operator in an
        /// event expression.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator node that this node indicates a change in truth value to
        /// </param>
        /// <param name="beginState">is the container for events that makes up the Start state
        /// </param>
        /// <param name="context">is the handle to services required for evaluation
        /// </param>
        /// <returns> state node containing the truth value state for the operator
        /// </returns>
        public abstract EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context);

        /// <summary> Constructor creates a list of child nodes.</summary>
        internal EvalNode()
        {
            childNodes = new ELinkedList<EvalNode>();
        }

        /// <summary> Adds a child node.</summary>
        /// <param name="childNode">is the child evaluation tree node to add
        /// </param>
        public void AddChildNode(EvalNode childNode)
        {
            childNodes.Add(childNode);
        }

        /// <summary> Returns list of child nodes.</summary>
        /// <returns> list of child nodes
        /// </returns>
        public ELinkedList<EvalNode> ChildNodes
        {
            get { return childNodes; }
        }

        /// <summary> Recursively print out all nodes.</summary>
        /// <param name="prefix">is printed out for naming the printed info
        /// </param>
        public void DumpDebug(String prefix)
        {
            log.Debug(".DumpDebug " + prefix + this.ToString());
            foreach (EvalNode node in childNodes)
            {
                node.DumpDebug(prefix + "  ");
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalNode));
    }
}