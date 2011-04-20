package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;

import java.util.List;

public class ExprDotEvalMinByMaxBy extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {
        super.setTypeInfo(ExprDotEvalTypeInfo.event(inputEventType));
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);

        if (this.getEnumMethodEnum() == EnumMethodEnum.MAXBY) {
            return new EnumEvalMaxBy(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        return new EnumEvalMinBy(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }
}
