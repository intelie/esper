package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.util.JavaClassHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExprDotEvalAggregate extends ExprDotEvalEnumMethodBase {

    private Class resultType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        Class initializationType = bodiesAndParameters.get(0).getBodyEvaluator().getType();

        String parameterResult = goesToNames.get(0);   // provides result expression
        Map<String, Object> propsResult = new HashMap<String, Object>();
        propsResult.put(parameterResult, initializationType);
        MapEventType typeResult = new MapEventType(EventTypeMetadata.createAnonymous("lambda"), "lambdaaggregate", null, propsResult, null, null);

        return new EventType[] {typeResult, inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParam initValueParam = bodiesAndParameters.get(0);
        ExprEvaluator initValueEval = initValueParam.getBodyEvaluator();
        resultType = JavaClassHelper.getBoxedType(initValueEval.getType());

        ExprDotEvalParamLambda resultAndAdd = (ExprDotEvalParamLambda) bodiesAndParameters.get(1);
        return new EnumEvalAggregate(initValueEval,
                resultAndAdd.getBodyEvaluator(), resultAndAdd.getStreamCountIncoming(),
                (MapEventType) resultAndAdd.getGoesToTypes()[0], resultAndAdd.getGoesToNames().get(0));
    }

    public Class getResultType() {
        return resultType;
    }

    public EventType getResultEventType() {
        return null;
    }
}
