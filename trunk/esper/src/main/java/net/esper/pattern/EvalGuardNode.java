/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern;

import net.esper.pattern.guard.GuardFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class represents a guard in the evaluation tree representing an event expressions.
 */
public final class EvalGuardNode extends EvalNode
{
    private GuardFactory guardFactory;

    /**
     * Constructor.
     * @param guardFactory - fcatory for guard construction
     */
    public EvalGuardNode(GuardFactory guardFactory)
    {
        this.guardFactory = guardFactory;
    }

    public final EvalStateNode newState(Evaluator parentNode,
                                        MatchedEventMap beginState,
                                        PatternContext context, Object stateNodeId)
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

        return context.getPatternStateFactory().makeGuardState(parentNode, this, beginState, context, stateNodeId);
    }

    /**
     * Returns the guard factory.
     * @return guard factory
     */
    public GuardFactory getGuardFactory()
    {
        return guardFactory;
    }

    public final String toString()
    {
        return ("EvalGuardNode guardFactory=" + guardFactory +
                "  children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalGuardNode.class);
}
