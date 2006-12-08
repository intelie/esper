package net.esper.pattern;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This class represents an 'or' operator in the evaluation tree representing any event expressions.
 */
public final class EvalOrNode extends EvalNode
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
            throw new IllegalStateException("Expected number of child nodes incorrect, expected >=2 child node, found "
                    + getChildNodes().size());
        }

        return new EvalOrStateNode(parentNode, this.getChildNodes(), beginState, context);
    }

    public final String toString()
    {
        return ("EvalOrNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalOrNode.class);
}