package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ExprDotEvalAverage extends ExprDotEvalEnumMethodBase {

    private Class returnType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        returnType = first.getBodyEvaluator().getType();
        if (returnType == BigDecimal.class || returnType == BigInteger.class) {
            returnType = BigDecimal.class;
            return new EnumEvalAverageBigDecimal(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        returnType = Double.class;
        return new EnumEvalAverage(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }

    public Class getResultType() {
        return returnType;
    }
}
