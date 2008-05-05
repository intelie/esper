package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;
import com.espertech.esper.client.ConfigurationRevisionEventType;

public class RevisionSpec
{
    private final ConfigurationRevisionEventType.PropertyRevision propertyRevision;
    private final EventType fullEventType;
    private final EventType[] deltaTypes;
    private final String[] deltaAliases;
    private final String[] keyPropertyNames;
    private final String[] changesetPropertyNames;
    private final String[] fullEventOnlyPropertyNames;

    public RevisionSpec(ConfigurationRevisionEventType.PropertyRevision propertyRevision, EventType fullEventType, EventType[] deltaTypes, String[] deltaAliases, String[] keyPropertyNames, String[] changesetPropertyNames, String[] fullEventOnlyPropertyNames)
    {
        this.propertyRevision = propertyRevision;
        this.fullEventType = fullEventType;
        this.deltaTypes = deltaTypes;
        this.deltaAliases = deltaAliases;
        this.keyPropertyNames = keyPropertyNames;
        this.changesetPropertyNames = changesetPropertyNames;
        this.fullEventOnlyPropertyNames = fullEventOnlyPropertyNames;
    }

    public ConfigurationRevisionEventType.PropertyRevision getPropertyRevision()
    {
        return propertyRevision;
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

    public String[] getChangesetPropertyNames()
    {
        return changesetPropertyNames;
    }

    public String[] getFullEventOnlyPropertyNames()
    {
        return fullEventOnlyPropertyNames;
    }
}
