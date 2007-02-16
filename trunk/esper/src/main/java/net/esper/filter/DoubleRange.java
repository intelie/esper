package net.esper.filter;


/**
 * Holds a range of double values with a minimum (start) value and a maximum (end) value.
 */
public final class DoubleRange
{
    private double min;
    private double max;
    private int hashCode;

    /**
     * Constructor - takes range endpoints.
     * @param min is the low endpoint
     * @param max is the high endpoint
     */
    public DoubleRange(double min, double max)
    {
        this.min = min;
        this.max = max;

        if (min > max)
        {
            this.max = min;
            this.min = max;
        }

        hashCode = ((Double) min).hashCode() ^ ((Double) max).hashCode();
    }

    /**
     * Returns low endpoint.
     * @return low endpoint
     */
    public final double getMin()
    {
        return min;
    }

    /**
     * Returns high endpoint.
     * @return high endpoint
     */
    public final double getMax()
    {
        return max;
    }

    public final boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof DoubleRange))
        {
            return false;
        }

        DoubleRange otherRange = (DoubleRange) other;
        return ((otherRange.max == this.max) && (otherRange.min == this.min));
    }

    public final int hashCode()
    {
        return hashCode;
    }

    public final String toString()
    {
        return "DoubleRange" +
               " min=" + min +
               " max=" + max;
    }
}
