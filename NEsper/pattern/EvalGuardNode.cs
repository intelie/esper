using System;

using net.esper.pattern.guard;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary> This class represents a guard in the evaluation tree representing an event expressions.</summary>
	public sealed class EvalGuardNode:EvalNode
	{
		private GuardFactory guardFactory;
		
		/// <summary> Constructor.</summary>
		/// <param name="guardFactory">- factory for guard node
		/// </param>
		public EvalGuardNode(GuardFactory guardFactory)
		{
			this.guardFactory = guardFactory;
		}
		
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
			
			return new EvalGuardStateNode(parentNode, guardFactory, ChildNodes[0], beginState, context);
		}
		
		public override String ToString()
		{
			return ("EvalGuardNode guardFactory=" + guardFactory + "  children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalGuardNode));
	}
}