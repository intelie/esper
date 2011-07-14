package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.epl.datetime.calop.CalendarOpFactory;
import com.espertech.esper.epl.datetime.interval.IntervalOpFactory;
import com.espertech.esper.epl.datetime.reformatop.ReformatOpFactory;

public class DatetimeMethodEnumStatics {

    public static OpFactory calendarOpFactory = new CalendarOpFactory();
    public static OpFactory reformatOpFactory = new ReformatOpFactory();
    public static OpFactory intervalOpFactory = new IntervalOpFactory();
}
