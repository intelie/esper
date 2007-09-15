package net.esper.support.bean;

public class SupportBeanBase implements SupportMarkerInterface
{
    private String id;

    public SupportBeanBase(String id)
    {
        this.id = id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public String toString()
    {
        return "id=" + id;
    }
}
