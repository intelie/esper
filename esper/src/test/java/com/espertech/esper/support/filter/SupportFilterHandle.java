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

package com.espertech.esper.support.filter;

import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.client.EventBean;

import java.util.Collection;

public class SupportFilterHandle implements FilterHandleCallback
{
    private int countInvoked;
    private EventBean lastEvent;

    public void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches)
    {
        countInvoked++;
        lastEvent = event;
    }

    public boolean isSubSelect()
    {
        return false;
    }

    public int getCountInvoked()
    {
        return countInvoked;
    }

    public EventBean getLastEvent()
    {
        return lastEvent;
    }

    public void setCountInvoked(int countInvoked)
    {
        this.countInvoked = countInvoked;
    }

    public void setLastEvent(EventBean lastEvent)
    {
        this.lastEvent = lastEvent;
    }

    public int getAndResetCountInvoked()
    {
        int count = countInvoked;
        countInvoked = 0;
        return count;
    }

    public String getStatementId()
    {
        return "";
    }
}
