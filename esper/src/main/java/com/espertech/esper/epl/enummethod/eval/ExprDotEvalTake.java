package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.EnumMethodEnum;

import java.util.Collection;
import java.util.List;

public class ExprDotEvalTake extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprEvaluator sizeEval = bodiesAndParameters.get(0).getBodyEvaluator();
        if (getEnumMethodEnum() == EnumMethodEnum.TAKE) {
            return new EnumEvalTake(sizeEval, numStreamsIncoming);
        }
        else {
            return new EnumEvalTakeLast(sizeEval, numStreamsIncoming);
        }
    }

    public Class getResultType() {
        return Collection.class;
    }
}
