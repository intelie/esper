using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.eql.parse;
using net.esper.util;

namespace net.esper.pattern.guard
{
	/// <summary>
	/// Factory for <seealso cref="TimerWithinGuard"/> instances.
	/// </summary>
	
	public class TimerWithinGuardFactory
		: GuardFactory
		, MetaDefItem
	{
		private long milliseconds;
		
		public IList<Object> GuardParameters
		{
            set
            {
                IList<Object> guardParameters = value;

                String errorMessage = "Timer-within guard requires a single numeric or time period parameter";
                if (guardParameters.Count != 1)
                {
                    throw new GuardParameterException(errorMessage);
                }

                Object parameter = guardParameters[0];
                if (parameter is TimePeriodParameter)
                {
                    TimePeriodParameter param = (TimePeriodParameter) parameter;
                    milliseconds = (long) Math.Round(1000d*param.NumSeconds);
                }
                if (TypeHelper.IsFloatingPointNumber(parameter))
                {
                    milliseconds = (long) Math.Round(1000d*Convert.ToDouble(parameter));
                }
                else if (TypeHelper.IsIntegralNumber(parameter))
                {
                    milliseconds = 1000*Convert.ToInt64(parameter);
                }
                else
                {
                    throw new GuardParameterException(errorMessage);
                }
            }
		}
		
        /// <summary>
        /// Constructs a guard instance.
        /// </summary>
        /// <param name="context">services for use by guard</param>
        /// <param name="quitable">to use for indicating the guard has quit</param>
        /// <returns>guard instance</returns>
		public virtual Guard MakeGuard(PatternContext context,
									   Quitable quitable,
									   Object stateNodeId,
									   Object guardState)
		{
			return new TimerWithinGuard(milliseconds, context, quitable);
		}
	}
}
