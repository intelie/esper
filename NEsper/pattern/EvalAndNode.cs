using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
    /// <summary>
    /// This class represents an 'and' operator in the evaluation tree representing an event expressions.
    /// </summary>

    public sealed class EvalAndNode : EvalNode
    {
        public override EvalStateNode newState(Evaluator parentNode, MatchedEventMap beginState, PatternContext context)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".newState");
            }

            if (ChildNodes.Count <= 1)
            {
                throw new SystemException("Expected number of child nodes incorrect, expected >=2 nodes, found " + ChildNodes.Count);
            }

            return new EvalAndStateNode(parentNode, this.ChildNodes, beginState, context);
        }

        public override String ToString()
        {
            return ("EvalAndNode children=" + this.ChildNodes.Count);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EvalAndNode));
    }
}