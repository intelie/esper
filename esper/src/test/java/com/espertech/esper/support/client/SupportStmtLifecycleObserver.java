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

package com.espertech.esper.support.client;

import com.espertech.esper.core.StatementLifecycleObserver;
import com.espertech.esper.core.StatementLifecycleEvent;

import java.util.List;
import java.util.ArrayList;

public class SupportStmtLifecycleObserver implements StatementLifecycleObserver
{
    private List<StatementLifecycleEvent> events = new ArrayList<StatementLifecycleEvent>();
    private Object[] lastContext;

    public void observe(StatementLifecycleEvent event)
    {
        events.add(event);
        lastContext = event.getParams();
    }

    public Object[] getLastContext()
    {
        return lastContext;
    }

    public List<StatementLifecycleEvent> getEvents()
    {
        return events;
    }

    public String getEventsAsString()
    {
        String result = "";
        for (StatementLifecycleEvent event : events) {
            result += event.getEventType().toString() + ";";
        }
        return result;
    }

    public void flush()
    {
        events.clear();
    }
}
