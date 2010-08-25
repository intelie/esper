/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;

import java.util.Iterator;

public class AggregationAccessImpl implements AggregationAccess
{
    private int streamId;
    private ArrayDequeJDK6Backport<EventBean> events = new ArrayDequeJDK6Backport<EventBean>();

    public AggregationAccessImpl(int streamId)
    {
        this.streamId = streamId;
    }

    public void applyLeave(EventBean[] eventsPerStream)
    {
        events.remove(eventsPerStream[streamId]);
    }

    public void applyEnter(EventBean[] eventsPerStream)
    {
        events.add(eventsPerStream[streamId]);
    }

    public EventBean getNthPriorValue(int index)
    {
        EventBean[] all = events.toArray(new EventBean[events.size()]);
        if (all.length < index) {
            return null;
        }
        return all[index];
    }

    public EventBean getFirstValue() {
        if (events.isEmpty()) {
            return null;
        }
        return events.getFirst();
    }

    public EventBean getLastValue()
    {
        if (events.isEmpty()) {
            return null;
        }
        return events.getLast();
    }

    public Iterator<EventBean> iterator()
    {
        return events.iterator();
    }

    public int size()
    {
        return events.size();
    }
}
