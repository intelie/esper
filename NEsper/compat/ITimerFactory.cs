using System;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Factory object that creates timers.
    /// </summary>

    public interface ITimerFactory
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

        ITimer CreateTimer(
            TimerCallback timerCallback,
            Object state,
            int dueTime,
            int period);
    }
}
