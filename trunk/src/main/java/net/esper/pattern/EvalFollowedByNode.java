package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents a followed-by operator in the evaluation tree representing any event expressions.
 */
public final class EvalFollowedByNode extends EvalNode
{
    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".newState");
        }

        if (getChildNodes().size() <= 1)
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected >=2 child nodes, found "
                    + getChildNodes().size());
        }

        return new EvalFollowedByStateNode(parentNode, this.getChildNodes(), beginState, context);
    }

    public final String toString()
    {
        return ("EvalFollowedByNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalFollowedByNode.class);
}