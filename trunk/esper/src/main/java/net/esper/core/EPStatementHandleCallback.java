package net.esper.core;

import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterHandle;
import net.esper.schedule.ScheduleHandle;
import net.esper.schedule.ScheduleHandleCallback;

public class EPStatementHandleCallback implements FilterHandle, ScheduleHandle
{
    private EPStatementHandle epStatementHandle;
    private FilterHandleCallback filterCallback;
    private ScheduleHandleCallback scheduleCallback;

    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, FilterHandleCallback callback)
    {
        this.epStatementHandle = epStatementHandle;
        this.filterCallback = callback;
    }

    public EPStatementHandleCallback(EPStatementHandle epStatementHandle, ScheduleHandleCallback callback)
    {
        this.epStatementHandle = epStatementHandle;
        this.scheduleCallback = callback;
    }

    public EPStatementHandle getEpStatementHandle()
    {
        return epStatementHandle;
    }

    public FilterHandleCallback getFilterCallback()
    {
        return filterCallback;
    }

    public ScheduleHandleCallback getScheduleCallback()
    {
        return scheduleCallback;
    }
}
