using System;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary>
    /// This class represents an 'and' operator in the evaluation tree representing an event expressions.
    /// </summary>

    public sealed class EvalAndNode : EvalNode
    {
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
        public override EvalStateNode NewState(Evaluator parentNode,
											   MatchedEventMap beginState,
											   PatternContext context,
											   Object stateNodeId)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".newState");
            }

            if (ChildNodes.Count <= 1)
            {
                throw new IllegalStateException("Expected number of child nodes incorrect, expected >=2 nodes, found " + ChildNodes.Count);
            }

			return context.PatternStateFactory.MakeAndStateNode(parentNode, this, beginState, stateNodeId);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return ("EvalAndNode children=" + this.ChildNodes.Count);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}