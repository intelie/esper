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

        /// <summary>
        /// Returns a bucket from which slots can be allocated for ordering concurrent callbacks.
        /// </summary>
        /// <returns>bucket</returns>
        public ScheduleBucket AllocateBucket()
        {
            curBucketNum++;
            return new ScheduleBucket(curBucketNum);
        }

        /// <summary>
        /// Gets the last time known to the scheduling service.
        /// </summary>
        /// <value></value>
        /// <returns> time that has last been set on this service
        /// </returns>
        public long Time
        {
            get { return this.currentTime; }
            set { this.currentTime = value; }
        }

        /// <summary>
        /// Add a callback for after the given milliseconds from the current time.
        /// If the same callback (equals) was already added before, the method will not add a new
        /// callback or change the existing callback to a new time, but throw an exception.
        /// </summary>
        /// <param name="afterMSec">number of millisec to get a callback</param>
        /// <param name="callback">to add</param>
        /// <param name="slot">allows ordering of concurrent callbacks</param>
        /// <throws>  ScheduleServiceException thrown if the add operation did not complete </throws>
        public void Add(long afterMSec, ScheduleCallback callback, ScheduleSlot slot)
        {
            if (callbackSetMap.ContainsKey(callback))
            {
                String message = "Callback already in collection";
                SchedulingServiceImpl.log.Fatal(".add " + message);
                throw new ScheduleServiceException(message);
            }

            long triggerOnTime = currentTime + afterMSec;

            AddTrigger(slot, callback, triggerOnTime);
        }

        /// <summary>
        /// Adds the specified spec.
        /// </summary>
        /// <param name="spec">The spec.</param>
        /// <param name="callback">The callback.</param>
        /// <param name="slot">The slot.</param>
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

            AddTrigger(slot, callback, nextScheduledTime);
        }

        /// <summary>
        /// Remove a callback.
        /// If the callback to be removed was not found an exception is thrown.
        /// </summary>
        /// <param name="callback">to remove</param>
        /// <param name="slot">for which the callback was added</param>
        /// <throws>  ScheduleServiceException thrown if the callback was not located </throws>
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

        /// <summary>
        /// Evaluate the current time and perform any callbacks.
        /// </summary>
        public void Evaluate()
        {
            // Get the values on or before the current time - to get those that are exactly on the
            // current time we just add one to the current time for getting the head map
            IEnumerator<KeyValuePair<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>>> headMapEnum ;

            IList<ScheduleCallback> triggerables = new List<ScheduleCallback>();

            // First determine all triggers to shoot
            IList<Int64> removeKeys = new List<Int64>();

            for( headMapEnum = timeCallbackMap.HeadFast(currentTime + 1) ; headMapEnum.MoveNext() ; )
            {
                KeyValuePair<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>> entry = headMapEnum.Current ;

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
                triggerable.ScheduledTrigger();
            }

            // Next remove all callbacks
            for (headMapEnum = timeCallbackMap.HeadFast(currentTime + 1); headMapEnum.MoveNext(); )
            {
                KeyValuePair<Int64, ETreeDictionary<ScheduleSlot, ScheduleCallback>> entry = headMapEnum.Current;

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

        private void AddTrigger(ScheduleSlot slot, ScheduleCallback callback, long triggerTime)
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

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
