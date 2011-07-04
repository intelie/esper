package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.epl.expression.ExprDotNodeUtility;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.map.MapEventType;

import java.util.List;

public class ExprDotEvalFirstLastOf extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        EventType firstParamType;
        if (inputEventType == null) {
            firstParamType = ExprDotNodeUtility.makeTransientMapType(enumMethodUsedName, goesToNames.get(0), collectionComponentType);
        }
        else {
            firstParamType = inputEventType;
        }
        return new EventType[] {firstParamType};
    }

    public EnumEval getEnumEval(EventAdapterService eventAdapterService, StreamTypeService streamTypeService, String statementId, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {

        if (bodiesAndParameters.isEmpty()) {
            if (inputEventType != null) {
                super.setTypeInfo(ExprDotEvalTypeInfo.event(inputEventType));
            }
            else {
                super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(collectionComponentType));                
            }
            if (this.getEnumMethodEnum() == EnumMethodEnum.FIRST) {
                return new EnumEvalFirstOfNoPredicate(numStreamsIncoming);
            }
            else {
                return new EnumEvalLastOfNoPredicate(numStreamsIncoming);
            }
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        if (inputEventType != null) {
            super.setTypeInfo(ExprDotEvalTypeInfo.event(inputEventType));
            if (this.getEnumMethodEnum() == EnumMethodEnum.FIRST) {
                return new EnumEvalFirstOfPredicateEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
            }
            else {
                return new EnumEvalLastOfPredicateEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
            }
        }
        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(collectionComponentType));
        if (this.getEnumMethodEnum() == EnumMethodEnum.FIRST) {
            return new EnumEvalFirstOfPredicateScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
        }
        else {
            return new EnumEvalLastOfPredicateScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
        }

    }
}
