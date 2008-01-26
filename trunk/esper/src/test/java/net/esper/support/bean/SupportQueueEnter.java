package net.esper.support.bean;

public class SupportQueueEnter
{
    private int id;
    private String location;
    private String sku;
    private long timeEnter;

    public SupportQueueEnter(int id, String location, String sku, long timeEnter)
    {
        this.id = id;
        this.location = location;
        this.sku = sku;
        this.timeEnter = timeEnter;
    }

    public int getId()
    {
        return id;
    }

    public String getLocation()
    {
        return location;
    }

    public String getSku()
    {
        return sku;
    }

    public long getTimeEnter()
    {
        return timeEnter;
    }
}
