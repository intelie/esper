using System;
using System.Collections.Generic;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.schedule
{
    /// <summary> Implements the schedule service by simply keeping a sorted set of long millisecond
    /// values and a set of callbacks for each.
    /// </summary>

    public sealed class SchedulingServiceImpl : SchedulingService
    {
        // Map of time and callback
        private readonly ETreeDictionary<long, ETreeDictionary<ScheduleSlot, ScheduleCallback>> timeCallbackMap;

        // Map of callback and callback list for faster removal
        private readonly EDictionary<ScheduleCallback, ETreeDictionary<ScheduleSlot, ScheduleCallback>> callbackSetMap;

        // Current time - used for evaluation as well as for adding new callbacks
        private long currentTime;

        // Current bucket number - for use in ordering callbacks by bucket
        private int curBucketNum;

        /// <summary> Constructor.</summary>
        public SchedulingServiceImpl()
        {
            this.timeCallbackMap = new ETreeDictionary<long, ETreeDictionary<ScheduleSlot, ScheduleCallback>>();
            this.callbackSetMap = new EHashDictionary<ScheduleCallback, ETreeDictionary<ScheduleSlot, ScheduleCallback>>();
            this.currentTime = DateTimeHelper.TimeInMillis( DateTime.Now );
        }

        public ScheduleBucket allocateBucket()
        {
            curBucketNum++;
            return new ScheduleBucket(curBucketNum);
        }

        public long Time
        {
            get { return this.currentTime; }
            set { this.currentTime = value; }
        }

        public void Add(long afterMSec, ScheduleCallback callback, ScheduleSlot slot)
        {
            if (callbackSetMap.ContainsKey(callback))
            {
                String message = "Callback already in collection";
                SchedulingServiceImpl.log.Fatal(".add " + message);
                throw new ScheduleServiceException(message);
            }

            long triggerOnTime = currentTime + afterMSec;

            addTrigger(slot, callback, triggerOnTime);
        }

        public void Add(ScheduleSpec spec, ScheduleCallback callback, ScheduleSlot slot)
        {
            if (callbackSetMap.ContainsKey(callback))
            {
                String message = "Callback already in collection";
                SchedulingServiceImpl.log.Fatal(".add " + message);
                throw new ScheduleServiceException(message);
            }

            long nextScheduledTime = ScheduleComputeHelper.ComputeNextOccurance(spec, currentTime);

            if (nextScheduledTime <= currentTime)
            {
                String message = "Schedule computation returned invalid time, operation not completed";
                SchedulingServiceImpl.log.Fatal(".add " + message + "  nextScheduledTime=" + nextScheduledTime + "  currentTime=" + currentTime);
                return;
            }

            addTrigger(slot, callback, nextScheduledTime);
        }

        public void Remove(ScheduleCallback callback, ScheduleSlot slot)
        {
            ETreeDictionary<ScheduleSlot, ScheduleCallback> callbackSet = callbackSetMap.Fetch( callback ) ;
            if ( callbackSet == null )
            {
                String message = "Callback cannot be located in collection";
                SchedulingServiceImpl.log.Fatal(".remove " + message);
                throw new ScheduleServiceException(message);
            }
            callbackSet.Remove(slot);
            callbackSetMap.Remove(callback);
        }

        public void Evaluate()
        {
            // Get the values on or before the current time - to get those that are exactly on the
            // current time we just add one to the current time for getting the head map
            ETreeDictionary<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>> headMap = timeCallbackMap.Head( currentTime + 1 );

            IList<ScheduleCallback> triggerables = new List<ScheduleCallback>();

            // First determine all triggers to shoot
            IList<Int64> removeKeys = new List<Int64>();
            foreach (KeyValuePair<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>> entry in headMap)
            {
                removeKeys.Add(entry.Key);
                foreach (ScheduleCallback callback in entry.Value.Values)
                {
                    triggerables.Add(callback);
                }
            }

            // Then call all triggers
            // Trigger callbacks can themselves remove further callbacks
            foreach (ScheduleCallback triggerable in triggerables)
            {
                triggerable.scheduledTrigger();
            }

            // Next remove all callbacks
            foreach (KeyValuePair<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>> entry in headMap)
            {
                foreach (ScheduleCallback callback in entry.Value.Values)
                {
                    callbackSetMap.Remove(callback);
                }
            }

            // Remove all triggered msec values
            foreach (Int64 key in removeKeys)
            {
                timeCallbackMap.Remove(key);
            }
        }

        private void addTrigger(ScheduleSlot slot, ScheduleCallback callback, long triggerTime)
        {
            ETreeDictionary<ScheduleSlot, ScheduleCallback> callbackSet = timeCallbackMap.Fetch(triggerTime);
            if (callbackSet == null)
            {
                callbackSet = new ETreeDictionary<ScheduleSlot, ScheduleCallback>();
                timeCallbackMap[triggerTime] = callbackSet;
            }
            callbackSet[slot] = callback;
            callbackSetMap[callback] = callbackSet;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(SchedulingServiceImpl));
    }
}
