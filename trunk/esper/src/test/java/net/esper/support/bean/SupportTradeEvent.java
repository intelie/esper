package net.esper.support.bean;

public class SupportTradeEvent
{
    private int id;
    private String userId;
    private String ccypair;
    private String direction;

    public SupportTradeEvent(int id, String userId, String ccypair, String direction)
    {
        this.id = id;
        this.userId = userId;
        this.ccypair = ccypair;
        this.direction = direction;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getCcypair()
    {
        return ccypair;
    }

    public String getDirection()
    {
        return direction;
    }

    public String toString()
    {
        return "id=" + id +
               " userId=" + userId +
               " ccypair=" + ccypair +
               " direction=" + direction;
    }
}
