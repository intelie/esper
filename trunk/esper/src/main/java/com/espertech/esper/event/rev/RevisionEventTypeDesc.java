package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionEventTypeDesc
{
    private final EventPropertyGetter[] keyPropertyGetters;
    private final EventPropertyGetter[] allPropertyGetters;
    private final PropertyGroupDesc group;

    public RevisionEventTypeDesc(EventPropertyGetter[] keyPropertyGetters, EventPropertyGetter[] allPropertyGetters, PropertyGroupDesc group)
    {
        this.keyPropertyGetters = keyPropertyGetters;
        this.allPropertyGetters = allPropertyGetters;
        this.group = group;
    }

    public EventPropertyGetter[] getKeyPropertyGetters()
    {
        return keyPropertyGetters;
    }

    public EventPropertyGetter[] getAllPropertyGetters()
    {
        return allPropertyGetters;
    }

    public PropertyGroupDesc getGroup()
    {
        return group;
    }
}
