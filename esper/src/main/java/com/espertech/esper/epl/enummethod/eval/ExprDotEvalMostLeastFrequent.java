package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.util.JavaClassHelper;

import java.util.List;

public class ExprDotEvalMostLeastFrequent extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {

        if (bodiesAndParameters.isEmpty()) {
            Class returnType = JavaClassHelper.getBoxedType(collectionComponentType);
            super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(returnType));
            return new EnumEvalMostLeastFrequentScalar(numStreamsIncoming, this.getEnumMethodEnum() == EnumMethodEnum.MOSTFREQUENT);
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        Class returnType = JavaClassHelper.getBoxedType(first.getBodyEvaluator().getType());
        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(returnType));

        return new EnumEvalMostLeastFrequentEvent(first.getBodyEvaluator(), numStreamsIncoming, this.getEnumMethodEnum() == EnumMethodEnum.MOSTFREQUENT);
    }
}
