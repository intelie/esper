package net.esper.view.stat;

import net.esper.view.ViewProcessingException;
import net.esper.client.EPException;

/**
 * Bean for performing statistical calculations. The bean keeps sums of X and Y datapoints and sums on squares
 * that can be reused by subclasses. The bean calculates standard deviation (sample and population), variance,
 * average and sum.
  */
public class BaseStatisticsBean implements Cloneable
{
    private double sumX;
    private double sumXSq;
    private double sumY;
    private double sumYSq;
    private double sumXY;
    private long dataPoints;

    private void initialize()
    {
        sumX = 0;
        sumXSq = 0;
        sumY = 0;
        sumYSq = 0;
        sumXY = 0;
        dataPoints = 0;
    }

    /**
     * Add a data point for the X data set only.
     * @param x is the X data point to add.
     */
    public final void addPoint(double x)
    {
        dataPoints++;
        sumX += x;
        sumXSq += x * x;
    }

    /**
     * Add a data point.
     * @param x is the X data point to add.
     * @param y is the Y data point to add.
     */
    public final void addPoint(double x, double y)
    {
        dataPoints++;
        sumX += x;
        sumXSq += x * x;
        sumY += y;
        sumYSq += y * y;
        sumXY += x * y;
    }

    /**
     * Remove a X data point only.
     * @param x is the X data point to remove.
     */
    public final void removePoint(double x)
    {
        dataPoints--;
        if (dataPoints <= 0)
        {
            initialize();
        }
        else
        {
            sumX -= x;
            sumXSq -= x * x;
        }
    }

    /**
     * Remove a data point.
     * @param x is the X data point to remove.
     * @param y is the Y data point to remove.
     */
    public final void removePoint(double x, double y)
    {
        dataPoints--;
        if (dataPoints <= 0)
        {
            initialize();
        }
        else
        {
            sumX -= x;
            sumXSq -= x * x;
            sumY -= y;
            sumYSq -= y * y;
            sumXY -= x * y;
        }
    }

    /**
     * Calculates standard deviation for X based on the entire population given as arguments.
     * Equivalent to Microsoft Excel formula STDEVPA.
     * @return standard deviation assuming population for X
     */
    public final double getXStandardDeviationPop()
    {
        if (dataPoints == 0)
        {
            return Double.NaN;
        }

        double temp = (sumXSq - sumX * sumX / dataPoints) / dataPoints;
        return Math.sqrt(temp);
    }

    /**
     * Calculates standard deviation for Y based on the entire population given as arguments.
     * Equivalent to Microsoft Excel formula STDEVPA.
     * @return standard deviation assuming population for Y
     */
    public final double getYStandardDeviationPop()
    {
        if (dataPoints == 0)
        {
            return Double.NaN;
        }

        double temp = (sumYSq - sumY * sumY / dataPoints) / dataPoints;
        return Math.sqrt(temp);
    }

    /**
     * Calculates standard deviation for X based on the sample data points supplied.
     * Equivalent to Microsoft Excel formula STDEV.
     * @return standard deviation assuming sample for X
     */
    public final double getXStandardDeviationSample()
    {
        if (dataPoints < 2)
        {
            return Double.NaN;
        }

        double variance = getXVariance();
        return Math.sqrt(variance);
    }

    /**
     * Calculates standard deviation for Y based on the sample data points supplied.
     * Equivalent to Microsoft Excel formula STDEV.
     * @return standard deviation assuming sample for Y
     */
    public final double getYStandardDeviationSample()
    {
        if (dataPoints < 2)
        {
            return Double.NaN;
        }

        double variance = getYVariance();
        return Math.sqrt(variance);
    }

    /**
     * Calculates standard deviation for X based on the sample data points supplied.
     * Equivalent to Microsoft Excel formula STDEV.
     * @return variance as the square of the sample standard deviation for X
     */
    public final double getXVariance()
    {
        if (dataPoints < 2)
        {
            return Double.NaN;
        }

        double variance = (sumXSq - sumX * sumX / dataPoints) / (dataPoints - 1);
        return variance;
    }

    /**
     * Calculates standard deviation for Y based on the sample data points supplied.
     * Equivalent to Microsoft Excel formula STDEV.
     * @return variance as the square of the sample standard deviation for Y
     */
    public final double getYVariance()
    {
        if (dataPoints < 2)
        {
            return Double.NaN;
        }

        double variance = (sumYSq - sumY * sumY / dataPoints) / (dataPoints - 1);
        return variance;
    }

    /**
     * Returns the number of data points.
     * @return number of data points
     */
    public final long getN()
    {
        return dataPoints;
    }

    /**
     * Returns the sum of all X data points.
     * @return sum of X data points
     */
    public final double getXSum()
    {
        return sumX;
    }

    /**
     * Returns the sum of all Y data points.
     * @return sum of Y data points
     */
    public final double getYSum()
    {
        return sumY;
    }

    /**
     * Returns the average of all X data points.
     * @return average of X data points
     */
    public final double getXAverage()
    {
        if (dataPoints == 0)
        {
            return Double.NaN;
        }

        return sumX / dataPoints;
    }

    /**
     * Returns the average of all Y data points.
     * @return average of Y data points
     */
    public final double getYAverage()
    {
        if (dataPoints == 0)
        {
            return Double.NaN;
        }

        return sumY / dataPoints;
    }

    /**
     * For use by subclasses, returns sum (X * X).
     * @return sum of X squared
     */
    public final double getSumXSq()
    {
        return sumXSq;
    }

    /**
     * For use by subclasses, returns sum (Y * Y).
     * @return sum of Y squared
     */
    public final double getSumYSq()
    {
        return sumYSq;
    }

    /**
     * For use by subclasses, returns sum (X * Y).
     * @return sum of X times Y
     */
    public final double getSumXY()
    {
        return sumXY;
    }

    public final Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new EPException(e);
        }
    }

    public final String toString()
    {
        return "datapoints=" + this.dataPoints +
               "  sumX=" + this.sumX +
               "  sumXSq=" + this.sumXSq +
               "  sumY=" + this.sumY +
               "  sumYSq=" + this.sumYSq +
               "  sumXY=" + this.sumXY;
    }
}
