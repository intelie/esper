package com.espertech.esper.client;

import com.espertech.esper.event.EventType;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ConfigurationVariantEventType implements Serializable
{
    private List<String> eventTypeAliases;

    /**
     * Ctor.
     */
    public ConfigurationVariantEventType()
    {
        eventTypeAliases = new ArrayList<String>();
    }

    public List<String> getEventTypeAliases()
    {
        return eventTypeAliases;
    }

    public void addEventTypeAlias(String eventTypeAlias)
    {
        eventTypeAliases.add(eventTypeAlias);
    }
}
