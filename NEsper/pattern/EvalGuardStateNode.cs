using System;

using net.esper.compat;
using net.esper.pattern.guard;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class represents the state of a "within" operator in the evaluation state tree.
	/// The within operator applies to a subexpression and is thus expected to only
	/// have one child node.
	/// </summary>

    public sealed class EvalGuardStateNode
		: EvalStateNode
		, Evaluator
		, Quitable
    {
        private EvalStateNode activeChildNode;
        private readonly Guard guard;

	    /// <summary>Constructor.</summary>
	    /// <param name="parentNode">
	    /// is the parent evaluator to call to indicate truth value
	    /// </param>
	    /// <param name="beginState">contains the events that make up prior matches</param>
	    /// <param name="context">contains handles to services required</param>
	    /// <param name="evalGuardNode">is the factory node associated to the state</param>
	    /// <param name="stateObjectId">is the state object's id value</param>
	    public EvalGuardStateNode(Evaluator parentNode,
	                              EvalGuardNode evalGuardNode,
								  MatchedEventMap beginState,
								  PatternContext context,
								  Object stateObjectId)
	        : base(evalGuardNode, parentNode, null)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".constructor");
	        }

	        guard = evalGuardNode.GuardFactory.MakeGuard(context, this, stateObjectId, null);

	        this.activeChildNode = evalGuardNode.ChildNodes[0].NewState(this, beginState, context, null);
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
                log.Debug(".Start Starting within timer and single child node");
            }

            if (activeChildNode == null)
            {
                throw new IllegalStateException("Invalid state, child state node is inactive");
            }

            // Start the single child state
            activeChildNode.Start();

            // Start the guard
            guard.StartGuard();
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

	        bool haveQuitted = activeChildNode == null;

            // If one of the children quits, remove the child
            if (isQuitted)
            {
                activeChildNode = null;

                // Stop guard, since associated subexpression is gone
                guard.StopGuard();
            }

	        if (!(haveQuitted))
	        {
	            bool guardPass = guard.Inspect(matchEvent);
	            if (guardPass)
	            {
	                this.ParentEvaluator.EvaluateTrue(matchEvent, this, isQuitted);
	            }
	        }
        }

        /// <summary>
        /// Indicate a change in truth value to false.
        /// </summary>
        /// <param name="fromNode">is the node that indicates the change</param>
        public void EvaluateFalse(EvalStateNode fromNode)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".evaluateFalse Removing fromNode=" + fromNode.GetHashCode());
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
                log.Debug(".Quit Stopping all children");
            }

            if (activeChildNode != null)
            {
                activeChildNode.Quit();
                guard.StopGuard();
            }

            activeChildNode = null;
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
            if (activeChildNode != null)
            {
                activeChildNode.Accept(visitor, data);
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
            return "EvaluationWitinStateNode activeChildNode=" + activeChildNode + " guard=" + guard;
        }

        /// <summary>
        /// Indicate guard quitted.
        /// </summary>

        public void GuardQuit()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".quit Guard has quit, Stopping child node, activeChildNode=" + activeChildNode);
            }

            // It is possible that the child node has already been quit such as when the parent wait time was shorter.
            // 1. parent node's guard indicates quit to all children
            // 2. this node's guards also indicates quit, however that already occured
            if (activeChildNode != null)
            {
                activeChildNode.Quit();
            }
            activeChildNode = null;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
