package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class ExprDotEvalProperty implements ExprDotEval {

    private final EventPropertyGetter getter;
    private final ExprDotEvalTypeInfo returnType;

    public ExprDotEvalProperty(EventPropertyGetter getter, ExprDotEvalTypeInfo returnType) {
        this.getter = getter;
        this.returnType = returnType;
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (!(target instanceof EventBean)) {
            return null;
        }
        return getter.get((EventBean) target);
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return returnType;
    }
}
