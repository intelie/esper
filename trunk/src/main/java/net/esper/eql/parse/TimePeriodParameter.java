package net.esper.eql.parse;

public class TimePeriodParameter
{
    private double numSeconds;

    public TimePeriodParameter(double numSeconds)
    {
        this.numSeconds = numSeconds;
    }

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
