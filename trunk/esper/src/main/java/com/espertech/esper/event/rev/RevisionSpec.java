package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;

public class RevisionSpec
{
    private final EventType fullEventType;
    private final EventType[] deltaTypes;
    private final String[] deltaAliases;
    private final String[] keyPropertyNames;

    public RevisionSpec(EventType fullEventType, EventType[] deltaTypes, String[] deltaAliases, String[] keyPropertyNames)
    {
        this.fullEventType = fullEventType;
        this.deltaTypes = deltaTypes;
        this.deltaAliases = deltaAliases;
        this.keyPropertyNames = keyPropertyNames;

        String allProperties[] = PropertyGroupBuilder.copyAndSort(fullEventType.getPropertyNames());
        PropertyGroupDesc[] desc = PropertyGroupBuilder.analyzeGroups(allProperties, deltaTypes, deltaAliases);        
    }

    public EventType getFullEventType()
    {
        return fullEventType;
    }

    public EventType[] getDeltaTypes()
    {
        return deltaTypes;
    }

    public String[] getDeltaAliases()
    {
        return deltaAliases;
    }

    public String[] getKeyPropertyNames()
    {
        return keyPropertyNames;
    }
}
