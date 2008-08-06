package com.espertech.esper.epl.metric;

/**
 * Handle for statements metric reporting by runtime.
 */
public class StatementMetricHandle
{
    private final int groupNum;
    private final int index;

    /**
     * Ctor.
     * @param groupNum group number, zero for default group
     * @param index index slot
     */
    public StatementMetricHandle(int groupNum, int index)
    {
        this.groupNum = groupNum;
        this.index = index;
    }

    /**
     * Returns group number for statement.
     * @return group number
     */
    public int getGroupNum()
    {
        return groupNum;
    }

    /**
     * Returns slot number of metric.
     * @return metric index
     */
    public int getIndex()
    {
        return index;
    }
}
