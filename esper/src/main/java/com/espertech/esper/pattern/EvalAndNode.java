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
 * This class represents an 'and' operator in the evaluation tree representing an event expressions.
 */
public class EvalAndNode extends EvalNodeBase
{
    private static final long serialVersionUID = 6830000101092907359L;

    protected EvalAndNode() {
    }

    public EvalStateNode newState(Evaluator parentNode,
                                  MatchedEventMap beginState,
                                  EvalStateNodeNumber stateNodeId)
    {
        return new EvalAndStateNode(parentNode, this, beginState);
    }

    public final String toString()
    {
        return ("EvalAndNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalAndNode.class);
}
