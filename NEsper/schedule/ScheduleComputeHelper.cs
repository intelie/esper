using System;
using System.Collections.Generic;
using System.Globalization;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.schedule
{
	/// <summary>
	/// For a crontab-like schedule, this class computes the next occurance given a Start time and a specification of
	/// what the schedule looks like.
	/// The resolution at which this works is at the second level. The next occurance
	/// is always at least 1 second ahead.
	/// The class implements an algorithm that Starts at the highest precision (seconds) and
	/// continues to the lowest precicion (month). For each precision level the
	/// algorithm looks at the list of valid values and finds a value for each that is equal to or greater then
	/// the valid values supplied. If no equal or
	/// greater value was supplied, it will reset all higher precision elements to its minimum value.
	/// </summary>
	
    public sealed class ScheduleComputeHelper
	{
        /// <summary>
        /// Minimum time to next occurance.
        /// </summary>
        
        private const int MIN_OFFSET_MSEC = 1000;
        
		/// <summary> Computes the next lowest date in milliseconds based on a specification and the
		/// from-time passed in.
		/// </summary>
		/// <param name="spec">defines the schedule
		/// </param>
		/// <param name="afterTimeInMillis">defines the start time
		/// </param>
		/// <returns> a long date tick value for the next schedule occurance matching the spec
		/// </returns>
		
        public static long ComputeNextOccurance(ScheduleSpec spec, long afterTimeInMillis)
		{
			if (log.IsDebugEnabled)
			{
				log.Debug(".computeNextOccurance Computing next occurance," +
				          "  afterTimeInTicks=" + DateTimeHelper.TimeFromMillis(afterTimeInMillis) +
				          "  as long=" + afterTimeInMillis +
				          "  spec=" + spec);
			}
			
			// Add the minimum resolution to the Start time to ensure we don't get the same exact time
			if (spec.UnitValues.ContainsKey(ScheduleUnit.SECONDS))
			{
				afterTimeInMillis += MIN_OFFSET_MSEC;
			}
			else
			{
				afterTimeInMillis += 60 * MIN_OFFSET_MSEC;
			}
			
			DateTime result = Compute(spec, afterTimeInMillis);
			
			if (log.IsDebugEnabled)
			{
				log.Debug(
					".computeNextOccurance Completed," +
					"  result=" + result.ToString("r") +
					"  long=" + DateTimeHelper.TimeInMillis(result) ) ;
			}

            return DateTimeHelper.TimeInMillis(result);
		}
		
		private static DateTime Compute(ScheduleSpec spec, long afterTimeInMillis)
		{
			DateTime after = DateTimeHelper.TimeFromMillis( afterTimeInMillis ) ;
			
			ScheduleCalendar result = new ScheduleCalendar();
			result.Milliseconds = after.Millisecond;

            ETreeSet<Int32> minutesSet = spec.UnitValues.Fetch(ScheduleUnit.MINUTES);
            ETreeSet<Int32> hoursSet = spec.UnitValues.Fetch(ScheduleUnit.HOURS);
            ETreeSet<Int32> monthsSet = spec.UnitValues.Fetch(ScheduleUnit.MONTHS);
            ETreeSet<Int32> secondsSet = null;
            
			bool isSecondsSpecified = false;
			
			if (spec.UnitValues.ContainsKey(ScheduleUnit.SECONDS))
			{
				isSecondsSpecified = true;
				secondsSet = spec.UnitValues.Fetch(ScheduleUnit.SECONDS) ;
			}
			
			if (isSecondsSpecified)
			{
                result.Second = NextValue(secondsSet, after.Second);
				if (result.Second == -1)
				{
                    result.Second = NextValue(secondsSet, 0);
					after = after.AddMinutes(1) ;
				}
			}

            result.Minute = NextValue(minutesSet, after.Minute);
			if (result.Minute != after.Minute)
			{
                result.Second = NextValue(secondsSet, 0);
			}
			if (result.Minute == - 1)
			{
                result.Minute = NextValue(minutesSet, 0);
				after = after.AddHours( 1 ) ;
			}

            result.Hour = NextValue(hoursSet, after.Hour);
			if (result.Hour != after.Hour)
			{
                result.Second = NextValue(secondsSet, 0);
                result.Minute = NextValue(minutesSet, 0);
			}
			if (result.Hour == -1)
			{
                result.Hour = NextValue(hoursSet, 0);
				after = after.AddDays(1) ;
			}
			
			// This call may change second, minute and/or hour parameters
			// They may be reset to minimum values if the day rolled
			result.DayOfMonth = DetermineDayOfMonth(spec, ref after, result);
			
			bool dayMatchRealDate = false;
			while (!dayMatchRealDate)
			{
				if (CheckDayValidInMonth( result.DayOfMonth, after.Month, after.Year ))
				{
					dayMatchRealDate = true;
				}
				else
				{
					after = after.AddMonths(1) ;
				}
			}

            int currentMonth = after.Month ;
            result.Month = NextValue(monthsSet, currentMonth);
			if (result.Month != currentMonth)
			{
                result.Second = NextValue(secondsSet, 0);
                result.Minute = NextValue(minutesSet, 0);
				result.Hour = NextValue(hoursSet, 0);
				result.DayOfMonth = DetermineDayOfMonth(spec, ref after, result);
			}
			if (result.Month == -1)
			{
                result.Month = NextValue(monthsSet, 0);
				after = after.AddYears(1) ;
			}
			
			// Perform a last valid date check, if failing, try to compute a new date based on this altered after date
			int year = after.Year;
            if (!CheckDayValidInMonth(result.DayOfMonth, result.Month, year))
			{
				return Compute( spec, DateTimeHelper.TimeInMillis( after ) );
			}
			
			DateTime resultDate = GetTime(result, after.Year);
			return resultDate;
		}

		/// <summary>
		/// Determine the next valid day of month based on the given specification of valid days in month and
		/// valid days in week. If both days in week and days in month are supplied, the days are OR-ed.
		/// </summary>
		/// <param name="spec"></param>
		/// <param name="after"></param>
		/// <param name="result"></param>
		/// <returns></returns>
		
		private static int DetermineDayOfMonth(ScheduleSpec spec, ref DateTime after, ScheduleCalendar result)
		{
            ETreeSet<Int32> daysOfMonthSet = spec.UnitValues.Fetch(ScheduleUnit.DAYS_OF_MONTH);
            ETreeSet<Int32> daysOfWeekSet = spec.UnitValues.Fetch(ScheduleUnit.DAYS_OF_WEEK);
            ETreeSet<Int32> secondsSet = spec.UnitValues.Fetch(ScheduleUnit.SECONDS);
            ETreeSet<Int32> minutesSet = spec.UnitValues.Fetch(ScheduleUnit.MINUTES);
            ETreeSet<Int32> hoursSet = spec.UnitValues.Fetch(ScheduleUnit.HOURS);
			
			int dayOfMonth;
			
			// If days of week is a wildcard, just go by days of month
			if (daysOfWeekSet == null)
			{
                dayOfMonth = NextValue(daysOfMonthSet, after.Day);
				if (dayOfMonth != after.Day)
				{
                    result.Second = (NextValue(secondsSet, 0));
                    result.Minute = (NextValue(minutesSet, 0));
                    result.Hour = (NextValue(hoursSet, 0));
				}
				if (dayOfMonth == - 1)
				{
                    dayOfMonth = NextValue(daysOfMonthSet, 0);
					after = after.AddMonths(1) ;
				}
			}
			// If days of weeks is not a wildcard and days of month is a wildcard, go by days of week only
			else if (daysOfMonthSet == null)
			{
				// Loop to find the next day of month that works for the specified day of week values
				while (true)
				{
					dayOfMonth = after.Day;
					int dayOfWeek = (int) after.DayOfWeek;
					
					// TODO
					//
					// Check the DayOfWeek logic in this section.  The former code reads something
					// like the following:
					//
					// Calendar.Get(after, SupportClass.CalendarManager.DAY_OF_WEEK) - 1;
					//
					// Java calendars are one based which means that subtracting one makes them
					// zero-based.  CLR DateTimes are zero-based so there should be no need to
					// tweak the dates to make this work.
					
					// If the day matches neither the day of month nor the day of week
					if (!daysOfWeekSet.Contains(dayOfWeek))
					{
                        result.Second = NextValue(secondsSet, 0);
                        result.Minute = NextValue(minutesSet, 0);
                        result.Hour = NextValue(hoursSet, 0);
						after = after.AddDays(1) ;
					}
					else
					{
						break;
					}
				}
			}
			// Both days of weeks and days of month are not a wildcard
			else
			{
				// Loop to find the next day of month that works for either day of month  OR   day of week
				while (true)
				{
					dayOfMonth = after.Day;
					int dayOfWeek = (int) after.DayOfWeek;
					
					// TODO
					//
					// See my discussion above about day of week conversion
					
					// If the day matches neither the day of month nor the day of week
					if ((!daysOfWeekSet.Contains(dayOfWeek)) && (!daysOfMonthSet.Contains(dayOfMonth)))
					{
                        result.Second = NextValue(secondsSet, 0);
                        result.Minute = NextValue(minutesSet, 0);
                        result.Hour = NextValue(hoursSet, 0);
						after = after.AddDays(1) ;
					}
					else
					{
						break;
					}
				}
			}
			
			return dayOfMonth;
		}
		
		private static DateTime GetTime(ScheduleCalendar result, int year)
		{
			// TODO
			//
			// Here again we have a case of 1-based vs. 0-based indexing.
			// 		Java months are 0-based.
			// 		CLR  months are 1-based.
			
			DateTime dateTime = new DateTime(
				year,
				result.Month,
				result.DayOfMonth,
				result.Hour,
				result.Minute,
				result.Second,
				result.Milliseconds,
				new GregorianCalendar() ) ;

			return dateTime ;
		}
		
		/// <summary>
		/// Check if this is a valid date.
		/// </summary>
		/// <param name="day"></param>
		/// <param name="month"></param>
		/// <param name="year"></param>
		/// <returns></returns>
		
		private static bool CheckDayValidInMonth(int day, int month, int year)
		{
			try
			{
				DateTime dateTime = new DateTime( year, month, day ) ;
				return true ;
			}
			catch (ArgumentException)
			{
				return false;
			}
		}
		
		/// <summary>
		/// Determine if in the supplied valueSet there is a value after the given Start value.
		/// Return -1 to indicate that there is no value after the given StartValue.
		/// If the valueSet passed is null it is treated as a wildcard and the same StartValue is returned
		/// </summary>
		/// <param name="valueSet"></param>
		/// <param name="startValue"></param>
		/// <returns></returns>

		private static int NextValue(ETreeSet<Int32> valueSet, int startValue)
        {
            if (valueSet == null)
            {
                return startValue;
            }

            if (valueSet.Contains(startValue))
            {
                return startValue;
            }

            ETreeSet<Int32> tailSet = valueSet.TailSet(startValue + 1);

            if (tailSet.Count == 0)
            {
                return -1;
            }
            else
            {
                return tailSet.First;
            }
        }
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
