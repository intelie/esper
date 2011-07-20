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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnumEvalToMap extends EnumEvalBase implements EnumEval {

    private ExprEvaluator secondExpression;

    public EnumEvalToMap(ExprEvaluator innerExpression, int streamCountIncoming, ExprEvaluator secondExpression) {
        super(innerExpression, streamCountIncoming);
        this.secondExpression = secondExpression;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Map map = new HashMap();

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object key = innerExpression.evaluate(eventsLambda, isNewData, context);
            Object value = secondExpression.evaluate(eventsLambda, isNewData, context);
            map.put(key, value);
        }

        return map;
    }
}
