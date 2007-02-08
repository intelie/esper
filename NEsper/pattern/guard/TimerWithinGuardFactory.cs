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
			//UPGRADE_TODO: Method 'java.lang.Math.round' was converted to 'System.Math.Round' which has a different behavior. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1073_javalangMathround_double'"
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
			//UPGRADE_TODO: Method 'java.lang.Math.round' was converted to 'System.Math.Round' which has a different behavior. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1073_javalangMathround_double'"
			this.milliseconds = (long) System.Math.Round(milliseconds);
		}
		
		public virtual Guard makeGuard(PatternContext context, Quitable quitable)
		{
			return new TimerWithinGuard(milliseconds, context, quitable);
		}
	}
}
