package com.espertech.esper.plugin;

import java.io.Serializable;
import java.net.URI;

public class PlugInEventTypeHandlerContext
{
    private final URI pluginEventTypeURI;
    private final URI eventTypeURI;
    private final Serializable typeInitializer;

    public PlugInEventTypeHandlerContext(URI pluginEventTypeURI, URI eventTypeURI, Serializable typeInitializer)
    {
        this.pluginEventTypeURI = pluginEventTypeURI;
        this.eventTypeURI = eventTypeURI;
        this.typeInitializer = typeInitializer;
    }

    public URI getPluginEventTypeURI()
    {
        return pluginEventTypeURI;
    }

    public URI getEventTypeURI()
    {
        return eventTypeURI;
    }

    public Serializable getTypeInitializer()
    {
        return typeInitializer;
    }
}
