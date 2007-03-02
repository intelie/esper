using System;

namespace net.esper.timer
{
	/// <summary>
    /// Callback interface for a time provider that triggers at scheduled intervals.
    /// </summary>

    public interface TimerCallback
    {
        /// <summary>
        /// Invoked by the internal clocking service at regular intervals.
        /// </summary>

        void TimerCallback();
    }
}