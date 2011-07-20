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
import java.util.LinkedHashMap;
import java.util.Map;

public class EnumEvalGroupByKeyValueSelector extends EnumEvalBase implements EnumEval {

    private ExprEvaluator secondExpression;

    public EnumEvalGroupByKeyValueSelector(ExprEvaluator innerExpression, int streamCountIncoming, ExprEvaluator secondExpression) {
        super(innerExpression, streamCountIncoming);
        this.secondExpression = secondExpression;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Map<Object, Collection> result = new LinkedHashMap<Object, Collection>();

        Collection<EventBean> beans = (Collection<EventBean>) target;
        for (EventBean next : beans) {
            eventsLambda[streamNumLambda] = next;

            Object key = innerExpression.evaluate(eventsLambda, isNewData, context);
            Object entry = secondExpression.evaluate(eventsLambda, isNewData, context);

            Collection value = result.get(key);
            if (value == null) {
                value = new ArrayList();
                result.put(key, value);
            }
            value.add(entry);
        }

        return result;
    }
}
