using net.esper.core;
using net.esper.compat;
using net.esper.pattern;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.pattern.observer
{
    /// <summary>
    /// Observer implementation for indicating that a certain time arrived, similar to "crontab".
    /// </summary>

    public class TimerAtObserver 
		: EventObserver
		, ScheduleHandleCallback
    {
        private readonly ScheduleSpec scheduleSpec;
        private readonly PatternContext context;
        private readonly ScheduleSlot scheduleSlot;
        private readonly MatchedEventMap beginState;
        private readonly ObserverEventEvaluator observerEventEvaluator;

        private bool isTimerActive = false;
		private EPStatementHandleCallback scheduleHandle;

        /// <summary> Ctor.</summary>
        /// <param name="scheduleSpec">specification containing the crontab schedule
        /// </param>
        /// <param name="context">timer serive to use
        /// </param>
        /// <param name="beginState">Start state
        /// </param>
        /// <param name="observerEventEvaluator">receiver for events
        /// </param>
        public TimerAtObserver(ScheduleSpec scheduleSpec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
        {
            this.scheduleSpec = scheduleSpec;
            this.context = context;
            this.beginState = beginState;
            this.observerEventEvaluator = observerEventEvaluator;
            this.scheduleSlot = context.ScheduleBucket.AllocateSlot();
        }

        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>
        public void ScheduledTrigger(ExtensionServicesContext extensionServicesContext)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".scheduledTrigger");
            }

            observerEventEvaluator.ObserverEvaluateTrue(beginState);
            isTimerActive = false;
        }

        /// <summary>
        /// Start observing.
        /// </summary>
        public virtual void StartObserve()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".StartObserve Starting at, spec=" + scheduleSpec);
            }

            if (isTimerActive == true)
            {
                throw new IllegalStateException("Timer already active");
            }

	        scheduleHandle = new EPStatementHandleCallback(context.EpStatementHandle, this);
	        context.SchedulingService.Add(scheduleSpec, scheduleHandle, scheduleSlot);
            isTimerActive = true;
        }

        /// <summary>
        /// Stop observing.
        /// </summary>
        public virtual void StopObserve()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".StopObserve");
            }

            if (isTimerActive)
            {
	            context.SchedulingService.Remove(scheduleHandle, scheduleSlot);
	            isTimerActive = false;
	            scheduleHandle = null;
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}