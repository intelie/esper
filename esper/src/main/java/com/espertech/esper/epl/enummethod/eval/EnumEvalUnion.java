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

public class EnumEvalUnion implements EnumEval {

    private final EventBean[] events;
    private final ExprEvaluatorEnumeration evaluator;
    private final boolean scalar;

    public EnumEvalUnion(int numStreams, ExprEvaluatorEnumeration evaluator, boolean scalar) {
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
        
        if (set == null || set.isEmpty()) {
            return target;
        }

        ArrayList<Object> result = new ArrayList<Object>(target);
        result.addAll(set);

        return result;
    }
}
