using System;
using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;
namespace net.esper.pattern
{
	
	/// <summary> This class represents an 'or' operator in the evaluation tree representing any event expressions.</summary>
	public sealed class EvalOrNode:EvalNode
	{
		public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count <= 1)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected >=2 child node, found " + ChildNodes.Count);
			}
			
			return new EvalOrStateNode(parentNode, this.ChildNodes, beginState, context);
		}
		
		public override String ToString()
		{
			return ("EvalOrNode children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalOrNode));
	}
}