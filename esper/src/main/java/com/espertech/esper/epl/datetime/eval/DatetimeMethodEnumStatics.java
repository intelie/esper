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

package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.epl.datetime.calop.CalendarOpFactory;
import com.espertech.esper.epl.datetime.interval.IntervalOpFactory;
import com.espertech.esper.epl.datetime.reformatop.ReformatOpFactory;

public class DatetimeMethodEnumStatics {

    public static OpFactory calendarOpFactory = new CalendarOpFactory();
    public static OpFactory reformatOpFactory = new ReformatOpFactory();
    public static OpFactory intervalOpFactory = new IntervalOpFactory();
}
