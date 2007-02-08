using System;
using System.Timers;

namespace net.esper.timer
{
    /// <summary>
    /// Timer task to simply invoke the callback when triggered.
    /// </summary>

    sealed class EQLTimerTask
    {
        private readonly TimerCallback callback;
        private bool isCancelled;

        public bool Cancelled
        {
            set { isCancelled = value; }
        }

        public EQLTimerTask(TimerCallback callback)
        {
            this.callback = callback;
        }

        public void Run( object sender, ElapsedEventArgs e)
        {
            if (!isCancelled)
            {
                callback.TimerCallback();
            }
        }
    }
}