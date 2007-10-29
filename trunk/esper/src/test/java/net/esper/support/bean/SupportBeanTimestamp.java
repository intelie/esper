package net.esper.support.bean;

public class SupportBeanTimestamp
{
    private String id;
    private long timestamp;
    private String groupId;

    public SupportBeanTimestamp(String id, long timestamp)
    {
        this.id = id;
        this.timestamp = timestamp;
    }

    public SupportBeanTimestamp(String id, String groupId, long timestamp)
    {
        this.id = id;
        this.groupId = groupId;
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

    public String getGroupId()
    {
        return groupId;
    }
}
