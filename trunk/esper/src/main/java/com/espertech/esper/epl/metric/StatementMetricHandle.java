/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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
