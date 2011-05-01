package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public abstract class EnumEvalBaseScalar extends EnumEvalBase implements EnumEval {

    protected final String evalPropertyName;
    protected final MapEventBean evalEvent;

    public EnumEvalBaseScalar(ExprEvaluator innerExpression, int streamCountIncoming, MapEventType type, String propertyName) {
        super(innerExpression, streamCountIncoming);
        this.evalPropertyName = propertyName;
        this.evalEvent = new MapEventBean(new HashMap<String, Object>(), type);
    }
}
