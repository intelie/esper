using System;
using System.Timers;

namespace net.esper.timer
{
    /// <summary>
    /// Timer task to simply invoke the callback when triggered.
    /// </summary>

    sealed class EQLTimerTask
    {
        private readonly TimerCallback timerCallback;
        private bool isCancelled;

        public bool Cancelled
        {
            set { isCancelled = value; }
        }

        public EQLTimerTask(TimerCallback callback)
        {
            this.timerCallback = callback;
        }

        public void Run( object sender, ElapsedEventArgs e)
        {
            if (!isCancelled)
            {
                timerCallback();
            }
        }
    }
}