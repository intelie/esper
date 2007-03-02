using System;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

using net.esper.util;

namespace net.esper.pattern
{
	/// <summary>
	/// This class is always the root node in the evaluation tree representing an event expression.
	/// It hold the handle to the EPStatement implementation for notifying when matches are found.
	/// </summary>

	public sealed class EvalRootNode : EvalNode, PatternStarter
	{
		public StopCallback Start(PatternMatchCallback callback, PatternContext context)
		{
			MatchedEventMap beginState = new MatchedEventMap();
			EvalRootStateNode rootState = (EvalRootStateNode) newState(null, beginState, context);
			rootState.Callback = callback;
			rootState.Start();
			return rootState.Stop;
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
			
			return new EvalRootStateNode(this.ChildNodes[0], beginState, context);
		}
		
		public override String ToString()
		{
			return ("EvalRootNode children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalRootNode));
	}
}
