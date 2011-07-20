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
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EnumEvalTake implements EnumEval {

    private ExprEvaluator sizeEval;
    private EventBean[] events;

    public EnumEvalTake(ExprEvaluator sizeEval, int numStreams) {
        this.sizeEval = sizeEval;
        this.events = new EventBean[numStreams];
    }

    public EventBean[] getEventsPrototype() {
        return events;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        Object sizeObj = sizeEval.evaluate(events, isNewData, context);
        if (sizeObj == null) {
            return null;
        }

        if (target.isEmpty()) {
            return target;
        }

        int size = ((Number) sizeObj).intValue();
        if (size <= 0) {
            return Collections.emptyList();
        }

        if (target.size() < size) {
            return target;
        }

        if (size == 1) {
            return Collections.singletonList(target.iterator().next());
        }

        ArrayList<Object> result = new ArrayList<Object>(size);
        for (Object next : target) {
            if (result.size() >= size) {
                break;
            }
            result.add(next);
        }
        return result;
    }
}
