/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class contains the state of an 'every' operator in the evaluation state tree.
 * EVERY nodes work as a factory for new state subnodes. When a child node of an EVERY
 * node calls the evaluateTrue method on the EVERY node, the EVERY node will call newState on its child
 * node BEFORE it calls evaluateTrue on its parent node. It keeps a reference to the new child in
 * its list. (BEFORE because the root node could call quit on child nodes for stopping all
 * listeners).
 */
public final class EvalEveryStateSpawnEvaluator implements Evaluator
{
    private boolean isEvaluatedTrue;

    private final String statementName;

    EvalEveryStateSpawnEvaluator(String statementName) {
        this.statementName = statementName;
    }

    public final boolean isEvaluatedTrue()
    {
        return isEvaluatedTrue;
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        log.warn("Event/request processing: Uncontrolled pattern matching of \"every\" operator - infinite loop when using EVERY operator on expression(s) containing a not operator, for statement '" + statementName + "'");
        isEvaluatedTrue = true;
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        log.warn("Event/request processing: Uncontrolled pattern matching of \"every\" operator - infinite loop when using EVERY operator on expression(s) containing a not operator, for statement '" + statementName + "'");
        isEvaluatedTrue = true;
    }

    public boolean isFilterChildNonQuitting() {
        return false;
    }

    private static final Log log = LogFactory.getLog(EvalEveryStateSpawnEvaluator.class);
}

