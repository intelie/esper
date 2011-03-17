package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.Iterator;

public class ExprDotEvalUnpackCollValue implements ExprDotEval {

    public ExprDotEvalUnpackCollValue() {
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        return target;
    }

    public Class getResultType() {
        return Collection.class;
    }

    public EventType getResultEventType() {
        return null;
    }    
}
