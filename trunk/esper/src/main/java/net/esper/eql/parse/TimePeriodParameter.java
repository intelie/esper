package net.esper.eql.parse;

import net.esper.util.MetaDefItem;

/**
 * Parameter for views that accept time period information such as "5 sec 100 msec".
 */
public class TimePeriodParameter implements MetaDefItem
{
    private double numSeconds;

    /**
     * Ctor.
     * @param numSeconds is the number of seconds represented by time period string
     */
    public TimePeriodParameter(double numSeconds)
    {
        this.numSeconds = numSeconds;
    }

    /**
     * Returns the number of seconds.
     * @return the number of seconds specified by time period string
     */
    public double getNumSeconds()
    {
        return numSeconds;
    }

    public boolean equals(Object object)
    {
        if (!(object instanceof TimePeriodParameter))
        {
            return false;
        }
        TimePeriodParameter other = (TimePeriodParameter) object;
        return other.numSeconds == this.numSeconds; 
    }
}
