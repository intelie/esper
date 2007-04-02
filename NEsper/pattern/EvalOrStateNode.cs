using System;
using System.Collections.Generic;

using net.esper.compat;

using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;

namespace net.esper.pattern
{
    /// <summary>
    /// This class represents the state of a "or" operator in the evaluation state tree.
    /// </summary>

    public sealed class EvalOrStateNode : EvalStateNode, Evaluator
    {
        private readonly ELinkedList<EvalNode> orNodeChildNodes;
        private readonly IList<EvalStateNode> childNodes;

        /// <summary> Constructor.</summary>
        /// <param name="parentNode">is the parent evaluator to call to indicate truth value
        /// </param>
        /// <param name="orNodeChildNodes">are the child nodes of the or-node
        /// </param>
        /// <param name="beginState">contains the events that make up prior matches
        /// </param>
        /// <param name="context">contains handles to services required
        /// </param>
        public EvalOrStateNode(Evaluator parentNode,
        ELinkedList<EvalNode> orNodeChildNodes,
        MatchedEventMap beginState,
        PatternContext context)
            :
                        base(parentNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".constructor");
            }

            this.orNodeChildNodes = orNodeChildNodes;
            this.childNodes = new List<EvalStateNode>();

            // In an "or" expression we need to create states for all child expressions/listeners,
            // since all are going to be Started
            foreach (EvalNode node in orNodeChildNodes)
            {
                EvalStateNode childState = node.NewState(this, beginState, context);
                childNodes.Add(childState);
            }
        }

        /// <summary>
        /// Starts the event expression or an instance of it.
        /// Child classes are expected to initialize and Start any event listeners
        /// or schedule any time-based callbacks as needed.
        /// </summary>
        public override void Start()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".Start Starting or-expression all children, size=" + orNodeChildNodes.Count);
            }

            if (childNodes.Count != orNodeChildNodes.Count)
            {
                throw new SystemException("OR state node does not have the required child state nodes");
            }

            // In an "or" expression we Start all child listeners
            foreach (EvalStateNode child in childNodes)
            {
                child.Start();
            }
        }

        /// <summary>
        /// Indicate a change in truth value to true.
        /// </summary>
        /// <param name="matchEvent">is the container for events that caused the change in truth value</param>
        /// <param name="fromNode">is the node that indicates the change</param>
        /// <param name="isQuitted">is an indication of whether the node continues listenening or Stops listening</param>
        public void EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateTrue fromNode=" + fromNode.GetHashCode());
            }

            // If one of the children quits, the whole or expression turns true and all subexpressions must guardQuit
            if (isQuitted)
            {
                childNodes.Remove(fromNode);
                Quit(); // Quit the remaining listeners
            }

            this.ParentEvaluator.EvaluateTrue(matchEvent, this, isQuitted);
        }

        /// <summary>
        /// Indicate a change in truth value to false.
        /// </summary>
        /// <param name="fromNode">is the node that indicates the change</param>
        public void EvaluateFalse(EvalStateNode fromNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateFalse fromNode=" + fromNode.GetHashCode());
            }
        }

        /// <summary>
        /// Stops the event expression or an instance of it. Child classes are expected to free resources
        /// and Stop any event listeners or remove any time-based callbacks.
        /// </summary>
        public override void Quit()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".guardQuit Stopping all children");
            }

            foreach (EvalStateNode child in childNodes)
            {
                child.Quit();
            }
            childNodes.Clear();
        }

        /// <summary>
        /// Accept a visitor. Child classes are expected to invoke the visit method on the visitor instance
        /// passed in.
        /// </summary>
        /// <param name="visitor">on which the visit method is invoked by each node</param>
        /// <param name="data">any additional data the visitor may need is passed in this parameter</param>
        /// <returns>
        /// any additional data the visitor may need or null
        /// </returns>
        public override Object Accept(EvalStateNodeVisitor visitor, Object data)
        {
            return visitor.Visit(this, data);
        }

        /// <summary>
        /// Pass the visitor to all child nodes.
        /// </summary>
        /// <param name="visitor">is the instance to be passed to all child nodes</param>
        /// <param name="data">any additional data the visitor may need is passed in this parameter</param>
        /// <returns>
        /// any additional data the visitor may need or null
        /// </returns>
        public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
        {
            foreach (EvalStateNode node in childNodes)
            {
                node.Accept(visitor, data);
            }
            return data;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return "EvalOrStateNode nodes=" + childNodes.Count;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalOrStateNode));
    }
}
