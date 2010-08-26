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
import java.util.LinkedHashMap;
import java.util.Set;

public class AggregationAccessJoinImpl implements AggregationAccess
{
    private int streamId;
    private LinkedHashMap<EventBean, Integer> refSet = new LinkedHashMap<EventBean, Integer>();

    public AggregationAccessJoinImpl(int streamId)
    {
        this.streamId = streamId;
    }

    public void applyEnter(EventBean[] eventsPerStream)
    {
        EventBean event = eventsPerStream[streamId];
        if (event ==null) {
            return;
        }
        Integer value = refSet.get(event);
        if (value == null)
        {
            refSet.put(event, 1);
            return;
        }

        value++;
        refSet.put(event, value);
    }

    public void applyLeave(EventBean[] eventsPerStream)
    {
        EventBean event = eventsPerStream[streamId];
        if (event == null) {
            return;
        }

        Integer value = refSet.get(event);
        if (value == null)
        {
            return;
        }

        if (value == 1)
        {
            refSet.remove(event);
            return;
        }

        value--;
        refSet.put(event, value);
    }

    public EventBean getNthPriorValue(int index)
    {
        Set<EventBean> events = refSet.keySet();
        EventBean[] array = events.toArray(new EventBean[events.size()]);
        return array[index];
    }

    public EventBean getFirstValue() {
        if (refSet.isEmpty()) {
            return null;
        }
        return refSet.entrySet().iterator().next().getKey();
    }

    public EventBean getLastValue()
    {
        if (refSet.isEmpty()) {
            return null;
        }
        // TODO - more effective
        Set<EventBean> events = refSet.keySet();
        EventBean[] array = events.toArray(new EventBean[events.size()]);
        return array[events.size() - 1];
    }

    public Iterator<EventBean> iterator()
    {
        Set<EventBean> events = refSet.keySet();
        return events.iterator();
    }

    public int size()
    {
        return refSet.size();
    }
}