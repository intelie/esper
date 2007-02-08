using System;
using System.Timers;

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

        private Timer timer;
        private TimerCallback timerCallback;
        private EQLTimerTask timerTask;

        /// <summary> Constructor.</summary>
        protected internal TimerServiceImpl()
        {
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

            timer = new Timer();
            timerTask = new EQLTimerTask(timerCallback);
            // With no delay Start every INTERNAL_CLOCK_RESOLUTION_MSEC
            timer.Interval = TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC;
            timer.Elapsed += timerTask.Run;
            timer.Start();
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

            timerTask.Cancelled = true;
            timer.Stop();

            try
            {
                // Sleep for 100 ms to await the internal timer
                System.Threading.Thread.Sleep(new System.TimeSpan((long)10000 * 100));
            }
            catch (System.Threading.ThreadInterruptedException)
            {
            }

            timer = null;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TimerServiceImpl));
    }
}