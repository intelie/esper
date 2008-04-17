package com.espertech.esper.plugin;

import java.io.Serializable;

public class PlugInEventTypeContext
{
    private final String pluginEventTypeURI;
    private final String eventTypeURI;
    private final Serializable typeInitializer;

    public PlugInEventTypeContext(String pluginEventTypeURI, String eventTypeURI, Serializable typeInitializer)
    {
        this.pluginEventTypeURI = pluginEventTypeURI;
        this.eventTypeURI = eventTypeURI;
        this.typeInitializer = typeInitializer;
    }

    public String getPluginEventTypeURI()
    {
        return pluginEventTypeURI;
    }

    public String getEventTypeURI()
    {
        return eventTypeURI;
    }

    public Serializable getTypeInitializer()
    {
        return typeInitializer;
    }
}
