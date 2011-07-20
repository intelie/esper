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

public class CalendarOpSet implements CalendarOp {

    private final CalendarFieldEnum fieldName;
    private final ExprEvaluator valueExpr;

    public CalendarOpSet(CalendarFieldEnum fieldName, ExprEvaluator valueExpr) {
        this.fieldName = fieldName;
        this.valueExpr = valueExpr;
    }

    public void evaluate(Calendar cal, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Integer value = CalendarOpUtil.getInt(valueExpr, eventsPerStream, isNewData, context);
        if (value == null) {
            return;
        }
        cal.set(fieldName.getCalendarField(), value);
    }
}
