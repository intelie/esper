package com.espertech.esper.support.bean;

import java.io.Serializable;

public class SupportSerializableBean implements Serializable
{
    private String id;

    public SupportSerializableBean(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof SupportSerializableBean))
        {
            return false;
        }
        SupportSerializableBean other = (SupportSerializableBean) obj;
        return other.id.equals(id);
    }

    public int hashCode()
    {
        return id.hashCode();
    }

    public String toString()
    {
        return this.getClass().getSimpleName() + " id=" + id; 
    }
}
