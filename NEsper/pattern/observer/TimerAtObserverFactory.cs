using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.parse;
using net.esper.pattern;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.pattern.observer
{
	/// <summary>
    /// Factory for 'crontab' observers that indicate truth when a time point was reached.
    /// </summary>
	
    public class TimerAtObserverFactory : ObserverFactory
	{
		private ScheduleSpec spec = null;
		
		/// <summary> Ctor.
		/// The crontab observer requires a schedule specification that is extracted from arguments.
		/// </summary>
		/// <param name="args">schedule specification
		/// </param>
		public TimerAtObserverFactory(Object[] args)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".TimerAtObserverFactory " + CollectionHelper.Render(args));
			}
			
			if ((args.Length < 5) || (args.Length > 6))
			{
				throw new ArgumentException("Invalid number of parameters for timer:at");
			}

            EDictionary<ScheduleUnit, ETreeSet<Int32>> unitMap = new EHashDictionary<ScheduleUnit, ETreeSet<Int32>>();
			unitMap[ScheduleUnit.MINUTES] = computeValues(args[0],ScheduleUnit.MINUTES);
			unitMap[ScheduleUnit.HOURS] = computeValues(args[1], ScheduleUnit.HOURS);
			unitMap[ScheduleUnit.DAYS_OF_WEEK] = computeValues(args[2], ScheduleUnit.DAYS_OF_WEEK);
			unitMap[ScheduleUnit.DAYS_OF_MONTH] = computeValues(args[3], ScheduleUnit.DAYS_OF_MONTH);
			unitMap[ScheduleUnit.MONTHS] = computeValues(args[4], ScheduleUnit.MONTHS);
			if (args.Length > 5)
			{
				unitMap[ScheduleUnit.SECONDS] = computeValues(args[5], ScheduleUnit.SECONDS);
			}
			spec = new ScheduleSpec(unitMap);
		}

        private ETreeSet<Int32> computeValues(Object unitParameter, ScheduleUnit unit)
        {
            if (unitParameter is Int32)
            {
                ETreeSet<Int32> result = new ETreeSet<Int32>();
                result.Add((Int32)unitParameter);
                return result;
            }

            NumberSetParameter numberSet = (NumberSetParameter)unitParameter;
            if (numberSet.IsWildcard(unit.Min(), unit.Min()))
            {
                return null;
            }

            ISet<Int32> _result = numberSet.GetValuesInRange(unit.Min(), unit.Max());
            ETreeSet<Int32> resultSorted = new ETreeSet<Int32>();
            resultSorted.AddAll(_result);

            return resultSorted;
        }

        /// <summary>
        /// Make an observer instance.
        /// </summary>
        /// <param name="context">services that may be required by observer implementation</param>
        /// <param name="beginState">Start state for observer</param>
        /// <param name="observerEventEvaluator">receiver for events observed</param>
        /// <returns>observer instance</returns>
		public virtual EventObserver MakeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
		{
			return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TimerAtObserverFactory));
	}
}
