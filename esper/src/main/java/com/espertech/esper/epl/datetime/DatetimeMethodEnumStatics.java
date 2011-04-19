package com.espertech.esper.epl.datetime;

import com.espertech.esper.epl.datetime.calop.CalendarOpFactory;
import com.espertech.esper.epl.datetime.reformatop.ReformatOpFactory;

public class DatetimeMethodEnumStatics {

    public static OpFactory calendarOpFactory = new CalendarOpFactory();
    public static OpFactory reformatOpFactory = new ReformatOpFactory();
}
