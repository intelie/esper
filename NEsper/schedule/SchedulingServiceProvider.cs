using System;

namespace net.esper.schedule
{
	/// <summary>
    /// Static factory for implementations of the SchedulingService interface.
    /// </summary>
	
    public sealed class SchedulingServiceProvider
	{
		/// <summary>
        /// Creates an implementation of the SchedulingService interface.
        /// </summary>
		public static SchedulingService NewService()
		{
			return new SchedulingServiceImpl();
		}
	}
}