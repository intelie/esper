using System;

using net.esper.pattern;
using net.esper.schedule;

namespace net.esper.pattern.observer
{
    /// <summary>
    /// Observer that will wait a certain interval before indicating
    /// true (raising an event).
    /// </summary>

    public class TimerIntervalObserver : EventObserver, ScheduleCallback
    {
        private readonly long msec;
        private readonly PatternContext context;
        private readonly MatchedEventMap beginState;
        private readonly ObserverEventEvaluator observerEventEvaluator;
        private readonly ScheduleSlot scheduleSlot;

        private bool isTimerActive = false;

        /// <summary> Ctor.</summary>
        /// <param name="msec">number of milliseconds
        /// </param>
        /// <param name="context">timer service
        /// </param>
        /// <param name="beginState">Start state
        /// </param>
        /// <param name="observerEventEvaluator">receiver for events
        /// </param>
        public TimerIntervalObserver(long msec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
        {
            this.msec = msec;
            this.context = context;
            this.beginState = beginState;
            this.observerEventEvaluator = observerEventEvaluator;
            this.scheduleSlot = context.ScheduleBucket.AllocateSlot();
        }

        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>
        public void ScheduledTrigger()
        {
            observerEventEvaluator.ObserverEvaluateTrue(beginState);
            isTimerActive = false;
        }

        /// <summary>
        /// Start observing.
        /// </summary>
        public virtual void StartObserve()
        {
            if (isTimerActive == true)
            {
                throw new SystemException("Timer already active");
            }

            if (msec <= 0)
            {
                observerEventEvaluator.ObserverEvaluateTrue(beginState);
            }
            else
            {
                context.SchedulingService.Add(msec, this, scheduleSlot);
                isTimerActive = true;
            }
        }

        /// <summary>
        /// Stop observing.
        /// </summary>
        public virtual void StopObserve()
        {
            if (isTimerActive)
            {
                context.SchedulingService.Remove(this, scheduleSlot);
                isTimerActive = false;
            }
        }
    }
}