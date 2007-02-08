using System;

using net.esper.pattern;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.pattern.observer
{
	/// <summary>
    /// Observer implementation for indicating that a certain time arrived, similar to "crontab".
    /// </summary>
	
    public class TimerAtObserver : EventObserver, ScheduleCallback
	{
        private readonly ScheduleSpec scheduleSpec;
        private readonly PatternContext context;
        private readonly ScheduleSlot scheduleSlot;
        private readonly MatchedEventMap beginState;
		private readonly ObserverEventEvaluator observerEventEvaluator;
		
		private bool isTimerActive = false;
		
		/// <summary> Ctor.</summary>
		/// <param name="scheduleSpec">- specification containing the crontab schedule
		/// </param>
		/// <param name="context">- timer serive to use
		/// </param>
		/// <param name="beginState">- Start state
		/// </param>
		/// <param name="observerEventEvaluator">- receiver for events
		/// </param>
		public TimerAtObserver(ScheduleSpec scheduleSpec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
		{
			this.scheduleSpec = scheduleSpec;
			this.context = context;
			this.beginState = beginState;
			this.observerEventEvaluator = observerEventEvaluator;
			this.scheduleSlot = context.ScheduleBucket.allocateSlot();
		}
		
		public void  scheduledTrigger()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".scheduledTrigger");
			}
			
			observerEventEvaluator.observerEvaluateTrue(beginState);
			isTimerActive = false;
		}
		
		public virtual void  StartObserve()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".StartObserve Starting at, spec=" + scheduleSpec);
			}
			
			if (isTimerActive == true)
			{
				throw new SystemException("Timer already active");
			}
			
			context.SchedulingService.Add(scheduleSpec, this, scheduleSlot);
			isTimerActive = true;
		}
		
		public virtual void  StopObserve()
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".StopObserve");
			}
			
			if (isTimerActive)
			{
				context.SchedulingService.Remove(this, scheduleSlot);
				isTimerActive = false;
			}
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TimerAtObserver));
	}
}
