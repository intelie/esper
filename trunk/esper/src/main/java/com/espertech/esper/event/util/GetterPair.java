package com.espertech.esper.event.util;

import com.espertech.esper.client.EventPropertyGetter;

public class GetterPair
{
    private String name;
    private EventPropertyGetter getter;
    private JSONOutput output;

    public GetterPair(EventPropertyGetter getter, String name, JSONOutput output)
    {
        this.getter = getter;
        this.name = name;
        this.output = output;
    }

    public EventPropertyGetter getGetter()
    {
        return getter;
    }

    public String getName()
    {
        return name;
    }

    public JSONOutput getOutput()
    {
        return output;
    }
}
