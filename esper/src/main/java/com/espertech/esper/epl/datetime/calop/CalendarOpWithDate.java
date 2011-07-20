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
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Calendar;

public class CalendarOpWithDate implements CalendarOp {

    private ExprEvaluator year;
    private ExprEvaluator month;
    private ExprEvaluator day;

    public CalendarOpWithDate(ExprEvaluator year, ExprEvaluator month, ExprEvaluator day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Integer yearNum = getInt(year, eventsPerStream, isNewData, context);
        Integer monthNum = getInt(month, eventsPerStream, isNewData, context);
        Integer dayNum = getInt(day, eventsPerStream, isNewData, context);
        action(cal, yearNum, monthNum, dayNum);
    }

    protected static Integer getInt(ExprEvaluator expr, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object result = expr.evaluate(eventsPerStream, isNewData, context);
        if (result == null) {
            return null;
        }
        return (Integer) result;
    }

    private static void action(Calendar cal, Integer year, Integer month, Integer day) {
        if (year != null) {
            cal.set(Calendar.YEAR, year);
        }
        if (month != null) {
            cal.set(Calendar.MONTH, month);
        }
        if (day != null) {
            cal.set(Calendar.DATE, day);
        }
    }
}
