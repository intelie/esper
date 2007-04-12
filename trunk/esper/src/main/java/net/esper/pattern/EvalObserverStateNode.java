/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern;

import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
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
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     * @param evalObserverNode is the factory node associated to the state
     */
    public EvalObserverStateNode(Evaluator parentNode,
                             EvalObserverNode evalObserverNode,
                                   MatchedEventMap beginState,
                                   PatternContext context)
    {
        super(evalObserverNode, parentNode, null);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        eventObserver = evalObserverNode.getObserverFactory().makeObserver(context, beginState, this, null, null);
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

    public final void quit()
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
