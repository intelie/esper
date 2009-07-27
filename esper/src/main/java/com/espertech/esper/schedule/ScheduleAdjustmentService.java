package com.espertech.esper.schedule;

import java.util.Set;
import java.util.HashSet;

public class ScheduleAdjustmentService
{
    private Set<ScheduleAdjustmentCallback> callbacks = new HashSet<ScheduleAdjustmentCallback>();

    public void addCallback(ScheduleAdjustmentCallback callback)
    {
        callbacks.add(callback);
    }

    public void adjust(long delta)
    {
        for (ScheduleAdjustmentCallback callback : callbacks)
        {
            callback.adjust(delta);
        }
    }
}
