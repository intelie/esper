package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.EventUnderlyingCollection;
import com.espertech.esper.collection.EventUnderlyingIterator;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.Iterator;

public class ExprDotEvalUnpackCollEventBean implements ExprDotEval {

    public ExprDotEvalUnpackCollEventBean() {
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        Collection<EventBean> it = (Collection<EventBean>) target;
        return new EventUnderlyingCollection(it);
    }

    public Class getResultType() {
        return Collection.class;
    }
}
