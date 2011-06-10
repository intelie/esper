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
 * This class represents an 'every' operator in the evaluation tree representing an event expression.
 */
public class EvalEveryNode extends EvalNodeBase implements EvalNodeFilterChildNonQuitting
{
    private static final long serialVersionUID = 3672732014060588205L;

    /**
     * Ctor.
     */
    protected EvalEveryNode()
    {
    }

    public EvalStateNode newState(Evaluator parentNode,
                                  MatchedEventMap beginState,
                                  EvalStateNodeNumber stateNodeId)
    {
        return new EvalEveryStateNode(parentNode, this, beginState);
    }

    public final String toString()
    {
        return "EvalEveryNode children=" + this.getChildNodes().size();
    }

    private static final Log log = LogFactory.getLog(EvalEveryNode.class);
}
