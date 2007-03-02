using System;

using net.esper.pattern.guard;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class represents the state of a "within" operator in the evaluation state tree.
	/// The within operator applies to a subexpression and is thus expected to only
	/// have one child node.
	/// </summary>

    public sealed class EvalGuardStateNode:EvalStateNode, Evaluator, Quitable
	{
		private EvalStateNode activeChildNode;
		private readonly Guard guard;
		
		/// <summary> Constructor.</summary>
		/// <param name="parentNode">is the parent evaluator to call to indicate truth value
		/// </param>
		/// <param name="guardFactory">is the factory to use for the guard node
		/// </param>
		/// <param name="singleWithinChildNode">is the single child node of the within node
		/// </param>
		/// <param name="beginState">contains the events that make up prior matches
		/// </param>
		/// <param name="context">contains handles to services required
		/// </param>

        public EvalGuardStateNode(Evaluator parentNode, GuardFactory guardFactory, EvalNode singleWithinChildNode, MatchedEventMap beginState, PatternContext context):base(parentNode)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".constructor");
			}
			
			guard = guardFactory.MakeGuard(context, this);
			
			this.activeChildNode = singleWithinChildNode.newState(this, beginState, context);
		}
		
		public override void  Start()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".Start Starting within timer and single child node");
			}
			
			if (activeChildNode == null)
			{
				throw new SystemException("Invalid state, child state node is inactive");
			}
			
			// Start the single child state
			activeChildNode.Start();
			
			// Start the guard
			guard.StartGuard();
		}
		
		public void  EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".evaluateTrue fromNode=" + fromNode.GetHashCode());
			}
			
			// If one of the children quits, remove the child
			if (isQuitted)
			{
				activeChildNode = null;
				
				// Stop guard, since associated subexpression is gone
				guard.StopGuard();
			}
			
			bool guardPass = guard.inspect(matchEvent);
			if (guardPass)
			{
				this.ParentEvaluator.EvaluateTrue(matchEvent, this, isQuitted);
			}
		}
		
		public void  EvaluateFalse(EvalStateNode fromNode)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".evaluateFalse Removing fromNode=" + fromNode.GetHashCode());
			}
		}
		
		public override void  Quit()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".guardQuit Stopping all children");
			}
			
			if (activeChildNode != null)
			{
				activeChildNode.Quit();
				guard.StopGuard();
			}
			
			activeChildNode = null;
		}
		
		public override Object Accept(EvalStateNodeVisitor visitor, Object data)
		{
			return visitor.visit(this, data);
		}
		
		public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
		{
			if (activeChildNode != null)
			{
				activeChildNode.Accept(visitor, data);
			}
			return data;
		}
		
		public override String ToString()
		{
			return "EvaluationWitinStateNode activeChildNode=" + activeChildNode + " guard=" + guard;
		}
		
		public void  guardQuit()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".guardQuit Guard has quit, Stopping child node, activeChildNode=" + activeChildNode);
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
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalGuardStateNode));
	}
}
