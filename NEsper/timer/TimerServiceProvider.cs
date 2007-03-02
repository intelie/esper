using System;

namespace net.esper.timer
{
	/// <summary>
    /// Static factory for implementations of the TimerService interface.
    /// </summary>

    public sealed class TimerServiceProvider
	{
		/// <summary> Creates an implementation of the TimerService interface.</summary>
		/// <returns> implementation
		/// </returns>
		public static TimerService newService()
		{
			return new TimerServiceImpl();
		}
	}
}