using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class represents an 'every' operator in the evaluation tree representing an event expression.
    /// </summary>
	
    public sealed class EvalEveryNode:EvalNode
	{
		public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count != 1)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected 1 node, found " + ChildNodes.Count);
			}
			
			return new EvalEveryStateNode(parentNode, ChildNodes[0], beginState, context);
		}
		
		public override String ToString()
		{
			return "EvalEveryNode children=" + this.ChildNodes.Count;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalEveryNode));
	}
}