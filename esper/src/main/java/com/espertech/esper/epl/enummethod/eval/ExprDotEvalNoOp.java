package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;

import java.util.Collection;
import java.util.List;

public class ExprDotEvalNoOp extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {};
    }

    public EnumEval getEnumEval(List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) {
        return new EnumEvalNoOp(numStreamsIncoming);
    }

    public Class getResultType() {
        return Collection.class;
    }
}
