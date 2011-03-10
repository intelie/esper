/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.declexpr;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.ExpressionResultCacheEntry;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprEvaluatorLambda;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.ExpressionDeclItem;

import java.util.Collection;
import java.util.Map;

public abstract class ExprDeclaredEvalBase implements ExprEvaluator, ExprEvaluatorLambda {
    private final ExprEvaluator innerEvaluator;
    private final ExprEvaluatorLambda innerEvaluatorLambda;
    private final ExpressionDeclItem prototype;
    private final boolean isCache;

    public abstract EventBean[] getEventsPerStreamRewritten(EventBean[] eventsPerStream);

    public ExprDeclaredEvalBase(ExprEvaluator innerEvaluator, ExpressionDeclItem prototype, boolean isCache) {
        this.innerEvaluator = innerEvaluator;
        this.prototype = prototype;
        if (innerEvaluator instanceof ExprEvaluatorLambda) {
            innerEvaluatorLambda = (ExprEvaluatorLambda) innerEvaluator;
        }
        else {
            innerEvaluatorLambda = null;
        }
        this.isCache = isCache;
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return null;
    }

    public Class getType() {
        return innerEvaluator.getType();
    }

    public final Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {

        // rewrite streams
        EventBean[] events = getEventsPerStreamRewritten(eventsPerStream);

        Object result;
        if (isCache) {      // no the same cache as for iterator
            ExpressionResultCacheEntry<EventBean[], Object> entry = context.getExpressionResultCacheService().getDeclaredExpressionLastValue(prototype, events);
            if (entry != null) {
                return entry.getResult();
            }
            result = innerEvaluator.evaluate(events, isNewData, context);
            context.getExpressionResultCacheService().saveDeclaredExpressionLastValue(prototype, events, result);
        }
        else {
            result = innerEvaluator.evaluate(events, isNewData, context);
        }

        return result;
    }

    public final Collection<EventBean> evaluateGetROCollection(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {

        // rewrite streams
        EventBean[] events = getEventsPerStreamRewritten(eventsPerStream);

        Collection<EventBean> result;
        if (isCache) {
            ExpressionResultCacheEntry<EventBean[], Collection<EventBean>> entry = context.getExpressionResultCacheService().getDeclaredExpressionLastColl(prototype, events);
            if (entry != null) {
                return entry.getResult();
            }

            result = innerEvaluatorLambda.evaluateGetROCollection(events, isNewData, context);
            context.getExpressionResultCacheService().saveDeclaredExpressionLastColl(prototype, events, result);
            return result;
        }
        else {
            result = innerEvaluatorLambda.evaluateGetROCollection(events, isNewData, context);
        }

        return result;
    }

    public EventType getEventTypeIterator() throws ExprValidationException {
        if (innerEvaluator instanceof ExprEvaluatorLambda) {
            ExprEvaluatorLambda lambda = (ExprEvaluatorLambda) innerEvaluator;
            return lambda.getEventTypeIterator();
        }
        return null;
    }
}