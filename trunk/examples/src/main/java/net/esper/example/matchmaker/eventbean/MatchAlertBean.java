package net.esper.example.matchmaker.eventbean;

public class MatchAlertBean
{
    private int otherUserId;
    private int selfUserId;

    public MatchAlertBean(int otherUserId, int selfUserId)
    {
        this.otherUserId = otherUserId;
        this.selfUserId = selfUserId;
    }

    public int getSelfUserId()
    {
        return selfUserId;
    }

    public int getOtherUserId()
    {
        return otherUserId;
    }
}
