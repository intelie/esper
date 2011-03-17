package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.EnumMethodEnum;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;

import java.util.List;

public class ExprDotEvalMinByMaxBy extends ExprDotEvalEnumMethodBase {

    private EventType eventType;
    private Class underlyingType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        eventType = inputEventType;
        underlyingType = inputEventType.getUnderlyingType();

        if (this.getEnumMethodEnum() == EnumMethodEnum.MAXBY) {
            return new EnumEvalMaxBy(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        return new EnumEvalMinBy(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }

    public Class getResultType() {
        return underlyingType;
    }

    public EventType getResultEventType() {
        return eventType;
    }
}
