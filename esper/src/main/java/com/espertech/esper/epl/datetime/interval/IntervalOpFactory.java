package com.espertech.esper.epl.datetime.interval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.eval.OpFactory;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.List;

public class IntervalOpFactory implements OpFactory {
    public IntervalOp getOp(EventType[] typesPerStream, DatetimeMethodEnum method, String methodNameUsed, List<ExprNode> parameters, ExprEvaluator[] evaluators)
        throws ExprValidationException {

        return new IntervalOpImpl(method, methodNameUsed, typesPerStream, parameters);
    }

}
