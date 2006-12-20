package net.esper.view.stat;


/**
 * Bean for calculating the regression slope and y intercept (same to Microsoft Excel LINEST function).
 */
public final class RegressionBean extends BaseStatisticsBean
{
    /**
     * Returns the Y intercept.
     * @return Y intercept
     */
    public double getYIntercept()
    {
        double slope = getSlope();

        if (slope == Double.NaN)
        {
            return Double.NaN;
        }

        return getYSum() / getN() - getSlope() * getXSum() / getN();
    }

    /**
     * Returns the slope.
     * @return regression slope
     */
    public double getSlope()
    {
        if (this.getN() == 0)
        {
            return Double.NaN;
        }

        double ssx = getSumXSq() - getXSum() * getXSum() / getN();

        if (ssx == 0)
        {
            return Double.NaN;
        }

        double sp = getSumXY() - this.getXSum() * this.getYSum() / getN();

        return sp / ssx;
    }

}
