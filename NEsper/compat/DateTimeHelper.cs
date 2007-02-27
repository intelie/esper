using System;

namespace net.esper.compat
{
    public class DateTimeHelper
    {
        /// <summary>
        /// Number of milliseconds per tick
        /// </summary>

        public const int MILLIS_PER_TICK = 10000;

        /// <summary>
        /// Converts ticks to milliseconds
        /// </summary>
        /// <param name="ticks"></param>
        /// <returns></returns>

        public static long TicksToMillis(long ticks)
        {
            return ticks / MILLIS_PER_TICK;
        }

        /// <summary>
        /// Converts milliseconds to ticks
        /// </summary>
        /// <param name="millis"></param>
        /// <returns></returns>

        public static long MillisToTicks(long millis)
        {
            return millis * MILLIS_PER_TICK;
        }

        /// <summary>
        /// Gets the number of milliseconds needed to represent
        /// the datetime.  This is needed to convert from Java
        /// datetime granularity (milliseconds) to CLR datetimes.
        /// </summary>
        /// <param name="dateTime"></param>
        /// <returns></returns>

        public static long TimeInMillis(DateTime dateTime)
        {
            return TicksToMillis(dateTime.Ticks);
        }

        /// <summary>
        /// Gets the datetime that matches the number of milliseconds provided.
        /// As with TimeInMillis, this is needed to convert from Java datetime
        /// granularity to CLR granularity.
        /// </summary>
        /// <param name="millis"></param>
        /// <returns></returns>

        public static DateTime TimeFromMillis(long millis)
        {
            return new DateTime(MillisToTicks(millis));
        }

        /// <summary>
        /// Returns the current time in millis
        /// </summary>

        public static long GetCurrentTimeMillis()
        {
            return TimeInMillis(DateTime.Now);
        }

        /// <summary>
        /// Returns the current time in millis
        /// </summary>

        public static long CurrentTimeMillis
        {
            get { return TimeInMillis(DateTime.Now); }
        }

        /// <summary>
        /// Converts millis in CLR to millis in Java
        /// </summary>
        /// <param name="millis"></param>
        /// <returns></returns>

        public static long MillisToJavaMillis(long millis)
        {
            return millis - 62135575200000L;
        }

        /// <summary>
        /// Converts milliseconds in Java to milliseconds in CLR
        /// </summary>
        /// <param name="javaMillis"></param>
        /// <returns></returns>

        public static long JavaMillisToMillis(long javaMillis)
        {
            return javaMillis + 62135575200000L;
        }
    }
}
