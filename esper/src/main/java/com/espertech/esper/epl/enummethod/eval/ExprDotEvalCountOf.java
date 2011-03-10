package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;

import java.util.List;

public class ExprDotEvalCountOf extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        if (goesToNames.isEmpty()) {
            return new EventType[0];
        }
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        if (bodiesAndParameters.isEmpty()) {
            return new EnumEvalCountOf(numStreamsIncoming);
        }
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        return new EnumEvalCountOfSelector(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }

    public Class getResultType() {
        return Integer.class;
    }
}
