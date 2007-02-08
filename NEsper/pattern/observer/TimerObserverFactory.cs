using System;
using PatternContext = net.esper.pattern.PatternContext;
using MatchedEventMap = net.esper.pattern.MatchedEventMap;
using ScheduleSpec = net.esper.schedule.ScheduleSpec;
using TimePeriodParameter = net.esper.eql.parse.TimePeriodParameter;
namespace net.esper.pattern.observer
{
	
	/// <summary> Factory for making observer instances.</summary>
	public class TimerObserverFactory : ObserverFactory
	{
		private ScheduleSpec scheduleSpec;
		private long msec;
		
		/// <summary> Ctor.</summary>
		/// <param name="scheduleSpec">- schedule definition.
		/// </param>
		public TimerObserverFactory(ScheduleSpec scheduleSpec)
		{
			this.scheduleSpec = scheduleSpec;
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="seconds">- time in seconds.
		/// </param>
		public TimerObserverFactory(int seconds)
		{
			this.msec = seconds * 1000;
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="seconds">- time in seconds.
		/// </param>
		public TimerObserverFactory(double seconds)
		{
			//UPGRADE_TODO: Method 'java.lang.Math.round' was converted to 'System.Math.Round' which has a different behavior. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1073_javalangMathround_double'"
			this.msec = (long) System.Math.Round(seconds * 1000d);
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="timePeriodParameter">- time in seconds.
		/// </param>
		public TimerObserverFactory(TimePeriodParameter timePeriodParameter)
		{
			//UPGRADE_TODO: Method 'java.lang.Math.round' was converted to 'System.Math.Round' which has a different behavior. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1073_javalangMathround_double'"
			this.msec = (long) System.Math.Round(timePeriodParameter.NumSeconds * 1000d);
		}
		
		public virtual EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
		{
			if (scheduleSpec != null)
			{
				return new TimerAtObserver(scheduleSpec, context, beginState, observerEventEvaluator);
			}
			else
			{
				return new TimerIntervalObserver(msec, context, beginState, observerEventEvaluator);
			}
		}
	}
}