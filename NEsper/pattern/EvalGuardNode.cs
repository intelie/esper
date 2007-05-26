using System;

using net.esper.pattern.guard;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary> This class represents a guard in the evaluation tree representing an event expressions.</summary>
	public sealed class EvalGuardNode:EvalNode
	{
		private GuardFactory guardFactory;
		
		/// <summary>
		/// Gets the guard factory.
		/// </summary>
		
	    public GuardFactory GuardFactory
	    {
	        get { return guardFactory; }
	    }
	
		/// <summary> Constructor.</summary>
		/// <param name="guardFactory">factory for guard construction
		/// </param>
		public EvalGuardNode(GuardFactory guardFactory)
		{
			this.guardFactory = guardFactory;
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
		public override EvalStateNode NewState(Evaluator parentNode,
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
			
			return context.PatternStateFactory.MakeGuardState(parentNode, this, beginState, context, stateNodeId);
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return ("EvalGuardNode guardFactory=" + guardFactory + "  children=" + this.ChildNodes.Count);
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}