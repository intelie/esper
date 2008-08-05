package com.espertech.esper.epl.metric;

public class StatementMetricHandle
{
    private final int groupNum;
    private final int index;

    public StatementMetricHandle(int groupNum, int index)
    {
        this.groupNum = groupNum;
        this.index = index;
    }

    public int getGroupNum()
    {
        return groupNum;
    }

    public int getIndex()
    {
        return index;
    }
}
