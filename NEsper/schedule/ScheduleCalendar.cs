using System;

namespace net.esper.schedule
{
	/// <summary>
    /// Calendar class for use in scheduling, specifically for use in computing the next invocation time.
    /// </summary>
	
    public class ScheduleCalendar
	{
		virtual internal int Milliseconds
		{
			get { return milliseconds; }
			set { this.milliseconds = value; }
		}

		virtual internal int Second
		{
			get { return second; }
			set { this.second = value; }
		}

		virtual internal int Minute
		{
			get { return minute; }
			set { this.minute = value; }
		}

		virtual internal int Hour
		{
			get { return hour; }
			set { this.hour = value; }
		}

		virtual internal int DayOfMonth
		{
			get { return dayOfMonth; }
			set { this.dayOfMonth = value; }
		}

		virtual internal int Month
		{
			get { return month; }
			set { this.month = value; }
		}

		private int milliseconds;
		private int second;
		private int minute;
		private int hour;
		private int dayOfMonth;
		private int month;
		
		internal ScheduleCalendar(int milliseconds, int second, int minute, int hour, int dayOfMonth, int month)
		{
			this.milliseconds = milliseconds;
			this.second = second;
			this.minute = minute;
			this.hour = hour;
			this.dayOfMonth = dayOfMonth;
			this.month = month;
		}
		
		internal ScheduleCalendar()
		{
		}
	}
}