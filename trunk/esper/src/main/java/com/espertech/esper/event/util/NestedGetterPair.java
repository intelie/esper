package com.espertech.esper.event.util;

import com.espertech.esper.client.EventPropertyGetter;

public class NestedGetterPair
{
    private String name;
    private EventPropertyGetter getter;
    private RendererMeta metadata;
    private boolean isArray;

    public NestedGetterPair(EventPropertyGetter getter, String name, RendererMeta metadata, boolean isArray)
    {
        this.getter = getter;
        this.name = name;
        this.metadata = metadata;
        this.isArray = isArray;
    }

    public EventPropertyGetter getGetter()
    {
        return getter;
    }

    public String getName()
    {
        return name;
    }

    public RendererMeta getMetadata()
    {
        return metadata;
    }

    public boolean isArray()
    {
        return isArray;
    }
}
