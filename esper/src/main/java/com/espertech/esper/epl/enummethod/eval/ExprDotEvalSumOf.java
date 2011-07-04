package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.util.JavaClassHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ExprDotEvalSumOf extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(EventAdapterService eventAdapterService, StreamTypeService streamTypeService, String statementId, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {

        if (bodiesAndParameters.isEmpty()) {
            AggregationMethod aggMethod = getAggregator(collectionComponentType);
            super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(JavaClassHelper.getBoxedType(aggMethod.getValueType())));
            return new EnumEvalSumScalar(numStreamsIncoming, aggMethod);
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        AggregationMethod aggMethod = getAggregator(first.getBodyEvaluator().getType());
        Class returnType = JavaClassHelper.getBoxedType(aggMethod.getValueType());
        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(returnType));
        return new EnumEvalSumEvents(first.getBodyEvaluator(), first.getStreamCountIncoming(), aggMethod);
    }

    private static AggregationMethod getAggregator(Class evalType) {
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
        return aggMethod;
    }
}
