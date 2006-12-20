package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is always the root node in the evaluation tree representing an event expression.
 * It hold the handle to the EPStatement implementation for notifying when matches are found.
 */
public final class EvalRootNode extends EvalNode implements PatternStarter
{
    public final PatternStopCallback start(PatternMatchCallback callback,
                                           PatternContext context)
    {
        MatchedEventMap beginState = new MatchedEventMap();
        EvalRootStateNode rootState = (EvalRootStateNode) newState(null, beginState, context);
        rootState.setCallback(callback);
        rootState.start();
        return rootState;
    }

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

        return new EvalRootStateNode(this.getChildNodes().get(0), beginState, context);
    }

    public final String toString()
    {
        return ("EvalRootNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalRootNode.class);
}
