package com.espertech.esper.event.util;

import com.espertech.esper.client.EventPropertyGetter;

public class RendererMeta
{
    private final EventPropertyGetter[] getterSimple[];
    private final String[] simpleProps;

    public RendererMeta(EventPropertyGetter[][] getterSimple, String[] simpleProps)
    {
        this.getterSimple = getterSimple;
        this.simpleProps = simpleProps;
    }

    public EventPropertyGetter[][] getGetterSimple()
    {
        return getterSimple;
    }

    public String[] getSimpleProps()
    {
        return simpleProps;
    }
}
