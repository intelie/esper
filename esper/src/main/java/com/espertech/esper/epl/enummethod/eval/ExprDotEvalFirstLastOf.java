package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.EnumMethodEnum;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;

import java.util.List;

public class ExprDotEvalFirstLastOf extends ExprDotEvalEnumMethodBase {

    private Class returnType;
    private EventType returnEventType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        returnType = inputEventType.getUnderlyingType();
        returnEventType = inputEventType;

        if (bodiesAndParameters.isEmpty()) {
            if (this.getEnumMethodEnum() == EnumMethodEnum.FIRST) {
                return new EnumEvalFirstOfNoPredicate(numStreamsIncoming);
            }
            else {
                return new EnumEvalLastOfNoPredicate(numStreamsIncoming);
            }
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        if (this.getEnumMethodEnum() == EnumMethodEnum.FIRST) {
            return new EnumEvalFirstOfPredicate(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        else {
            return new EnumEvalLastOfPredicate(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
    }

    public Class getResultType() {
        return returnType;
    }

    public EventType getResultEventType() {
        return returnEventType;
    }
}
