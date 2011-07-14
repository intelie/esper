package com.espertech.esper.epl.datetime.calop;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Calendar;

public class CalendarOpRound implements CalendarOp {

    private final CalendarFieldEnum fieldName;
    private final int code;

    public CalendarOpRound(CalendarFieldEnum fieldName, DatetimeMethodEnum method) {
        this.fieldName = fieldName;
        if (method == DatetimeMethodEnum.ROUNDCEILING) {
            code = ApacheCommonsDateUtils.MODIFY_CEILING;
        }
        else if (method == DatetimeMethodEnum.ROUNDFLOOR) {
            code = ApacheCommonsDateUtils.MODIFY_TRUNCATE;
        }
        else if (method == DatetimeMethodEnum.ROUNDHALF) {
            code = ApacheCommonsDateUtils.MODIFY_ROUND;
        }
        else {
            throw new IllegalArgumentException("Unrecognized method '" + method + "'");
        }
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        ApacheCommonsDateUtils.modify(cal, fieldName.getCalendarField(), code);
    }
}
