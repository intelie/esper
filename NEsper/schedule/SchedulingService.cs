using System;
using System.Collections.Generic;

namespace net.esper.schedule
{
	/// <summary>
	/// Interface for a service that allows to add and remove handles (typically storing callbacks)
	/// for a certain time which are returned when
	/// the evaluate method is invoked and the current time is on or after the handle's registered time.
	/// It is the expectation that the setTime method is called
	/// with same or ascending values for each subsequent call. Handles with are triggered are automatically removed
	/// by implementations.
	/// </summary>
    
    public interface SchedulingService
    {
        /// <summary> Add a callback for after the given milliseconds from the current time.
        /// If the same callback (equals) was already added before, the method will not add a new
        /// callback or change the existing callback to a new time, but throw an exception.
        /// </summary>
        /// <param name="afterMSec">number of millisec to get a callback
        /// </param>
        /// <param name="handle">to add
        /// </param>
        /// <param name="slot">allows ordering of concurrent callbacks
        /// </param>
        /// <throws>  ScheduleServiceException thrown if the add operation did not complete </throws>
        
        void Add(long afterMSec, ScheduleHandle handle, ScheduleSlot slot);

        /// <summary> Add a callback for a time specified by the schedule specification passed in based on the current time.
        /// If the same callback (equals) was already added before, the method will not add a new
        /// callback or change the existing callback to a new time, but throw an exception.
        /// </summary>
        /// <param name="scheduleSpec">holds the crontab-like information defining the next occurance
        /// </param>
        /// <param name="handle">to add
        /// </param>
        /// <param name="slot">allows ordering of concurrent callbacks
        /// </param>
        /// <throws>  ScheduleServiceException thrown if the add operation did not complete </throws>
        
        void Add(ScheduleSpec scheduleSpec, ScheduleHandle handle, ScheduleSlot slot);

        /// <summary> Remove a callback.
        /// If the callback to be removed was not found an exception is thrown.
        /// </summary>
        /// <param name="handle">to remove
        /// </param>
        /// <param name="slot">for which the callback was added
        /// </param>
        /// <throws>  ScheduleServiceException thrown if the callback was not located </throws>
        
        void Remove(ScheduleHandle handle, ScheduleSlot slot);

        /// <summary> Gets the last time known to the scheduling service.</summary>
        /// <summary> Set the time based upon which the evaluation of events invokes callbacks.</summary>
        /// <returns> time that has last been set on this service
        /// </returns>

        long Time
        {
            get;
            set;
        }

	    /// <summary>
	    /// Scheduling service evaluation lock to synchronize evaluation with engine locks.
	    /// </summary>
	    void EvaluateLock();

	    /// <summary>
	    /// Scheduling service evaluation unlock to synchronize evaluation with engine locks.
	    /// </summary>
	    void EvaluateUnLock();
		
        /// <summary> Evaluate the current time and perform any callbacks.</summary>
        
        void Evaluate(ICollection<ScheduleHandle> handles);

        /// <summary> Returns a bucket from which slots can be allocated for ordering concurrent callbacks.</summary>
        /// <returns> bucket
        /// </returns>
 
        ScheduleBucket AllocateBucket();
    }
}
