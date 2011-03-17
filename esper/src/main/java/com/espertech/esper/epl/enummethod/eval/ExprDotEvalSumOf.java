package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.util.JavaClassHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ExprDotEvalSumOf extends ExprDotEvalEnumMethodBase {

    private Class returnType;

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        Class evalType = first.getBodyEvaluator().getType();

        AggregationMethod aggMethod;
        if (JavaClassHelper.isFloatingPointClass(evalType)) {
            aggMethod = new DoubleSumAggregator();
        }
        else if (evalType == BigDecimal.class) {
            aggMethod = new BigDecimalSumAggregator();
        }
        else if (evalType == BigInteger.class) {
            aggMethod = new BigIntegerSumAggregator();
        }
        else if (JavaClassHelper.getBoxedType(evalType) == Long.class) {
            aggMethod = new LongSumAggregator();
        }
        else {
            aggMethod = new IntegerSumAggregator();
        }
        returnType = JavaClassHelper.getBoxedType(aggMethod.getValueType());
        return new EnumEvalSum(first.getBodyEvaluator(), first.getStreamCountIncoming(), aggMethod);
    }

    public Class getResultType() {
        return returnType;
    }

    public EventType getResultEventType() {
        return null;
    }
}
