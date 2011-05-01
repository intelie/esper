package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.EnumMethodEnum;

import java.util.List;

public class ExprDotEvalTakeAndTakeLast extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {
        ExprEvaluator sizeEval = bodiesAndParameters.get(0).getBodyEvaluator();
        
        if (inputEventType != null) {
            super.setTypeInfo(ExprDotEvalTypeInfo.eventColl(inputEventType));
        }
        else {
            super.setTypeInfo(ExprDotEvalTypeInfo.componentColl(collectionComponentType));
        }

        if (getEnumMethodEnum() == EnumMethodEnum.TAKE) {
            return new EnumEvalTake(sizeEval, numStreamsIncoming);
        }
        else {
            return new EnumEvalTakeLast(sizeEval, numStreamsIncoming);
        }
    }
}
