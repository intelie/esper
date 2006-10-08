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
}
