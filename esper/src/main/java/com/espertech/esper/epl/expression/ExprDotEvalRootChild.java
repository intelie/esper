/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;

import java.util.Collection;
import java.util.Map;

public class ExprDotEvalRootChild implements ExprEvaluator, ExprEvaluatorLambda
{
    private final ExprEvaluator rootNodeEvaluator;
    private final ExprEvaluatorLambda rootLambdaEvaluator;
    private final ExprDotEval[] evalIteratorEventBean;
    private final ExprDotEval[] evalUnpacking;

    public ExprDotEvalRootChild(ExprEvaluator rootNodeEvaluator, ExprEvaluatorLambda rootLambdaEvaluator, ExprDotEval[] evalIteratorEventBean, ExprDotEval[] evalUnpacking) {
        this.rootNodeEvaluator = rootNodeEvaluator;
        this.rootLambdaEvaluator = rootLambdaEvaluator;
        this.evalUnpacking = evalUnpacking;
        this.evalIteratorEventBean = evalIteratorEventBean;
    }

    public Class getType()
    {
        return evalUnpacking[evalUnpacking.length - 1].getResultType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object inner;

        if (rootLambdaEvaluator != null) {
            inner = rootLambdaEvaluator.evaluateGetROCollection(eventsPerStream, isNewData, exprEvaluatorContext);
        }
        else {
            inner = rootNodeEvaluator.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
        }
        if (inner == null) {
            return null;
        }

        for (ExprDotEval methodEval : evalUnpacking) {
            inner = methodEval.evaluate(inner, eventsPerStream, isNewData, exprEvaluatorContext);
            if (inner == null) {
                break;
            }
        }
        return inner;
    }

    public Collection<EventBean> evaluateGetROCollection(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object inner;

        if (rootLambdaEvaluator != null) {
            inner = rootLambdaEvaluator.evaluateGetROCollection(eventsPerStream, isNewData, context);
        }
        else {
            return null;
        }
        if (inner == null) {
            return null;
        }

        for (ExprDotEval methodEval : evalIteratorEventBean) {
            inner = methodEval.evaluate(inner, eventsPerStream, isNewData, context);
            if (inner == null) {
                break;
            }
        }

        if (inner instanceof Collection) {
            return (Collection<EventBean>) inner;
        }
        return null;
    }

    public EventType getEventTypeIterator() throws ExprValidationException {
        if (rootLambdaEvaluator != null) {
            return rootLambdaEvaluator.getEventTypeIterator();
        }
        return null;
    }

    public Map<String, Object> getEventType() throws ExprValidationException {
        return null;
    }
}
