package com.espertech.esper.epl.datetime;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.reformatop.ReformatOp;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;

public class ExprDotEvalDTReformatOnly implements ExprDotEval
{
    private static final Log log = LogFactory.getLog(ExprDotEvalDTReformatOnly.class);

    private final String methodName;
    private final ReformatOp reformatOp;

    public ExprDotEvalDTReformatOnly(String methodName, ReformatOp reformatOp) {
        this.methodName = methodName;
        this.reformatOp = reformatOp;
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        if (target instanceof Calendar) {
            return reformatOp.evaluate((Calendar) target);
        }
        else if (target instanceof Date) {
            return reformatOp.evaluate((Date) target);
        }
        else if (target instanceof Long) {
            return reformatOp.evaluate((Long) target);
        }
        else {
            log.warn("Date-time method '" + methodName + "' received non-datetime input class " + target.getClass().getName());
            return null;
        }
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return ExprDotEvalTypeInfo.scalarOrUnderlying(reformatOp.getReturnType());
    }
}
