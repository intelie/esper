using System;

using org.apache.commons.logging;

namespace net.esper.support.util
{
	/// <summary>
    /// Utility class for comparing double values up to a given precision
    /// </summary>
	
    public class DoubleValueAssertionUtil
	{
		public static bool Equals(double valueActual, double valueExpected, int precision)
		{
			if (precision < 1)
			{
				throw new ArgumentException("Invalid precision value of " + precision + " supplied");
			}
			
			if (Double.IsNaN(valueActual) && Double.IsNaN(valueExpected))
			{
				return true;
			}
			if ((Double.IsNaN(valueActual) && !Double.IsNaN(valueExpected)) ||
			    (!Double.IsNaN(valueActual) && Double.IsNaN(valueExpected)))
			{
				log.Debug(
					".Equals Compare failed, " +
					"  valueActual=" + valueActual +
					"  valueExpected=" + valueExpected);
				return false;
			}
			
			double factor = Math.Pow(10, precision);
			double val1 = valueActual * factor;
			double val2 = valueExpected * factor;
			
			// Round to closest integer
			double d1 = Math.Round(val1);
			double d2 = Math.Round(val2);
			
			if (d1 != d2)
			{
				log.Debug(
					".Equals Compare failed, " +
					"  valueActual=" + valueActual +
					"  valueExpected=" + valueExpected +
					"  precision=" + precision);
				return false;
			}
			
			return true;
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
