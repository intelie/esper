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

import com.espertech.esper.filter.EventEvaluator;
import com.espertech.esper.filter.FilterHandle;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.List;
import java.util.Collection;

public class SupportEventEvaluator implements EventEvaluator
{
    private int countInvoked;
    private EventBean lastEvent;
    private Collection<FilterHandle> lastMatches;

    public void matchEvent(EventBean event, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        countInvoked++;
        lastEvent = event;
        lastMatches = matches;
    }

    public EventBean getLastEvent()
    {
        return lastEvent;
    }

    public Collection<FilterHandle> getLastMatches()
    {
        return lastMatches;
    }

    public void setCountInvoked(int countInvoked)
    {
        this.countInvoked = countInvoked;
    }

    public void setLastEvent(EventBean lastEvent)
    {
        this.lastEvent = lastEvent;
    }

    public void setLastMatches(List<FilterHandle> lastMatches)
    {
        this.lastMatches = lastMatches;
    }

    public int getAndResetCountInvoked()
    {
        int count = countInvoked;
        countInvoked = 0;
        return count;
    }
}
