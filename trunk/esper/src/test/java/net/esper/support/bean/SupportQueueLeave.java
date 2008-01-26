package net.esper.support.bean;

public class SupportQueueLeave
{
    private int id;
    private String location;
    private long timeLeave;

    public SupportQueueLeave(int id, String location, long timeLeave)
    {
        this.id = id;
        this.location = location;
        this.timeLeave = timeLeave;
    }

    public int getId()
    {
        return id;
    }

    public String getLocation()
    {
        return location;
    }

    public long getTimeLeave()
    {
        return timeLeave;
    }
}
