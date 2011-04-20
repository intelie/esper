package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotNodeUtility;
import com.espertech.esper.event.map.MapEventType;

import java.util.*;

public class ExprDotEvalWhere extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        EventType firstParamType;
        if (inputEventType == null) {
            firstParamType = ExprDotNodeUtility.makeTransientMapType(enumMethodUsedName, goesToNames.get(0), collectionComponentType);
        }
        else {
            firstParamType = inputEventType;
        }

        if (goesToNames.size() == 1) {
            return new EventType[] {firstParamType};
        }

        MapEventType indexEventType = ExprDotNodeUtility.makeTransientMapType(enumMethodUsedName, goesToNames.get(1), int.class);
        return new EventType[]{firstParamType, indexEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);

        if (inputEventType != null) {
            super.setTypeInfo(ExprDotEvalTypeInfo.eventColl(inputEventType));
            if (first.getGoesToNames().size() == 1) {
                return new EnumEvalWhereEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
            }
            return new EnumEvalWhereIndexEvents(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[1], first.getGoesToNames().get(1));
        }

        super.setTypeInfo(ExprDotEvalTypeInfo.componentColl(collectionComponentType));
        if (first.getGoesToNames().size() == 1) {
            return new EnumEvalWhereScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
        }
        return new EnumEvalWhereScalarIndex(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0), (MapEventType) first.getGoesToTypes()[1], first.getGoesToNames().get(1));
    }
}
