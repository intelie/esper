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
import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalSumEvents extends EnumEvalBase implements EnumEval {

    private final AggregationMethod aggregationMethod;

    public EnumEvalSumEvents(ExprEvaluator innerExpression, int streamCountIncoming, AggregationMethod aggregationMethod) {
        super(innerExpression, streamCountIncoming);
        this.aggregationMethod = aggregationMethod;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        aggregationMethod.clear();
        
        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object value = innerExpression.evaluate(eventsLambda, isNewData, context);
            aggregationMethod.enter(value);
        }

        return aggregationMethod.getValue();
    }
}
