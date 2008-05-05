package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionTypeDescDeclared
{
    private final EventPropertyGetter[] keyPropertyGetters;
    private final EventPropertyGetter[] changesetPropertyGetters;
    private final PropertyGroupDesc group;

    public RevisionTypeDescDeclared(EventPropertyGetter[] keyPropertyGetters, EventPropertyGetter[] changesetPropertyGetters, PropertyGroupDesc group)
    {
        this.keyPropertyGetters = keyPropertyGetters;
        this.changesetPropertyGetters = changesetPropertyGetters;
        this.group = group;
    }

    public EventPropertyGetter[] getKeyPropertyGetters()
    {
        return keyPropertyGetters;
    }

    public EventPropertyGetter[] getChangesetPropertyGetters()
    {
        return changesetPropertyGetters;
    }

    public PropertyGroupDesc getGroup()
    {
        return group;
    }
}
