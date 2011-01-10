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

/**
 * This class represents a followed-by operator in the evaluation tree representing any event expressions.
 */
public class EvalFollowedByNode extends EvalNode
{
    private static final long serialVersionUID = -3535280879288655577L;

    private transient PatternContext context;

    protected EvalFollowedByNode() {        
    }

    public EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context,
                                                 EvalStateNodeNumber stateNodeId)
    {
        if (this.context == null) {
            this.context = context;
        }
        return new EvalFollowedByStateNode(parentNode, this, beginState);
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
