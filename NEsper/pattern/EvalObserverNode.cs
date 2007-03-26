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

        /// <summary>
        /// Create the evaluation state node containing the truth value state for each operator in an
        /// event expression.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator node that this node indicates a change in truth value to</param>
        /// <param name="beginState">is the container for events that makes up the Start state</param>
        /// <param name="context">is the handle to services required for evaluation</param>
        /// <returns>
        /// state node containing the truth value state for the operator
        /// </returns>
		public override EvalStateNode NewState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
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

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return ("EvalObserverNode observerFactory=" + observerFactory + "  children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalObserverNode));
	}
}