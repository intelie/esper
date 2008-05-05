package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionTypeDescOverlayed
{
    private final EventPropertyGetter[] keyPropertyGetters;
    private final EventPropertyGetter[] changesetPropertyGetters;
    private final int[] changesetPropertyIndex;

    public RevisionTypeDescOverlayed(EventPropertyGetter[] keyPropertyGetters, EventPropertyGetter[] changesetPropertyGetters, int[] changesetPropertyIndex)
    {
        this.keyPropertyGetters = keyPropertyGetters;
        this.changesetPropertyGetters = changesetPropertyGetters;
        this.changesetPropertyIndex = changesetPropertyIndex;
    }

    public EventPropertyGetter[] getKeyPropertyGetters()
    {
        return keyPropertyGetters;
    }

    public EventPropertyGetter[] getChangesetPropertyGetters()
    {
        return changesetPropertyGetters;
    }

    public int[] getChangesetPropertyIndex()
    {
        return changesetPropertyIndex;
    }
}
