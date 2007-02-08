using System;

namespace net.esper.timer
{
	/// <summary>
	/// Service interface for repeated callbacks at regular intervals.
	/// </summary>

	public interface TimerService
	{
		/// <summary> Set the callback method to invoke for clock ticks.</summary>
		/// <param name="timerCallback">is the callback
		/// </param>
		TimerCallback Callback
		{
			set;
		}
		
		/// <summary> Start clock expecting callbacks at regular intervals and a fixed rate.
		/// Catch-up callbacks are possible should the callback fall behind.
		/// </summary>
		void  StartInternalClock();
		
		/// <summary> Stop internal clock.</summary>
		/// <param name="warnIfNotStarted">use true to indicate whether to warn if the clock is not Started, use false to not warn
		/// and expect the clock to be not Started. 
		/// </param>
		void  StopInternalClock(bool warnIfNotStarted);
	}

	public struct TimerService_Fields
	{
		/// <summary>
		/// Resolution in milliseconds of the internal clock.
		/// </summary>
		
		public const int INTERNAL_CLOCK_RESOLUTION_MSEC = 100;
	}
}
