///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.eql.parse;
using net.esper.pattern;
using net.esper.pattern.guard;
using net.esper.schedule;
using net.esper.util;

namespace net.esper.pattern.observer
{
	/// <summary>Factory for making observer instances.</summary>
	public class TimerIntervalObserverFactory
		: ObserverFactory
		, MetaDefItem
	{
	    /// <summary>Number of milliseconds after which the interval should fire.</summary>
	    protected long milliseconds;

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
                String errorMessage = "Timer-interval observer requires a single numeric or time period parameter";
                if (value.Count != 1)
                {
                    throw new ObserverParameterException(errorMessage);
                }

                Object parameter = value[0];
                if (parameter is TimePeriodParameter)
                {
                    TimePeriodParameter param = (TimePeriodParameter) parameter;
                    milliseconds = (long) Math.Round(1000d*param.NumSeconds);
                }
                else if (TypeHelper.IsFloatingPointNumber(parameter))
                {
                    milliseconds = (long) Math.Round(1000d*Convert.ToDouble(parameter));
                }
                else if (TypeHelper.IsIntegralNumber(parameter))
                {
                    milliseconds = 1000*Convert.ToInt64(parameter);
                }
                else
                {
                    throw new ObserverParameterException(errorMessage);
                }
            }
	    }

        /// <summary>
        /// </summary>
        /// <param name="context"></param>
        /// <param name="beginState"></param>
        /// <param name="observerEventEvaluator"></param>
        /// <param name="stateNodeId"></param>
        /// <param name="observerState"></param>
        /// <returns></returns>
        /// Make an observer instance.
        /// @param context - services that may be required by observer implementation
        /// @param beginState - start state for observer
        /// @param observerEventEvaluator - receiver for events observed
        /// @param stateNodeId - optional id for the associated pattern state node
        /// @param observerState - state node for observer
        /// @return observer instance
	    public EventObserver MakeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
	    {
	        return new TimerIntervalObserver(milliseconds, context, beginState, observerEventEvaluator);
	    }
	}
} // End of namespace
