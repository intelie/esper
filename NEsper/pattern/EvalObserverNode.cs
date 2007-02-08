using System;

using net.esper.pattern.observer;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary> This class represents an observer expression in the evaluation tree representing an pattern expression.</summary>
	public sealed class EvalObserverNode:EvalNode
	{
		private ObserverFactory observerFactory;
		
		/// <summary> Constructor.</summary>
		/// <param name="observerFactory">is the factory to use for the observer instance
		/// </param>
		public EvalObserverNode(ObserverFactory observerFactory)
		{
			this.observerFactory = observerFactory;
		}
		
		public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count != 0)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected no child nodes, found " + ChildNodes.Count);
			}
			
			return new EvalObserverStateNode(parentNode, observerFactory, beginState, context);
		}
		
		public override String ToString()
		{
			return ("EvalObserverNode observerFactory=" + observerFactory + "  children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalObserverNode));
	}
}