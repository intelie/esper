package net.esper.pattern;

import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
import net.esper.pattern.observer.ObserverFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class represents the state of an eventObserver sub-expression in the evaluation state tree.
 */
public final class EvalObserverStateNode extends EvalStateNode implements ObserverEventEvaluator
{
    private EventObserver eventObserver;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param observerFactory is the observer factory that makes observer instances
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalObserverStateNode(Evaluator parentNode,
                             ObserverFactory observerFactory,
                                   MatchedEventMap beginState,
                                   PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        eventObserver = observerFactory.makeObserver(context, beginState, this);
    }

    public void observerEvaluateTrue(MatchedEventMap matchEvent)
    {
        this.getParentEvaluator().evaluateTrue(matchEvent, this, true);
    }

    public void observerEvaluateFalse()
    {
        this.getParentEvaluator().evaluateFalse(this);
    }

    public final void start()
    {
        eventObserver.startObserve();
    }

    protected final void quit()
    {
        eventObserver.stopObserve();
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        return data;
    }

    public final String toString()
    {
        return "EvalObserverStateNode eventObserver=" + eventObserver;
    }

    private static final Log log = LogFactory.getLog(EvalObserverStateNode.class);
}
