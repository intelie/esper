package net.esper.support.bean;

public class SupportBeanTimestamp
{
    private String id;
    private long timestamp;

    public SupportBeanTimestamp(String id, long timestamp)
    {
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId()
    {
        return id;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}
