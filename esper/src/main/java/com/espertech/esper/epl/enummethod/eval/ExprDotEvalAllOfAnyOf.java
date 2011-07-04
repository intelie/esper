package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.epl.expression.ExprDotNodeUtility;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.map.MapEventType;

import java.util.List;

public class ExprDotEvalAllOfAnyOf extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        if (inputEventType != null) {
            return new EventType[] {inputEventType};
        }
        else {
            return new EventType[] {ExprDotNodeUtility.makeTransientMapType(enumMethodUsedName, goesToNames.get(0), collectionComponentType)};
        }
    }

    public EnumEval getEnumEval(EventAdapterService eventAdapterService, StreamTypeService streamTypeService, String statementId, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);

        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(Boolean.class));
        if (inputEventType != null) {
            if (super.getEnumMethodEnum() == EnumMethodEnum.ALLOF) {
                return new EnumEvalAllOfEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
            }
            return new EnumEvalAnyOfEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }

        if (super.getEnumMethodEnum() == EnumMethodEnum.ALLOF) {
            return new EnumEvalAllOfScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
        }
        return new EnumEvalAnyOfScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
    }
}
