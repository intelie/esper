package com.espertech.esper.plugin;

import com.espertech.esper.event.EventAdapterService;

import java.io.Serializable;
import java.net.URI;

public class PlugInEventRepresentationContext
{
    private final EventAdapterService eventAdapterService;
    private final URI eventRepresentationURI;
    private final Serializable representationInitializer;

    public PlugInEventRepresentationContext(EventAdapterService eventAdapterService, URI eventRepresentationURI, Serializable representationInitializer)
    {
        this.eventAdapterService = eventAdapterService;
        this.eventRepresentationURI = eventRepresentationURI;
        this.representationInitializer = representationInitializer;
    }

    public URI getEventRepresentationURI()
    {
        return eventRepresentationURI;
    }

    public Serializable getRepresentationInitializer()
    {
        return representationInitializer;
    }

    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }
}
