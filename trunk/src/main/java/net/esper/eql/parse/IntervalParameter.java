package net.esper.eql.parse;

public class IntervalParameter
{
    private double numSeconds;

    public IntervalParameter(double numSeconds)
    {
        this.numSeconds = numSeconds;
    }

    public double getNumSeconds()
    {
        return numSeconds;
    }

    public boolean equals(Object object)
    {
        if (!(object instanceof IntervalParameter))
        {
            return false;
        }
        IntervalParameter other = (IntervalParameter) object;
        return other.numSeconds == this.numSeconds; 
    }
}
