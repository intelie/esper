/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.filter.FilterHandleCallback;

import java.util.ArrayDeque;
import java.util.Collection;

/**
 * This class contains the state of a single filter expression in the evaluation state tree.
 */
public final class EvalFilterStateNodeConsumeImpl extends EvalFilterStateNode implements EvalFilterStateNodeConsume
{
    public EvalFilterStateNodeConsumeImpl(Evaluator parentNode, EvalFilterNode evalFilterNode, MatchedEventMap beginState) {
        super(parentNode, evalFilterNode, beginState);
    }

    public final void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches)
    {
        // not receiving the remaining matches simply means we evaluate the event
        if (allStmtMatches == null) {
            super.matchFound(event, null);
            return;
        }

        EvalFilterConsumptionHandler handler = getEvalFilterNode().getContext().getConsumptionHandler();
        processMatches(handler, event, allStmtMatches);
    }

    public static void processMatches(EvalFilterConsumptionHandler handler, EventBean event, Collection<FilterHandleCallback> allStmtMatches) {

        // ignore all other callbacks for the same event
        if (handler.getLastEvent() == event) {
            return;
        }
        handler.setLastEvent(event);

        // evaluate consumption for all same-pattern filters
        ArrayDeque<FilterHandleCallback> matches = new ArrayDeque<FilterHandleCallback>();

        int currentConsumption = Integer.MIN_VALUE;
        for (FilterHandleCallback callback : allStmtMatches) {
            if (!(callback instanceof EvalFilterStateNodeConsume)) {
                continue;
            }
            EvalFilterStateNodeConsume node = (EvalFilterStateNodeConsume) callback;
            Integer consumption = node.getEvalFilterNode().getConsumptionLevel();
            if (consumption == null) {
                consumption = 0;
            }

            if (consumption > currentConsumption) {
                matches.clear();
                currentConsumption = consumption;
            }
            if (consumption == currentConsumption) {
                matches.add(callback);
            }
        }

        // execute matches
        for (FilterHandleCallback match : matches) {
            match.matchFound(event, null);
        }
    }
}
