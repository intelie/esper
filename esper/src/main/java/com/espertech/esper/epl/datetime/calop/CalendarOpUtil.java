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
import com.espertech.esper.epl.expression.*;

public class CalendarOpUtil {

    protected static Integer getInt(ExprEvaluator expr, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        Object result = expr.evaluate(eventsPerStream, isNewData, context);
        if (result == null) {
            return null;
        }
        return (Integer) result;
    }

    public static CalendarFieldEnum getEnum(String methodName, ExprNode exprNode) throws ExprValidationException {
        String message = "Date-time enumeration method '" + methodName + "'";
        String validFieldNames = "valid field names are '" + CalendarFieldEnum.getValidList() + "'";
        if (!(exprNode instanceof ExprConstantNode)) {
            throw new ExprValidationException(message + " requires a constant string-type parameter as its first parameter, " + validFieldNames);
        }
        String fieldname = (String) exprNode.getExprEvaluator().evaluate(null, true, null);
        CalendarFieldEnum fieldNum = CalendarFieldEnum.fromString(fieldname);
        if (fieldNum == null) {
            throw new ExprValidationException(message + " datetime-field name '" + fieldname + "' is not recognized, " + validFieldNames);
        }
        return fieldNum;
    }
}
