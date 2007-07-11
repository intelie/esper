using net.esper.core;
using net.esper.compat;
using net.esper.pattern;
using net.esper.schedule;

namespace net.esper.pattern.guard
{
    /// <summary> Guard implementation that keeps a timer instance and quits when the timer expired,
    /// letting all <seealso cref="MatchedEventMap"/> instances pass until then.
    /// </summary>

    public class TimerWithinGuard
		: Guard
		, ScheduleHandleCallback
    {
        private readonly long msec;
        private readonly PatternContext context;
        private readonly Quitable quitable;
        private readonly ScheduleSlot scheduleSlot;

        private bool isTimerActive;
		private EPStatementHandleCallback scheduleHandle;

        /// <summary> Ctor.</summary>
        /// <param name="msec">number of millisecond to guard expiration
        /// </param>
        /// <param name="context">contains timer service
        /// </param>
        /// <param name="quitable">to use to indicate that the gaurd quitted
        /// </param>
        public TimerWithinGuard(long msec, PatternContext context, Quitable quitable)
        {
            this.msec = msec;
            this.context = context;
            this.quitable = quitable;
            this.scheduleSlot = context.ScheduleBucket.AllocateSlot();
        }

        /// <summary>
        /// Start the guard operation.
        /// </summary>
        public virtual void StartGuard()
        {
            if (isTimerActive == true)
            {
                throw new IllegalStateException("Timer already active");
            }

            // Start the stopwatch timer
	        scheduleHandle = new EPStatementHandleCallback(context.EpStatementHandle, this);
	        context.SchedulingService.Add(msec, scheduleHandle, scheduleSlot);
            isTimerActive = true;
        }

        /// <summary>
        /// Called when sub-expression quits, or when the pattern Stopped.
        /// </summary>
        public virtual void StopGuard()
        {
            if (isTimerActive)
            {
                context.SchedulingService.Remove(scheduleHandle, scheduleSlot);
				scheduleHandle = null;
                isTimerActive = false;
            }
        }

        /// <summary>
        /// Returns true if inspection shows that the match events can pass, or false to not pass.
        /// </summary>
        /// <param name="matchEvent"></param>
        /// <returns>true to pass, false to not pass</returns>
        public virtual bool Inspect(MatchedEventMap matchEvent)
        {
            // no need to test: for timing only, if the timer expired the guardQuit Stops any events from coming here
            return true;
        }

        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>
        public void ScheduledTrigger(ExtensionServicesContext extensionServicesContext)
        {
            // Timer callback is automatically removed when triggering
            isTimerActive = false;
            quitable.GuardQuit();
        }
    }
}
