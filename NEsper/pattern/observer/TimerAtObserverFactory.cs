using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.parse;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.pattern.observer
{
	/// <summary>
    /// Factory for 'crontab' observers that indicate truth when a time point was reached.
    /// </summary>
	
    public class TimerAtObserverFactory 
		: ObserverFactory
		, MetaDefItem
	{
	    /// <summary>
	    /// The specification of the crontab schedule.
	    /// </summary>
	    protected ScheduleSpec spec = null;

        /// <summary>
        /// </summary>
        /// <value></value>
        /// Sets the observer object parameters.
        /// @param observerParameters is a list of parameters
        /// @throws ObserverParameterException thrown to indicate a parameter problem
	    public IList<Object> ObserverParameters
	    {
			set
			{
		    	if (log.IsDebugEnabled)
		        {
		            log.Debug(".setObserverParameters " + value);
		        }
	
		        if ((value.Count < 5) || (value.Count > 6))
		        {
		            throw new ObserverParameterException("Invalid number of parameters for timer:at");
		        }
	
		        EDictionary<ScheduleUnit, ETreeSet<Int32>> unitMap = new EHashDictionary<ScheduleUnit, ETreeSet<Int32>>();
		        unitMap[ScheduleUnit.MINUTES] = ComputeValues(value[0], ScheduleUnit.MINUTES);
		        unitMap[ScheduleUnit.HOURS] = ComputeValues(value[1], ScheduleUnit.HOURS);
		        unitMap[ScheduleUnit.DAYS_OF_WEEK] = ComputeValues(value[2], ScheduleUnit.DAYS_OF_WEEK);
		        unitMap[ScheduleUnit.DAYS_OF_MONTH] = ComputeValues(value[3], ScheduleUnit.DAYS_OF_MONTH);
		        unitMap[ScheduleUnit.MONTHS] = ComputeValues(value[4], ScheduleUnit.MONTHS);
		        if (value.Count > 5)
		        {
		            unitMap[ScheduleUnit.SECONDS] = ComputeValues(value[5], ScheduleUnit.SECONDS);
		        }
				spec = new ScheduleSpec(unitMap);
			}
		}

        /// <summary>
        /// Computes the values.
        /// </summary>
        /// <param name="unitParameter">The unit parameter.</param>
        /// <param name="unit">The unit.</param>
        /// <returns></returns>
        private static ETreeSet<Int32> ComputeValues(Object unitParameter, ScheduleUnit unit)
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

            Set<Int32> _result = numberSet.GetValuesInRange(unit.Min(), unit.Max());
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
        /// <param name="stateNodeId">optional id for the associated pattern state node</param>
        /// <param name="observerState">state node for observer</param>
        /// <returns>observer instance</returns>
		public virtual EventObserver MakeObserver(PatternContext context,
												  MatchedEventMap beginState,
												  ObserverEventEvaluator observerEventEvaluator,
												  Object stateNodeId,
												  Object observerState)
		{
			return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
