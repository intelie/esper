using System;
using System.Collections.Generic;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.schedule
{
    /// <summary>
	/// Implements the schedule service by simply keeping a sorted set of long millisecond
    /// values and a set of handles for each.
    /// </summary>

    public sealed class SchedulingServiceImpl : SchedulingService
    {
        // Map of time and handle
        private readonly TreeDictionary<long, TreeDictionary<ScheduleSlot, ScheduleHandle>> timeHandleMap;

        // Map of handle and handle list for faster removal
        private readonly EDictionary<ScheduleHandle, TreeDictionary<ScheduleSlot, ScheduleHandle>> handleSetMap;

        // Current time - used for evaluation as well as for adding new handles
        private long currentTime;

        // Current bucket number - for use in ordering handles by bucket
        private int curBucketNum;

        /// <summary> Constructor.</summary>
        public SchedulingServiceImpl()
        {
            this.timeHandleMap = new TreeDictionary<long, TreeDictionary<ScheduleSlot, ScheduleHandle>>();
            this.handleSetMap = new HashDictionary<ScheduleHandle, TreeDictionary<ScheduleSlot, ScheduleHandle>>();
            this.currentTime = DateTimeHelper.TimeInMillis( DateTime.Now );
        }

        /// <summary>
        /// Returns a bucket from which slots can be allocated for ordering concurrent handles.
        /// </summary>
        /// <returns>bucket</returns>
        public ScheduleBucket AllocateBucket()
        {
			lock( this )
			{
				curBucketNum++;
				return new ScheduleBucket(curBucketNum);
			}
        }

        /// <summary>
        /// Gets the last time known to the scheduling service.
        /// </summary>
        /// <value></value>
        /// <returns> time that has last been set on this service
        /// </returns>
        public long Time
        {
            get { lock( this ) { return this.currentTime; } }
            set { lock( this ) { this.currentTime = value; } }
        }

        /// <summary>
        /// Add a handle for after the given milliseconds from the current time.
        /// If the same handle (equals) was already added before, the method will not add a new
        /// handle or change the existing handle to a new time, but throw an exception.
        /// </summary>
        /// <param name="afterMSec">number of millisec to get a handle</param>
        /// <param name="handle">to add</param>
        /// <param name="slot">allows ordering of concurrent handles</param>
        /// <throws>  ScheduleServiceException thrown if the add operation did not complete </throws>
        public void Add(long afterMSec, ScheduleHandle handle, ScheduleSlot slot)
        {
			lock( this )
			{
	            if (handleSetMap.ContainsKey(handle))
	            {
	                String message = "Handle already in collection";
	                SchedulingServiceImpl.log.Fatal(".add " + message);
	                throw new ScheduleServiceException(message);
	            }

	            long triggerOnTime = currentTime + afterMSec;

	            AddTrigger(slot, handle, triggerOnTime);
			}
        }

        /// <summary>
        /// Adds the specified spec.
        /// </summary>
        /// <param name="spec">The spec.</param>
        /// <param name="handle">The handle.</param>
        /// <param name="slot">The slot.</param>
        public void Add(ScheduleSpec spec, ScheduleHandle handle, ScheduleSlot slot)
        {
			lock( this )
			{
	            if (handleSetMap.ContainsKey(handle))
	            {
	                String message = "Handle already in collection";
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

	            AddTrigger(slot, handle, nextScheduledTime);
			}
        }

        /// <summary>
        /// Remove a handle.
        /// If the handle to be removed was not found an exception is thrown.
        /// </summary>
        /// <param name="handle">to remove</param>
        /// <param name="slot">for which the handle was added</param>
        /// <throws>  ScheduleServiceException thrown if the handle was not located </throws>
        public void Remove(ScheduleHandle handle, ScheduleSlot slot)
        {
			lock( this )
			{
	            TreeDictionary<ScheduleSlot, ScheduleHandle> handleSet = handleSetMap.Fetch( handle ) ;
	            if ( handleSet == null )
	            {
	                String message = "Handle cannot be located in collection";
	                SchedulingServiceImpl.log.Fatal(".remove " + message);
	                throw new ScheduleServiceException(message);
	            }
	            handleSet.Remove(slot);
	            handleSetMap.Remove(handle);
			}
        }

        /// <summary>
        /// Scheduling service evaluation lock to synchronize evaluation with engine locks.
        /// </summary>
	    public void EvaluateLock()
	    {
	        // no additional locking before evaluation required
	    }

        /// <summary>
        /// Scheduling service evaluation unlock to synchronize evaluation with engine locks.
        /// </summary>
	    public void EvaluateUnLock()
	    {
	        // no additional locking before evaluation required
	    }
	
        /// <summary>
        /// Evaluate the current time and perform any handles.
        /// </summary>
        public void Evaluate(ICollection<ScheduleHandle> handles)
        {
			lock( this )
			{
	            // Get the values on or before the current time - to get those that are exactly on the
	            // current time we just add one to the current time for getting the head map
	            IEnumerator<KeyValuePair<Int64, TreeDictionary<ScheduleSlot, ScheduleHandle>>> headMapEnum ;

	            // First determine all triggers to shoot
	            IList<Int64> removeKeys = new List<Int64>();

	            for( headMapEnum = timeHandleMap.HeadFast(currentTime + 1) ; headMapEnum.MoveNext() ; )
	            {
	                KeyValuePair<Int64, TreeDictionary<ScheduleSlot, ScheduleHandle>> entry = headMapEnum.Current ;

	                removeKeys.Add(entry.Key);
	                foreach (ScheduleHandle handle in entry.Value.Values)
	                {
	                    handles.Add(handle);
	                }
	            }

	            // Next remove all handles
	            for (headMapEnum = timeHandleMap.HeadFast(currentTime + 1); headMapEnum.MoveNext(); )
	            {
	                KeyValuePair<Int64, TreeDictionary<ScheduleSlot, ScheduleHandle>> entry = headMapEnum.Current;

	                foreach (ScheduleHandle handle in entry.Value.Values)
	                {
	                    handleSetMap.Remove(handle);
	                }
	            }

	            // Remove all triggered msec values
	            foreach (Int64 key in removeKeys)
	            {
	                timeHandleMap.Remove(key);
	            }
			}
        }

        private void AddTrigger(ScheduleSlot slot, ScheduleHandle handle, long triggerTime)
        {
            TreeDictionary<ScheduleSlot, ScheduleHandle> handleSet = timeHandleMap.Fetch(triggerTime);
            if (handleSet == null)
            {
                handleSet = new TreeDictionary<ScheduleSlot, ScheduleHandle>();
                timeHandleMap[triggerTime] = handleSet;
            }

            handleSet[slot] = handle;
            handleSetMap[handle] = handleSet;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
