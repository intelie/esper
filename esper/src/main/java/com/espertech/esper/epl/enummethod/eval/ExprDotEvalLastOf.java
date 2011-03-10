package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.util.JavaClassHelper;

import java.util.List;

public class ExprDotEvalLastOf extends ExprDotEvalEnumMethodBase {

    private Class returnType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        returnType = JavaClassHelper.getBoxedType(first.getBodyEvaluator().getType());
        return new EnumEvalLastOf(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }

    public Class getResultType() {
        return returnType;
    }
}
