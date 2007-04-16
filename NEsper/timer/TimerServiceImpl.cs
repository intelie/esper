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
        /// <summary>
        /// Set the callback method to invoke for clock ticks.
        /// </summary>
        /// <value></value>
        public TimerCallback Callback
        {
            set
            {
                this.timerCallback = value;
            }
        }

        private const int INTERNAL_CLOCK_SLIP_MSEC = 10;

        private ITimer timer ;
        private TimerCallback timerCallback;
        private bool timerTaskCancelled;
        private int timerAlignCount;

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
            // Check the timerAlignCount to determine if we are here "too early"
            // The CLR is a little sloppy in the way that thread timers are handled.
            // In Java, when a timer is setup, the timer will adjust the interval
            // up and down to match the interval set by the requestor.  As a result,
            // you will can easily see intervals between calls that look like 109ms,
            // 94ms, 109ms, 94ms.  This is how the JVM ensures that the caller gets
            // an average of 100ms.  The CLR however will provide you with 109ms,
            // 109ms, 109ms, 109ms.  Eventually this leads to slip in the timer.
            // To account for that we under allocate the timer interval by some
            // small amount and allow the thread to sleep a wee-bit if the timer
            // is too early to the next clock cycle.

            int currTickCount = Environment.TickCount;
            int currDelta = timerAlignCount - currTickCount;

            while (currDelta > INTERNAL_CLOCK_SLIP_MSEC)
            {
                Thread.Sleep(currDelta);
                currTickCount = Environment.TickCount;
                currDelta = timerAlignCount - currTickCount;
            }

            Interlocked.Add(ref timerAlignCount, TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC);

            if (! timerTaskCancelled)
            {
                timerCallback();
            }
        }

        /// <summary>
        /// Start clock expecting callbacks at regular intervals and a fixed rate.
        /// Catch-up callbacks are possible should the callback fall behind.
        /// </summary>
        public void StartInternalClock()
        {
            if (timer != null)
            {
                log.Warn(".StartInternalClock Internal clock is already started, stop first before starting, operation not completed");
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
            timerAlignCount = Environment.TickCount;
            timer = TimerFactory.DefaultTimerFactory.CreateTimer(
                OnTimerElapsed, null, 0, TimerService_Fields.INTERNAL_CLOCK_RESOLUTION_MSEC - INTERNAL_CLOCK_SLIP_MSEC);
        }

        /// <summary>
        /// Stop internal clock.
        /// </summary>
        /// <param name="warnIfNotStarted">use true to indicate whether to warn if the clock is not Started, use false to not warn
        /// and expect the clock to be not Started.</param>
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

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}