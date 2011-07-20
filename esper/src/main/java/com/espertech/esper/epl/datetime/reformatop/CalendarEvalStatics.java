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

import com.espertech.esper.epl.datetime.reformatop.CalendarEval;

import java.util.Calendar;

public class CalendarEvalStatics {

    public static CalendarEval MinuteOfHour = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.MINUTE);
            }
        };

    public static CalendarEval MonthOfYear = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.MONTH);
            }
        };

    public static CalendarEval DayOfMonth = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.DATE);
            }
        };

    public static CalendarEval DayOfWeek = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.DAY_OF_WEEK);
            }
        };

    public static CalendarEval DayOfYear = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.DAY_OF_YEAR);
            }
        };    

    public static CalendarEval Era = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.ERA);
            }
        };

    public static CalendarEval HourOfDay = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.HOUR_OF_DAY);
            }
        };

    public static CalendarEval MillisOfSecond = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.MILLISECOND);
            }
        };

    public static CalendarEval SecondOfMinute = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.SECOND);
            }
        };

    public static CalendarEval Weekyear = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.WEEK_OF_YEAR);
            }
        };

    public static CalendarEval Year = new CalendarEval() {
            public Object evaluateInternal(Calendar cal) {
                return cal.get(Calendar.YEAR);
            }
        };
}
