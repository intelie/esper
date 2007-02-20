using System;
using System.Threading;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.timer
{
    /// <summary>
    /// Implementation of the internal clocking service interface.
    /// </summary>

    public class TimerServiceImpl : TimerService
    {
        public TimerCallback Callback
        {
            set
            {
                this.timerCallback = value;
            }
        }

#if true
        private HighResolutionTimer timer ;
#else
        private Timer timer;
#endif

        private TimerCallback timerCallback;
        private bool timerTaskCancelled;

        /// <summary> Constructor.</summary>
        protected internal TimerServiceImpl()
        {
            timerTaskCancelled = false;
        }

        /// <summary>
        /// Handles the timer event
        /// </summary>
        /// <param name="state">The user state object.</param>

        private void OnTimerElapsed(Object state)
        {
            if (! timerTaskCancelled)
            {
                timerCallback.TimerCallback();
            }
        }

        public void StartInternalClock()
        {
            if (timer != null)
            {
                log.Warn(".StartInternalClock Internal clock is already Started, Stop first before Starting, operation not completed");
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".StartInternalClock Starting internal clock daemon thread, resolution=" + net.esper.timer.TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC);
            }

            if (timerCallback == null)
            {
                throw new SystemException("Timer callback not set");
            }

            timerTaskCancelled = false;
#if true
            timer = new HighResolutionTimer(OnTimerElapsed, null, 0, TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC);
#else
            timer = new Timer(OnTimerElapsed, null, 0, TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC);
#endif
        }

        public void StopInternalClock(bool warnIfNotStarted)
        {
            if (timer == null)
            {
                if (warnIfNotStarted)
                {
                    log.Warn(".StopInternalClock Internal clock is already Stopped, Start first before Stopping, operation not completed");
                }
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".StopInternalClock Stopping internal clock daemon thread");
            }

            timerTaskCancelled = true;
            timer.Dispose();

            try
            {
                // Sleep for at least 100 ms to await the internal timer
                Thread.Sleep(100);
            }
            catch (System.Threading.ThreadInterruptedException)
            {
            }

            timer = null;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TimerServiceImpl));
    }
}