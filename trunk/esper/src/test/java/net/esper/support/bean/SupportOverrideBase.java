package net.esper.support.bean;

import java.io.Serializable;

public class SupportOverrideBase implements Serializable, SupportMarkerInterface
{
    private String val;

    public SupportOverrideBase(String val)
    {
        this.val = val;
    }

    public String getVal()
    {
        return val;
    }
}
