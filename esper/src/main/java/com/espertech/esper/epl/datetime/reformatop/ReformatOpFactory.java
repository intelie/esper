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

package com.espertech.esper.epl.datetime.reformatop;

import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.datetime.eval.OpFactory;
import com.espertech.esper.epl.datetime.calop.CalendarFieldEnum;
import com.espertech.esper.epl.datetime.calop.CalendarOpUtil;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;

public class ReformatOpFactory implements OpFactory {

    private static ReformatOp FormatString = new ReformatOpStringFormat();
    private static ReformatOp ToCal = new ReformatOpToCalendar();
    private static ReformatOp ToMsec = new ReformatOpToMillisec();
    private static ReformatOp ToDate = new ReformatOpToDate();

    public ReformatOp getOp(DatetimeMethodEnum method, String methodNameUsed, ExprNode first) throws ExprValidationException {
        if (method == DatetimeMethodEnum.GET) {
            CalendarFieldEnum fieldNum = CalendarOpUtil.getEnum(methodNameUsed, first);
            return new ReformatOpGetField(fieldNum);
        }
        if (method == DatetimeMethodEnum.FORMAT) {
            return FormatString;
        }
        if (method == DatetimeMethodEnum.TOCALENDAR) {
            return ToCal;
        }
        if (method == DatetimeMethodEnum.TOMILLISEC) {
            return ToMsec;
        }
        if (method == DatetimeMethodEnum.TODATE) {
            return ToDate;
        }
        if (method == DatetimeMethodEnum.GETDAYOFMONTH) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.DayOfMonth);
        }
        if (method == DatetimeMethodEnum.GETMINUTEOFHOUR) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.MinuteOfHour);
        }
        if (method == DatetimeMethodEnum.GETMONTHOFYEAR) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.MonthOfYear);
        }
        if (method == DatetimeMethodEnum.GETDAYOFWEEK) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.DayOfWeek);
        }
        if (method == DatetimeMethodEnum.GETDAYOFYEAR) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.DayOfYear);
        }
        if (method == DatetimeMethodEnum.GETERA) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.Era);
        }
        if (method == DatetimeMethodEnum.GETHOUROFDAY) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.HourOfDay);
        }
        if (method == DatetimeMethodEnum.GETMILLISOFSECOND) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.MillisOfSecond);
        }
        if (method == DatetimeMethodEnum.GETSECONDOFMINUTE) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.SecondOfMinute);
        }
        if (method == DatetimeMethodEnum.GETWEEKYEAR) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.Weekyear);
        }
        if (method == DatetimeMethodEnum.GETYEAR) {
            return new ReformatOpCalendarEval(CalendarEvalStatics.Year);
        }
        throw new IllegalStateException("Unrecognized date-time method code '" + method + "'");
    }
}
