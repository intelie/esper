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
	
    public sealed class EvalOrStateNode:EvalStateNode, Evaluator
	{
		private readonly ELinkedList < EvalNode > orNodeChildNodes;
		private readonly IList < EvalStateNode > childNodes;
		
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
		ELinkedList < EvalNode > orNodeChildNodes, 
		MatchedEventMap beginState, 
		PatternContext context) :
            			base(parentNode)
        {
			if (log.IsDebugEnabled)
			{
				log.Debug(".constructor");
			}
			
			this.orNodeChildNodes = orNodeChildNodes;
			this.childNodes = new List < EvalStateNode >();
			
			// In an "or" expression we need to create states for all child expressions/listeners,
			// since all are going to be Started
			foreach(EvalNode node in  orNodeChildNodes)
			{
				EvalStateNode childState = node.newState(this, beginState, context);
				childNodes.Add(childState);
			}
        }
		
		public override void  Start()
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
			foreach(EvalStateNode child in  childNodes)
			{
				child.Start();
			}
		}
		
		public void  EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted)
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
		
		public void  EvaluateFalse(EvalStateNode fromNode)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".evaluateFalse fromNode=" + fromNode.GetHashCode());
			}
		}
		
		public override void Quit()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".guardQuit Stopping all children");
			}
			
			foreach(EvalStateNode child in  childNodes)
			{
				child.Quit();
			}
			childNodes.Clear();
		}
		
		public override Object Accept(EvalStateNodeVisitor visitor, Object data)
		{
			return visitor.visit(this, data);
		}
		
		public override Object ChildrenAccept(EvalStateNodeVisitor visitor, Object data)
		{
			foreach(EvalStateNode node in  childNodes)
			{
				node.Accept(visitor, data);
			}
			return data;
		}
		
		public override String ToString()
		{
			return "EvalOrStateNode nodes=" + childNodes.Count;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalOrStateNode));
	}
}
