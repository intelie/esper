/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Implements the schedule service by simply keeping a sorted set of long millisecond
 * values and a set of handles for each.
 * <p>
 * Synchronized since statement creation and event evaluation by multiple (event send) threads
 * can lead to callbacks added/removed asynchronously.
 */
public final class SchedulingServiceImpl implements SchedulingService
{
    // Map of time and handle
    private final SortedMap<Long, SortedMap<ScheduleSlot, ScheduleHandle>> timeHandleMap;

    // Map of handle and handle list for faster removal
    private final Map<ScheduleHandle, SortedMap<ScheduleSlot, ScheduleHandle>> handleSetMap;

    // Current time - used for evaluation as well as for adding new handles
    private long currentTime;

    // Current bucket number - for use in ordering handles by bucket
    private int curBucketNum;

    /**
     * Constructor.
     */
    public SchedulingServiceImpl()
    {
        this.timeHandleMap = new TreeMap<Long, SortedMap<ScheduleSlot, ScheduleHandle>>();
        this.handleSetMap = new Hashtable<ScheduleHandle, SortedMap<ScheduleSlot, ScheduleHandle>>();
        this.currentTime = System.currentTimeMillis();
    }

    public synchronized ScheduleBucket allocateBucket()
    {
        curBucketNum++;
        return new ScheduleBucket(curBucketNum);
    }

    public synchronized long getTime()
    {
        return this.currentTime;
    }

    public synchronized final void setTime(long currentTime)
    {
        this.currentTime = currentTime;
    }

    public synchronized final void add(long afterMSec, ScheduleHandle handle, ScheduleSlot slot)
            throws ScheduleServiceException
    {
        if (handleSetMap.containsKey(handle))
        {
            String message = "Handle already in collection";
            SchedulingServiceImpl.log.fatal(".add " + message);
            throw new ScheduleHandleExistsException(message);
        }

        long triggerOnTime = currentTime + afterMSec;

        addTrigger(slot, handle, triggerOnTime);
    }

    public synchronized final void add(ScheduleSpec spec, ScheduleHandle handle, ScheduleSlot slot)
    {
        if (handleSetMap.containsKey(handle))
        {
            String message = "Handle already in collection";
            SchedulingServiceImpl.log.fatal(".add " + message);
            throw new ScheduleHandleExistsException(message);
        }

        long nextScheduledTime = ScheduleComputeHelper.computeNextOccurance(spec, currentTime);

        if (nextScheduledTime <= currentTime)
        {
            String message = "Schedule computation returned invalid time, operation not completed";
            SchedulingServiceImpl.log.fatal(".add " + message + "  nextScheduledTime=" + nextScheduledTime + "  currentTime=" + currentTime);
            assert false;
            return;
        }

        addTrigger(slot, handle, nextScheduledTime);
    }

    public synchronized final void remove(ScheduleHandle handle, ScheduleSlot slot)
    {
        SortedMap<ScheduleSlot, ScheduleHandle> handleSet = handleSetMap.get(handle);
        if (handleSet == null)
        {
            // If it already has been removed then that's fine;
            // Such could be the case when 2 timers fireStatementStopped at the same time, and one stops the other
            return;
        }
        handleSet.remove(slot);
        handleSetMap.remove(handle);
    }


    public void evaluateLock()
    {
        // no additional locking before evaluation required
    }

    public void evaluateUnLock()
    {
        // no additional locking before evaluation required
    }

    public synchronized final void evaluate(Collection<ScheduleHandle> handles)
    {
        // Get the values on or before the current time - to get those that are exactly on the
        // current time we just add one to the current time for getting the head map
        SortedMap<Long, SortedMap<ScheduleSlot, ScheduleHandle>> headMap = timeHandleMap.headMap(currentTime + 1);

        // First determine all triggers to shoot
        List<Long> removeKeys = new LinkedList<Long>();
        for (Long key : headMap.keySet())
        {
            SortedMap<ScheduleSlot, ScheduleHandle> value = headMap.get(key);
            removeKeys.add(key);
            for (ScheduleHandle handle : value.values())
            {
                handles.add(handle);
            }
        }

        // Next remove all handles
        for (Map.Entry<Long, SortedMap<ScheduleSlot, ScheduleHandle>> entry : headMap.entrySet())
        {
            for (ScheduleHandle handle : entry.getValue().values())
            {
                handleSetMap.remove(handle);
            }
        }

        // Remove all triggered msec values
        for (Long key : removeKeys)
        {
            timeHandleMap.remove(key);
        }
    }

    private void addTrigger(ScheduleSlot slot, ScheduleHandle handle, long triggerTime)
    {
        SortedMap<ScheduleSlot, ScheduleHandle> handleSet = timeHandleMap.get(triggerTime);
        if (handleSet == null)
        {
            handleSet = new TreeMap<ScheduleSlot, ScheduleHandle>();
            timeHandleMap.put(triggerTime, handleSet);
        }
        handleSet.put(slot, handle);
        handleSetMap.put(handle, handleSet);
    }

    private static final Log log = LogFactory.getLog(SchedulingServiceImpl.class);
}