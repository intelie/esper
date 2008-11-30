package com.espertech.esper.support.bean;

import java.util.Map;

public class SupportBeanMappedProp
{
    private final String id;
    private final Map<String, String> mapprop;

    public SupportBeanMappedProp(String id, Map<String, String> mapprop)
    {
        this.id = id;
        this.mapprop = mapprop;
    }

    public String getId()
    {
        return id;
    }

    public String getMapEntry(String key)
    {
        return mapprop.get(key);
    }
}
