package net.esper.support.util;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Utility class for comparing double values up to a given precision
 */
public class DoubleValueAssertionUtil
{
    public static boolean equals(double valueActual, double valueExpected, int precision)
    {
        if (precision < 1)
        {
            throw new IllegalArgumentException("Invalid precision value of " + precision + " supplied");
        }

        if ((Double.valueOf(valueActual).isNaN()) && (Double.valueOf(valueExpected).isNaN()))
        {
            return true;
        }
        if ( ((Double.valueOf(valueActual).isNaN()) && (!Double.valueOf(valueExpected).isNaN())) ||
             ((!Double.valueOf(valueActual).isNaN()) && (Double.valueOf(valueExpected).isNaN())) )
        {
            log.debug(".equals Compare failed, " +
                    "  valueActual=" + valueActual +
                    "  valueExpected=" + valueExpected);
            return false;
        }

        double factor = Math.pow(10, precision);
        double val1 = valueActual * factor;
        double val2 = valueExpected * factor;

        // Round to closest integer
        double d1 = Math.rint(val1);
        double d2 = Math.rint(val2);

        if (d1 != d2)
        {
            log.debug(".equals Compare failed, " +
                    "  valueActual=" + valueActual +
                    "  valueExpected=" + valueExpected +
                    "  precision=" + precision
                    );
            return false;
        }

        return true;
    }

    private static final Log log = LogFactory.getLog(DoubleValueAssertionUtil.class);
}
