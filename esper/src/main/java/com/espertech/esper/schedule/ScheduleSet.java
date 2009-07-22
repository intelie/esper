package com.espertech.esper.schedule;

import java.util.List;

public class ScheduleSet
{
    private List<ScheduleSetEntry> list;

    public ScheduleSet(List<ScheduleSetEntry> list)
    {
        this.list = list;
    }

    public List<ScheduleSetEntry> getList()
    {
        return list;
    }
}
