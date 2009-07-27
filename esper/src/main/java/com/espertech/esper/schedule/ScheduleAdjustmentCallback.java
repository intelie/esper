package com.espertech.esper.schedule;

public interface ScheduleAdjustmentCallback
{
    public void adjust(long delta);
}