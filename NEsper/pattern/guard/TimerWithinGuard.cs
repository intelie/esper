using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.schedule;

namespace net.esper.pattern.guard
{
	
	/// <summary> Guard implementation that keeps a timer instance and quits when the timer expired,
	/// letting all {@link MatchedEventMap} instances pass until then.
	/// </summary>
	
    public class TimerWithinGuard : Guard, ScheduleCallback
	{
		private readonly long msec;
		private readonly PatternContext context;
		private readonly Quitable quitable;
		private readonly ScheduleSlot scheduleSlot;
		
		private bool isTimerActive;
		
		/// <summary> Ctor.</summary>
		/// <param name="msec">- number of millisecond to guard expiration
		/// </param>
		/// <param name="context">- contains timer service
		/// </param>
		/// <param name="quitable">- to use to indicate that the gaurd quitted
		/// </param>
		public TimerWithinGuard(long msec, PatternContext context, Quitable quitable)
		{
			this.msec = msec;
			this.context = context;
			this.quitable = quitable;
			this.scheduleSlot = context.ScheduleBucket.AllocateSlot();
		}
		
		public virtual void  StartGuard()
		{
			if (isTimerActive == true)
			{
				throw new SystemException("Timer already active");
			}
			
			// Start the Stopwatch timer
			context.SchedulingService.Add(msec, this, scheduleSlot);
			isTimerActive = true;
		}
		
		public virtual void  StopGuard()
		{
			if (isTimerActive)
			{
				context.SchedulingService.Remove(this, scheduleSlot);
				isTimerActive = false;
			}
		}
		
		public virtual bool inspect(MatchedEventMap matchEvent)
		{
			// no need to test: for timing only, if the timer expired the guardQuit Stops any events from coming here
			return true;
		}
		
		public void  scheduledTrigger()
		{
			// Timer callback is automatically removed when triggering
			isTimerActive = false;
			quitable.guardQuit();
		}
	}
}
