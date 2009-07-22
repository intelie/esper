package com.espertech.esper.schedule;

public interface SchedulingServiceSPI extends SchedulingService
{
    public ScheduleSet take(String statementId);
    public void apply(ScheduleSet scheduleSet);
}
