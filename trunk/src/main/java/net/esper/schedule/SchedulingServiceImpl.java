package net.esper.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Implements the schedule service by simply keeping a sorted set of long millisecond
 * values and a set of callbacks for each.
 */
public final class SchedulingServiceImpl implements SchedulingService
{
    // Map of time and callback
    private final SortedMap<Long, Set<ScheduleCallback>> timeCallbackMap;

    // Map of callback and callback list for faster removal
    private final Map<ScheduleCallback, Set<ScheduleCallback>> callbackSetMap;

    // Current time - used for evaluation as well as for adding new callbacks
    private long currentTime;

    /**
     * Constructor.
     */
    public SchedulingServiceImpl()
    {
        this.timeCallbackMap = new TreeMap<Long, Set<ScheduleCallback>>();
        this.callbackSetMap = new Hashtable<ScheduleCallback, Set<ScheduleCallback>>();
        this.currentTime = System.currentTimeMillis();
    }

    public long getTime()
    {
        return this.currentTime;
    }

    public final void setTime(long currentTime)
    {
        this.currentTime = currentTime;
    }

    public final void add(long afterMSec, ScheduleCallback callback)
            throws ScheduleServiceException
    {
        if (callbackSetMap.containsKey(callback))
        {
            String message = "Callback already in collection";
            log.fatal(".add " + message);
            throw new ScheduleServiceException(message);
        }

        long triggerOnTime = currentTime + afterMSec;

        addTrigger(callback, triggerOnTime);
    }

    public final void add(ScheduleSpec spec, ScheduleCallback callback)
    {
        if (callbackSetMap.containsKey(callback))
        {
            String message = "Callback already in collection";
            log.fatal(".add " + message);
            throw new ScheduleServiceException(message);
        }

        long nextScheduledTime = ScheduleComputeHelper.computeNextOccurance(spec, currentTime);

        if (nextScheduledTime <= currentTime)
        {
            String message = "Schedule computation returned invalid time, operation not completed";
            log.fatal(".add " + message + "  nextScheduledTime=" + nextScheduledTime + "  currentTime=" + currentTime);
            assert false;
            return;
        }

        addTrigger(callback, nextScheduledTime);
    }

    public final void remove(ScheduleCallback callback)
    {
        Set<ScheduleCallback> callbackSet = callbackSetMap.get(callback);
        if (callbackSet == null)
        {
            String message = "Callback cannot be located in collection";
            log.fatal(".remove " + message);
            throw new ScheduleServiceException(message);
        }
        callbackSet.remove(callback);
        callbackSetMap.remove(callback);
    }

    public final void evaluate()
    {
        // Get the values on or before the current time - to get those that are exactly on the
        // current time we just add one to the current time for getting the head map
        SortedMap<Long, Set<ScheduleCallback>> headMap = timeCallbackMap.headMap(currentTime + 1);

        List<ScheduleCallback> triggerables = new LinkedList<ScheduleCallback>();

        // First determine all triggers to shoot
        for (Map.Entry<Long, Set<ScheduleCallback>> entry : headMap.entrySet())
        {
            for (ScheduleCallback callback : entry.getValue())
            {
                triggerables.add(callback);
            }
        }

        // Then call all triggers
        // Trigger callbacks can themselves remove further callbacks
        for (ScheduleCallback triggerable : triggerables)
        {
            triggerable.scheduledTrigger();
        }

        // Next remove all callbacks
        List<Long> removeKeys = new LinkedList<Long>();
        for (Map.Entry<Long, Set<ScheduleCallback>> entry : headMap.entrySet())
        {
            for (ScheduleCallback callback : entry.getValue())
            {
                callbackSetMap.remove(callback);
            }
            removeKeys.add(entry.getKey());
        }

        // Remove all triggered msec values
        for (Long key : removeKeys)
        {
            timeCallbackMap.remove(key);
        }
    }

    private void addTrigger(ScheduleCallback callback, long triggerTime)
    {
        Set<ScheduleCallback> callbackSet = timeCallbackMap.get(triggerTime);
        if (callbackSet == null)
        {
            callbackSet = new HashSet<ScheduleCallback>();
            timeCallbackMap.put(triggerTime, callbackSet);
        }
        callbackSet.add(callback);
        callbackSetMap.put(callback, callbackSet);
    }

    private static final Log log = LogFactory.getLog(SchedulingServiceImpl.class);
}
