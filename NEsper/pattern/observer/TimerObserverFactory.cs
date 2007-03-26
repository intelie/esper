using System;

using net.esper.eql.parse;
using net.esper.pattern;
using net.esper.schedule;

namespace net.esper.pattern.observer
{
	/// <summary>
    /// Factory for making observer instances.
    /// </summary>
	
    public class TimerObserverFactory : ObserverFactory
	{
		private ScheduleSpec scheduleSpec;
		private long msec;

        /// <summary>
        /// Initializes a new instance of the <see cref="TimerObserverFactory"/> class.
        /// </summary>
        /// <param name="args">The args.</param>
        public TimerObserverFactory(Object[] args)
        {
            Object value = args[0];

            if ( value is TimePeriodParameter)
            {
                this.msec = (long)Math.Round(((TimePeriodParameter)value).NumSeconds * 1000d);
            }
            else if (value is int)
            {
                this.msec = (long)Math.Round((int)value * 1000d);
            }
            else if (value is double)
            {
                this.msec = (long)Math.Round((double) value * 1000d);
            }
        }

		/// <summary> Ctor.</summary>
		/// <param name="scheduleSpec">schedule definition.
		/// </param>
		public TimerObserverFactory(ScheduleSpec scheduleSpec)
		{
			this.scheduleSpec = scheduleSpec;
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="seconds">time in seconds.
		/// </param>
		public TimerObserverFactory(int seconds)
		{
			this.msec = seconds * 1000;
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="seconds">time in seconds.
		/// </param>
		public TimerObserverFactory(double seconds)
		{
			this.msec = (long) Math.Round(seconds * 1000d);
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="timePeriodParameter">time in seconds.
		/// </param>
		public TimerObserverFactory(TimePeriodParameter timePeriodParameter)
		{
			this.msec = (long) Math.Round(timePeriodParameter.NumSeconds * 1000d);
		}

        /// <summary>
        /// Make an observer instance.
        /// </summary>
        /// <param name="context">services that may be required by observer implementation</param>
        /// <param name="beginState">Start state for observer</param>
        /// <param name="observerEventEvaluator">receiver for events observed</param>
        /// <returns>observer instance</returns>
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