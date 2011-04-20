package com.espertech.esper.epl.datetime.calop;

import com.espertech.esper.epl.datetime.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.OpFactory;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.List;

public class CalendarOpFactory implements OpFactory {

    public CalendarOp getOp(DatetimeMethodEnum method, String methodNameUsed, List<ExprNode> parameters, ExprEvaluator[] evaluators)
        throws ExprValidationException {
        if (method == DatetimeMethodEnum.WITHTIME) {
            return new CalendarOpWithTime(evaluators[0], evaluators[1], evaluators[2], evaluators[3]);
        }
        if (method == DatetimeMethodEnum.WITHDATE) {
            return new CalendarOpWithDate(evaluators[0], evaluators[1], evaluators[2]);
        }
        if (method == DatetimeMethodEnum.PLUS || method == DatetimeMethodEnum.MINUS) {
            return new CalendarOpPlusMinus(evaluators[0], method == DatetimeMethodEnum.MINUS ? -1 : 1);
        }
        if (method == DatetimeMethodEnum.WITHMAX ||
            method == DatetimeMethodEnum.WITHMIN ||
            method == DatetimeMethodEnum.ROUNDCEILING ||
            method == DatetimeMethodEnum.ROUNDFLOOR ||
            method == DatetimeMethodEnum.ROUNDHALF ||
            method == DatetimeMethodEnum.SET) {
            CalendarFieldEnum fieldNum = CalendarOpUtil.getEnum(methodNameUsed, parameters.get(0));
            if (method == DatetimeMethodEnum.WITHMIN) {
                return new CalendarOpWithMin(fieldNum);
            }
            if (method == DatetimeMethodEnum.ROUNDCEILING || method == DatetimeMethodEnum.ROUNDFLOOR || method == DatetimeMethodEnum.ROUNDHALF) {
                return new CalendarOpRound(fieldNum, method);
            }
            else if (method == DatetimeMethodEnum.SET) {
                return new CalendarOpSet(fieldNum, evaluators[1]);
            }
            return new CalendarOpWithMax(fieldNum);
        }
        throw new IllegalStateException("Unrecognized calendar-op code '" + method + "'");
    }
}
