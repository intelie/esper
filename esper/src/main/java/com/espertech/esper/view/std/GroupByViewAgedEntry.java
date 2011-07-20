/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.view.std;

import com.espertech.esper.view.View;

import java.util.List;

public class GroupByViewAgedEntry
{
    private final List<View> subviews;
    private long lastUpdateTime;

    public GroupByViewAgedEntry(List<View> subviews, long lastUpdateTime)
    {
        this.subviews = subviews;
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<View> getSubviews()
    {
        return subviews;
    }

    public long getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
}
