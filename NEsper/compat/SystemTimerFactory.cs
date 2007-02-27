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
            return new SystemTimer(
                timerCallback,
                state,
                dueTime,
                period ) ;
        }

        private class SystemTimer : ITimer
        {
            private Timer m_timer;

            /// <summary>
            /// Creates the timer and wraps it
            /// </summary>
            /// <param name="timerCallback"></param>
            /// <param name="state"></param>
            /// <param name="dueTime"></param>
            /// <param name="period"></param>

            public SystemTimer(
                TimerCallback timerCallback,
                Object state,
                int dueTime,
                int period)
            {
                m_timer = new Timer(
                    timerCallback,
                    state,
                    dueTime,
                    period);
            }

            /// <summary>
            /// Cleans up system resources
            /// </summary>

            public void Dispose()
            {
                if (m_timer != null)
                {
                    m_timer.Dispose();
                    m_timer = null;
                }
            }
        }
    }
}
