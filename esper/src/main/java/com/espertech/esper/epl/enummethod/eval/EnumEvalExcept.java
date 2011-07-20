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

package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprEvaluatorEnumeration;

import java.util.ArrayList;
import java.util.Collection;

public class EnumEvalExcept implements EnumEval {

    private final EventBean[] events;
    private final ExprEvaluatorEnumeration evaluator;
    private final boolean scalar;

    public EnumEvalExcept(int numStreams, ExprEvaluatorEnumeration evaluator, boolean scalar) {
        this.events = new EventBean[numStreams];
        this.evaluator = evaluator;
        this.scalar = scalar;
    }

    public EventBean[] getEventsPrototype() {
        return events;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        if (target == null) {
            return null;
        }

        Collection set;
        if (scalar) {
            set = evaluator.evaluateGetROCollectionScalar(events, isNewData, context);
        }
        else {
            set = evaluator.evaluateGetROCollectionEvents(events, isNewData, context);
        }
        
        if (set == null || set.isEmpty() || target.isEmpty()) {
            return target;
        }

        if (scalar) {
            ArrayList<Object> result = new ArrayList<Object>(target);
            result.removeAll(set);
            return result;
        }

        Collection<EventBean> targetEvents = (Collection<EventBean>) target;
        Collection<EventBean> sourceEvents = (Collection<EventBean>) set;
        ArrayList<EventBean> result = new ArrayList<EventBean>();

        // we compare event underlying
        for (EventBean targetEvent : targetEvents) {
            if (targetEvent == null) {
                result.add(null);
                continue;
            }

            boolean found = false;
            for (EventBean sourceEvent : sourceEvents) {
                if (targetEvent == sourceEvent) {
                    found = true;
                    break;
                }
                if (sourceEvent == null) {
                    continue;
                }
                if (targetEvent.getUnderlying().equals(sourceEvent.getUnderlying())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.add(targetEvent);
            }
        }
        return result;
    }
}
