package com.espertech.esper.plugin;

import com.espertech.esper.event.EventAdapterService;

import java.io.Serializable;
import java.net.URI;

/**
 * Context for use in {@link PlugInEventRepresentation} to initialize an implementation.
 */
public class PlugInEventRepresentationContext
{
    private final EventAdapterService eventAdapterService;
    private final URI eventRepresentationRootURI;
    private final Serializable representationInitializer;

    /**
     * Ctor.
     * @param eventAdapterService for creating further event types or wrapping event objects
     * @param eventRepresentationRootURI URI of the event representation
     * @param representationInitializer initializer objects
     */
    public PlugInEventRepresentationContext(EventAdapterService eventAdapterService, URI eventRepresentationRootURI, Serializable representationInitializer)
    {
        this.eventAdapterService = eventAdapterService;
        this.eventRepresentationRootURI = eventRepresentationRootURI;
        this.representationInitializer = representationInitializer;
    }

    /**
     * Ctor.
     * @return URI of event representation instance
     */
    public URI getEventRepresentationRootURI()
    {
        return eventRepresentationRootURI;
    }

    /**
     * Returns optional configuration for the event representation, or null if none supplied. An String XML document if
     * the configuration was read from an XML file.
     * @return configuration, or null if none supplied
     */
    public Serializable getRepresentationInitializer()
    {
        return representationInitializer;
    }

    /**
     * Returns the service for for creating further event types or wrapping event objects.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }
}
