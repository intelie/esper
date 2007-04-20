using System;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Implementation of the timer factory that uses the system timer.
    /// </summary>

    public class SystemTimerFactory : ITimerFactory
    {
        /// <summary>
        /// Creates a timer.  The timer will begin after dueTime (in milliseconds)
        /// has passed and will occur at an interval specified by the period.
        /// </summary>
        /// <param name="timerCallback"></param>
        /// <param name="state"></param>
        /// <param name="dueTime"></param>
        /// <param name="period"></param>
        /// <returns></returns>

        public ITimer CreateTimer(
            TimerCallback timerCallback,
            Object state,
            int dueTime,
            int period)
        {
            return new ThreadBasedTimer(
                timerCallback,
                state,
                dueTime,
                period ) ;
        }

        private class ThreadBasedTimer : ITimer
        {
            private const int INTERNAL_CLOCK_SLIP_MSEC = 10;

            private Guid m_id;
            private Thread m_thread;
            private TimerCallback m_timerCallback;
            private Object m_timerCallbackState;
            private int m_tickAlign;
            private int m_tickPeriod;
            private bool m_alive;

            /// <summary>
            /// Starts thread processing.
            /// </summary>

            private void Start()
            {
                try
                {
                    int lastTickCount = Environment.TickCount;

                    while (m_alive)
                    {
                        // Check the tickAlign to determine if we are here "too early"
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
                        int currDelta = m_tickAlign - currTickCount;

                        while (currDelta > INTERNAL_CLOCK_SLIP_MSEC)
                        {
                            Thread.Sleep(currDelta);
                            currTickCount = Environment.TickCount;
                            currDelta = m_tickAlign - currTickCount;
                        }

                        m_tickAlign += m_tickPeriod;
                        m_timerCallback(m_timerCallbackState);

                        lastTickCount = currTickCount;
                    }
                }
                catch (ThreadInterruptedException)
                {
                }
            }

            /// <summary>
            /// Creates the timer and wraps it
            /// </summary>
            /// <param name="timerCallback"></param>
            /// <param name="state"></param>
            /// <param name="dueTime"></param>
            /// <param name="period"></param>

            public ThreadBasedTimer(
                TimerCallback timerCallback,
                Object state,
                int dueTime,
                int period)
            {
                m_id = Guid.NewGuid();

                m_alive = true;

                m_timerCallback = timerCallback;
                m_timerCallbackState = state;

                m_tickPeriod = period;
                m_tickAlign = Environment.TickCount + dueTime;

                m_thread = new Thread(Start);
                m_thread.IsBackground = true;
                m_thread.Start();

                //m_timer = new Timer(
                //    timerCallback,
                //    state,
                //    dueTime,
                //    period);
            }

            /// <summary>
            /// Called when the object is destroyed.
            /// </summary>

            ~ThreadBasedTimer()
            {
                Dispose();
            }

            /// <summary>
            /// Cleans up system resources
            /// </summary>

            public void Dispose()
            {
                this.m_alive = false;

                if (this.m_thread != null)
                {
                    this.m_thread.Interrupt();
                    this.m_thread = null;
                }
            }
        }
    }
}
