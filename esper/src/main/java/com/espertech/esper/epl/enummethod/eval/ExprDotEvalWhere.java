package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.map.MapEventType;

import java.util.*;

public class ExprDotEvalWhere extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        if (goesToNames.size() == 1) {
            return new EventType[] {inputEventType};
        }
        String parameterNames = goesToNames.get(1);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(parameterNames, int.class);
        MapEventType type = new MapEventType(EventTypeMetadata.createAnonymous("lambda"), "lambdawhere", null, props, null, null);
        return new EventType[]{inputEventType, type};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        if (first.getGoesToNames().size() == 1) {
            return new EnumEvalWhere(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        else {
            return new EnumEvalWhereIndex(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[1], first.getGoesToNames().get(1));
        }
    }

    public Class getResultType() {
        return Collection.class;
    }
}
