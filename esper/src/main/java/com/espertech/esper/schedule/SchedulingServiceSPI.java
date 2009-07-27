package com.espertech.esper.schedule;

import java.util.Set;

public interface SchedulingServiceSPI extends SchedulingService
{
    public ScheduleSet take(Set<String> statementId);
    public void apply(ScheduleSet scheduleSet);
}
