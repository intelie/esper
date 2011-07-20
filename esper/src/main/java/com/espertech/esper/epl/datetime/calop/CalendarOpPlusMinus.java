/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.datetime.calop;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.datetime.eval.TimePeriod;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Calendar;

public class CalendarOpPlusMinus implements CalendarOp {

    private final ExprEvaluator param;
    private final int factor;

    public CalendarOpPlusMinus(ExprEvaluator param, int factor) {
        this.param = param;
        this.factor = factor;
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object value = param.evaluate(eventsPerStream, isNewData, context);
        if (value instanceof Number) {
            action(cal, factor, ((Number) value).longValue());
        }
        else {
            action(cal, factor, (TimePeriod) value);
        }        
    }

    protected static void action(Calendar cal, int factor, Long duration) {
        if (duration == null) {
            return;
        }
        long dur = duration;
        if (duration < Integer.MAX_VALUE) {
            cal.add(Calendar.MILLISECOND, (int) (factor * dur));
            return;
        }

        int days = (int) (duration / (1000L*60*60*24));
        int msec = (int) (duration - days * (1000L*60*60*24));
        cal.add(Calendar.MILLISECOND, factor * msec);
        cal.add(Calendar.DATE, factor * days);
    }

    protected static void action(Calendar cal, int factor, TimePeriod tp) {
        if (tp == null) {
            return;
        }
        if (tp.getYears() != null) {
            cal.add(Calendar.YEAR, factor * tp.getYears());
        }
        if (tp.getMonths() != null) {
            cal.add(Calendar.MONTH, factor * tp.getMonths());
        }
        if (tp.getWeeks() != null) {
            cal.add(Calendar.WEEK_OF_YEAR, factor * tp.getWeeks());
        }
        if (tp.getDays() != null) {
            cal.add(Calendar.DATE, factor * tp.getDays());
        }
        if (tp.getHours() != null) {
            cal.add(Calendar.HOUR_OF_DAY, factor * tp.getHours());
        }
        if (tp.getMinutes() != null) {
            cal.add(Calendar.MINUTE, factor * tp.getMinutes());
        }
        if (tp.getSeconds() != null) {
            cal.add(Calendar.SECOND, factor * tp.getSeconds());
        }
        if (tp.getMilliseconds() != null) {
            cal.add(Calendar.MILLISECOND, factor * tp.getMilliseconds());
        }
    }
}
