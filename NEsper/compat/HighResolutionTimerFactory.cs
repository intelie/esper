using System;
using System.Collections.Generic;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Implementation of the TimerFactory that uses the HighResolutionTimer.
    /// </summary>

    public class HighResolutionTimerFactory : ITimerFactory
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
            return new HighResolutionTimer(
                timerCallback,
                state,
                dueTime,
                period);
        }
    }
}
