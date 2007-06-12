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

	public sealed class EvalRootNode
		: EvalNode
		, PatternStarter
	{
        /// <summary>
        /// Starts the specified callback.
        /// </summary>
        /// <param name="callback">The callback.</param>
        /// <param name="context">The context.</param>
        /// <returns></returns>
		public PatternStopCallback Start(PatternMatchCallback callback, PatternContext context)
		{
			MatchedEventMap beginState = new MatchedEventMapImpl();
			EvalStateNode rootStateNode = NewState(null, beginState, context, null);
			EvalRootState rootState = (EvalRootState) rootStateNode;
			rootState.Callback = callback;
			rootStateNode.Start();
			return rootState;
		}

        /// <summary>
        /// Create the evaluation state node containing the truth value state for each operator in an
        /// event expression.
        /// </summary>
        /// <param name="parentNode">is the parent evaluator node that this node indicates a change in truth value to</param>
        /// <param name="beginState">is the container for events that makes up the Start state</param>
        /// <param name="context">is the handle to services required for evaluation</param>
        /// <param name="stateNodeId">is the new state object's identifier</param>
        /// <returns>
        /// state node containing the truth value state for the operator
        /// </returns>
		public override EvalStateNode NewState(
			Evaluator parentNode,
			MatchedEventMap beginState, 
			PatternContext context,
			Object stateNodeId)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".newState");
			}
			
			if (ChildNodes.Count != 1)
			{
				throw new SystemException("Expected number of child nodes incorrect, expected 1 child node, found " + ChildNodes.Count);
			}
			
			return context.PatternStateFactory.MakeRootNode(this.ChildNodes[0], beginState);
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return ("EvalRootNode children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
