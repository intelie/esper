/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.util.ExecutionPathDebugLog;

/**
 * This class represents a followed-by operator in the evaluation tree representing any event expressions.
 */
public final class EvalFollowedByNode extends EvalNode
{
    private static final long serialVersionUID = -3535280879288655577L;

    private transient PatternContext context;

    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context,
                                                 Object stateNodeId)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".newState");
        }

        if (getChildNodes().size() <= 1)
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected >=2 child nodes, found "
                    + getChildNodes().size());
        }

        this.context = context;
        return context.getPatternStateFactory().makeFollowedByState(parentNode, this, beginState, stateNodeId);
    }

    public PatternContext getContext() {
        return context;
    }

    public final String toString()
    {
        return ("EvalFollowedByNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalFollowedByNode.class);
}
