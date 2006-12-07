package net.esper.pattern;

import net.esper.pattern.observer.ObserverFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class represents an observer expression in the evaluation tree representing an pattern expression.
 */
public final class EvalObserverNode extends EvalNode
{
    private ObserverFactory observerFactory;

    /**
     * Constructor.
     * @param observerFactory is the factory to use for the observer instance
     */
    public EvalObserverNode(ObserverFactory observerFactory)
    {
        this.observerFactory = observerFactory;
    }

    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".newState");
        }

        if (getChildNodes().size() != 0)
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected no child nodes, found "
                    + getChildNodes().size());
        }

        return new EvalObserverStateNode(parentNode, observerFactory, beginState, context);
    }

    public final String toString()
    {
        return ("EvalObserverNode observerFactory=" + observerFactory +
                "  children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalObserverNode.class);
}
