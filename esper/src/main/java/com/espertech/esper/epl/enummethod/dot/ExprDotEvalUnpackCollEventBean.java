package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.EventUnderlyingCollection;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class ExprDotEvalUnpackCollEventBean implements ExprDotEval {

    private ExprDotEvalTypeInfo typeInfo;

    public ExprDotEvalUnpackCollEventBean(EventType type) {
        typeInfo = ExprDotEvalTypeInfo.componentColl(type.getUnderlyingType());
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        Collection<EventBean> it = (Collection<EventBean>) target;
        return new EventUnderlyingCollection(it);
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return typeInfo;
    }
}
