using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// This class represents an 'not' operator in the evaluation tree representing any event expressions.
    /// </summary>
	
    public sealed class EvalNotNode:EvalNode
	{
		public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count != 1)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected 1 child node, found " + ChildNodes.Count);
			}
			
			return new EvalNotStateNode(parentNode, this.ChildNodes[0], beginState, context);
		}
		
		public override String ToString()
		{
			return "EvalNotNode children=" + this.ChildNodes.Count;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalNotNode));
	}
}