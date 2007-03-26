using System;

using net.esper.pattern;
using net.esper.eql.parse;

namespace net.esper.pattern.guard
{
	/// <summary>
	/// Factory for {@link TimerWithinGuard} instances.
	/// </summary>
	
	public class TimerWithinGuardFactory : GuardFactory
	{
		private readonly long milliseconds;
		
		/// <summary> Creates a timer guard.</summary>
		/// <param name="seconds">number of seconds before guard expiration
		/// </param>
		public TimerWithinGuardFactory(int seconds):this((long) seconds)
		{
		}
		
		/// <summary> Creates a timer guard.</summary>
		/// <param name="seconds">number of seconds before guard expiration
		/// </param>
		public TimerWithinGuardFactory(double seconds)
		{
			this.milliseconds = (long) System.Math.Round(seconds * 1000d);
		}
		
		/// <summary> Creates a timer guard.</summary>
		/// <param name="seconds">number of seconds before guard expiration
		/// </param>
		public TimerWithinGuardFactory(long seconds)
		{
			this.milliseconds = seconds * 1000;
		}
		
		/// <summary> Creates a timer guard.</summary>
		/// <param name="timePeriodParameter">number of milliseconds before guard expiration
		/// </param>
		public TimerWithinGuardFactory(TimePeriodParameter timePeriodParameter)
		{
			double milliseconds = timePeriodParameter.NumSeconds * 1000d;
			this.milliseconds = (long) System.Math.Round(milliseconds);
		}

        /// <summary>
        /// Constructs a guard instance.
        /// </summary>
        /// <param name="context">services for use by guard</param>
        /// <param name="quitable">to use for indicating the guard has quit</param>
        /// <returns>guard instance</returns>
		public virtual Guard MakeGuard(PatternContext context, Quitable quitable)
		{
			return new TimerWithinGuard(milliseconds, context, quitable);
		}
	}
}
