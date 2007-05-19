package net.esper.example.matchmaker.eventbean;

public enum AgeRange
{
    AGE_1(18, 25),
    AGE_2(26, 35),
    AGE_3(36, 45),
    AGE_4(46, 55),
    AGE_5(55, 65),
    AGE_6(65, Integer.MAX_VALUE);

    private int low;
    private int high;
    
    AgeRange(int low, int high)
    {
        this.low = low;
        this.high = high;
    }

    public int getLow()
    {
        return low;
    }

    public int getHigh()
    {
        return high;
    }

}
