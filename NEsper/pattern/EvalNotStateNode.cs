using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary> This class contains the state of an 'not' operator in the evaluation state tree.
	/// The not operator inverts the truth of the subexpression under it. It defaults to being true rather than
	/// being false at Startup. True at Startup means it will generate an event on newState such that parent expressions
	/// may turn true. It turns permenantly false when it receives an event from a subexpression and the subexpression
	/// quitted. It indicates the false state via an evaluateFalse call on its parent evaluator.
	/// </summary>
	public sealed class EvalNotStateNode:EvalStateNode, Evaluator
	{
		private readonly MatchedEventMap beginState;
		private EvalStateNode childNode;
		
		/// <summary> Constructor.</summary>
		/// <param name="parentNode">is the parent evaluator to call to indicate truth value
		/// </param>
		/// <param name="notNodeChildNode">is the single child node of the not-node
		/// </param>
		/// <param name="beginState">contains the events that make up prior matches
		/// </param>
		/// <param name="context">contains handles to services required
		/// </param>
		public EvalNotStateNode(Evaluator parentNode, EvalNode notNodeChildNode, MatchedEventMap beginState, PatternContext context):base(parentNode)
		{
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".constructor");
			}
			
			this.beginState = beginState.shallowCopy();
			this.childNode = notNodeChildNode.newState(this, beginState, context);
		}
		
		public override void  Start()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".Start Starting single child node");
			}
			
			if (childNode == null)
			{
				throw new SystemException("'Not' state node is inactive");
			}
			
			childNode.Start();
			
			// The not node acts by inverting the truth
			// By default the child nodes are false. This not node acts inverts the truth and pretends the child is true,
			// raising an event up.
			this.ParentEvaluator.EvaluateTrue(beginState, this, false);
		}
		
		public void  EvaluateFalse(EvalStateNode fromNode)
		{
			log.Debug(".evaluateFalse");
		}
		
		public void  EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".evaluateTrue fromNode=" + fromNode.GetHashCode() + "  isQuitted=" + isQuitted);
			}
			
			// Only is the subexpression Stopped listening can we tell the parent evaluator that this
			// turned permanently false.
			if (isQuitted)
			{
				childNode = null;
				this.ParentEvaluator.EvaluateFalse(this);
			}
			else
			{
				// If the subexpression did not guardQuit, we stay in the "true" state
			}
		}
		
		public override void  Quit()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".guardQuit Quitting not-node single child, childNode=" + childNode);
			}
			
			if (childNode != null)
			{
				childNode.Quit();
			}
		}
		
		public override Object Accept(EvalStateNodeVisitor visitor, Object data)
		{
			return visitor.visit(this, data);
		}
		
		public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
		{
			childNode.Accept(visitor, data);
			
			return data;
		}
		
		public override String ToString()
		{
			return "EvalNotStateNode child=" + childNode;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalNotStateNode));
	}
}