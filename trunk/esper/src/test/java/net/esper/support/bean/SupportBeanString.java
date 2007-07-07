package net.esper.support.bean;

import java.io.Serializable;

public class SupportBeanString implements Serializable
{
    private String string;

    public SupportBeanString(String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public String toString()
    {
        return "SupportBeanString string=" + string;
    }
}
