package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;

import java.util.Collection;
import java.util.List;

public class ExprDotEvalSelectFrom extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        return new EnumEvalSelectFrom(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }

    public Class getResultType() {
        return Collection.class;
    }
}
