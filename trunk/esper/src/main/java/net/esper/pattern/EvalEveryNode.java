package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents an 'every' operator in the evaluation tree representing an event expression.
 */
public final class EvalEveryNode extends EvalNode
{
    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".newState");
        }

        if (getChildNodes().size() != 1)
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected 1 node, found "
                    + getChildNodes().size());
        }

        return new EvalEveryStateNode(parentNode, getChildNodes().get(0), beginState, context);
    }

    public final String toString()
    {
        return "EvalEveryNode children=" + this.getChildNodes().size();
    }

    private static final Log log = LogFactory.getLog(EvalEveryNode.class);
}
