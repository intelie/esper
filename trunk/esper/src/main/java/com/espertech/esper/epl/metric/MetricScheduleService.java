/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

import com.espertech.esper.schedule.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public final class MetricScheduleService implements MetricTimeSource
{
    private static final Log log = LogFactory.getLog(MetricScheduleService.class);

    private final SortedMap<Long, List<MetricExecution>> timeHandleMap;

    // Current time - used for evaluation as well as for adding new handles
    private volatile Long currentTime;

    private volatile Long nearestTime;

    /**
     * Constructor.
     */
    public MetricScheduleService()
    {
        this.timeHandleMap = new TreeMap<Long, List<MetricExecution>>();
    }

    public long getCurrentTime()
    {
        return currentTime;
    }

    public void destroy()
    {
        log.debug("Destroying scheduling service");
        timeHandleMap.clear();
        nearestTime = null;
    }

    public synchronized final void setTime(long currentTime)
    {
        this.currentTime = currentTime;
    }

    public synchronized final void add(long afterMSec, MetricExecution execution)
            throws ScheduleServiceException
    {
        long triggerOnTime = currentTime + afterMSec;
        List<MetricExecution> handleSet = timeHandleMap.get(triggerOnTime);
        if (handleSet == null)
        {
            handleSet = new ArrayList<MetricExecution>();
            timeHandleMap.put(triggerOnTime, handleSet);
        }
        handleSet.add(execution);

        nearestTime = timeHandleMap.firstKey();
    }

    public synchronized final void evaluate(Collection<MetricExecution> handles)
    {
        SortedMap<Long, List<MetricExecution>> headMap = timeHandleMap.headMap(currentTime + 1);

        // First determine all triggers to shoot
        List<Long> removeKeys = new LinkedList<Long>();
        for (Map.Entry<Long, List<MetricExecution>> entry : headMap.entrySet())
        {
            Long key = entry.getKey();
            List<MetricExecution> value = entry.getValue();
            removeKeys.add(key);
            for (MetricExecution handle : value)
            {
                handles.add(handle);
            }
        }

        // Remove all triggered msec values
        for (Long key : removeKeys)
        {
            timeHandleMap.remove(key);
        }

        if (!timeHandleMap.isEmpty())
        {
            nearestTime = timeHandleMap.firstKey();
        }
        else
        {
            nearestTime = null;
        }
    }

    public Long getNearestTime()
    {
        return nearestTime;
    }
}
