using System;

using net.esper.schedule;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.eql.view
{
	/// <summary> Output condition that is satisfied at the end
	/// of every time interval of a given length.
	/// </summary>
	
    public sealed class OutputConditionTime : OutputCondition
	{
		/// <summary> Returns the interval size in milliseconds.</summary>
		/// <returns> batch size
		/// </returns>

        public long MsecIntervalSize
		{
			get { return msecIntervalSize; }
		}

		private const bool DO_OUTPUT = true;
		private const bool FORCE_UPDATE = true;
		
		private readonly long msecIntervalSize;
		private readonly OutputCallback outputCallback;
		private readonly ScheduleSlot scheduleSlot;
		
		private long? currentReferencePoint;
		private ViewServiceContext context;
		private bool isCallbackScheduled;
		
		/// <summary> Constructor.</summary>
		/// <param name="secIntervalSize">is the number of seconds to batch events for.
		/// </param>
		/// <param name="context">is the view context for time scheduling	
		/// </param>
		/// <param name="outputCallback">is the callback to make once the condition is satisfied
		/// </param>
		public OutputConditionTime(double secIntervalSize, ViewServiceContext context, OutputCallback outputCallback)
		{
			if (outputCallback == null)
			{
				throw new System.NullReferenceException("Output condition by count requires a non-null callback");
			}
			if (secIntervalSize < 0.1)
			{
				throw new ArgumentException("Output condition by time requires a millisecond interval size of at least 100 msec");
			}
			if (context == null)
			{
				String message = "OutputConditionTime requires a non-null view context";
				throw new System.NullReferenceException(message);
			}
			
			this.msecIntervalSize = (long) System.Math.Round(1000 * secIntervalSize);
			this.context = context;
			this.outputCallback = outputCallback;
			this.scheduleSlot = context.ScheduleBucket.AllocateSlot();
		}

        /// <summary>
        /// Update the output condition.
        /// </summary>
        /// <param name="newEventsCount">number of new events incoming</param>
        /// <param name="oldEventsCount">number of old events incoming</param>
		public void UpdateOutputCondition(int newEventsCount, int oldEventsCount)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".updateOutputCondition, " + "  newEventsCount==" + newEventsCount + "  oldEventsCount==" + oldEventsCount);
			}
			
			if (currentReferencePoint == null)
			{
				currentReferencePoint = context.SchedulingService.Time;
			}
			
			// Schedule the next callback if there is none currently scheduled
			if (!isCallbackScheduled)
			{
				scheduleCallback();
			}
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return this.GetType().FullName + " msecIntervalSize=" + msecIntervalSize;
		}
		
		/// <summary>
		/// Called by the scheduling service after the requested event has
		/// occurred.
		/// </summary>
		
		private void HandleScheduledCallback()
		{
            isCallbackScheduled = false;
            outputCallback(OutputConditionTime.DO_OUTPUT, OutputConditionTime.FORCE_UPDATE);
            scheduleCallback();
		}
		
		private void scheduleCallback()
		{
			isCallbackScheduled = true;
			long current = context.SchedulingService.Time;
			long afterMSec = computeWaitMSec(current, currentReferencePoint.Value, this.msecIntervalSize);
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".scheduleCallback Scheduled new callback for " + " afterMsec=" + afterMSec + " now=" + current + " currentReferencePoint=" + currentReferencePoint + " msecIntervalSize=" + msecIntervalSize);
			}

			ScheduleCallback callback = new ScheduleCallbackImpl( HandleScheduledCallback ) ;

			context.SchedulingService.Add(afterMSec, callback, scheduleSlot);
		}
		
		/// <summary>
		/// Given a current time and a reference time and an interval size, compute the amount of
		/// milliseconds till the next interval.
		/// </summary>
		/// <param name="current">is the current time</param>
		/// <param name="reference">is the reference point</param>
		/// <param name="interval">is the interval size</param>
		/// <returns>milliseconds after current time that marks the end of the current interval</returns>

		internal static long computeWaitMSec(long current, long reference, long interval)
		{
			// Example:  current c=2300, reference r=1000, interval i=500, solution s=200
			//
			// int n = ((2300 - 1000) / 500) = 2
			// r + (n + 1) * i - c = 200
			//
			// Negative example:  current c=2300, reference r=4200, interval i=500, solution s=400
			// int n = ((2300 - 4200) / 500) = -3
			// r + (n + 1) * i - c = 4200 - 3*500 - 2300 = 400
			//
			long n = (long) ((current - reference) / (interval * 1f));
			if (reference > current)
			// References in the future need to deduct one window
			{
				n = n - 1;
			}
			long solution = reference + (n + 1) * interval - current;
			
			if (solution == 0)
			{
				return interval;
			}
			return solution;
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
