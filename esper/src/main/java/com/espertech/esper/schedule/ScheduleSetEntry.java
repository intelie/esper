package com.espertech.esper.schedule;

public class ScheduleSetEntry
{
    private Long time;
    private ScheduleSlot slot;
    private ScheduleHandle handle;

    public ScheduleSetEntry(Long time, ScheduleSlot slot, ScheduleHandle handle)
    {
        this.time = time;
        this.slot = slot;
        this.handle = handle;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

    public Long getTime()
    {
        return time;
    }

    public ScheduleSlot getSlot()
    {
        return slot;
    }

    public ScheduleHandle getHandle()
    {
        return handle;
    }
}