package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents an 'not' operator in the evaluation tree representing any event expressions.
 */
public final class EvalNotNode extends EvalNode
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
            throw new IllegalStateException("Expected number of child nodes incorrect, expected 1 child node, found "
                    + getChildNodes().size());
        }

        return new EvalNotStateNode(parentNode, this.getChildNodes().get(0), beginState, context);
    }

    public final String toString()
    {
        return "EvalNotNode children=" + this.getChildNodes().size();
    }

    private static final Log log = LogFactory.getLog(EvalNotNode.class);
}
