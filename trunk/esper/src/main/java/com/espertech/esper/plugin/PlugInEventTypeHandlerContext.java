package com.espertech.esper.plugin;

import java.io.Serializable;
import java.net.URI;

public class PlugInEventTypeHandlerContext
{
    private final URI eventTypeResolutionURI;
    private final Serializable typeInitializer;
    private final String eventTypeAlias;

    public PlugInEventTypeHandlerContext(URI eventTypeURI, Serializable typeInitializer, String eventTypeAlias)
    {
        this.eventTypeResolutionURI = eventTypeURI;
        this.typeInitializer = typeInitializer;
        this.eventTypeAlias = eventTypeAlias;
    }

    public URI getEventTypeResolutionURI()
    {
        return eventTypeResolutionURI;
    }

    public Serializable getTypeInitializer()
    {
        return typeInitializer;
    }

    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }
}
