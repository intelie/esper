package com.espertech.esper.support.bean;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupportBeanBase that = (SupportBeanBase) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }
}
