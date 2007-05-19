/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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
     * @param observerFactory is the factory to use to get an observer instance
     */
    public EvalObserverNode(ObserverFactory observerFactory)
    {
        this.observerFactory = observerFactory;
    }

    /**
     * Returns the observer factory.
     * @return factory for observer instances
     */
    public ObserverFactory getObserverFactory()
    {
        return observerFactory;
    }

    public final EvalStateNode newState(Evaluator parentNode,
                                        MatchedEventMap beginState,
                                        PatternContext context,
                                        Object stateNodeId)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".newState");
        }

        if (!getChildNodes().isEmpty())
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected no child nodes, found "
                    + getChildNodes().size());
        }

        return context.getPatternStateFactory().makeObserverNode(parentNode, this, beginState, stateNodeId);
    }

    public final String toString()
    {
        return ("EvalObserverNode observerFactory=" + observerFactory +
                "  children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalObserverNode.class);
}
