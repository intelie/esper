package com.espertech.esper.epl.core;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventTypeSPI;

import java.util.HashSet;

public class SelectExprEventTypeRegistry
{
    private HashSet<String> registry;

    public SelectExprEventTypeRegistry(HashSet<String> registry)
    {
        this.registry = registry;
    }

    public void add(EventType eventType)
    {
        if (!(eventType instanceof EventTypeSPI))
        {
            return;
        }
        registry.add(((EventTypeSPI) eventType).getMetadata().getPrimaryAssociationName());
    }
}
