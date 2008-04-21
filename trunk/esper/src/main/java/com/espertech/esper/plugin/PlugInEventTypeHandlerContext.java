package com.espertech.esper.plugin;

import java.io.Serializable;
import java.net.URI;

/**
 * Context for use in {@link PlugInEventRepresentation} to provide information to help decide
 * whether an event representation can handle the requested event type.
 */
public class PlugInEventTypeHandlerContext
{
    private final URI eventTypeResolutionURI;
    private final Serializable typeInitializer;
    private final String eventTypeAlias;

    /**
     * Ctor.
     * @param eventTypeResolutionURI the URI specified for resolving the event type, may be a child URI
     * of the event representation URI and may carry additional parameters
     * @param typeInitializer optional configuration for the type, or null if none supplied
     * @param eventTypeAlias the name of the event
     */
    public PlugInEventTypeHandlerContext(URI eventTypeResolutionURI, Serializable typeInitializer, String eventTypeAlias)
    {
        this.eventTypeResolutionURI = eventTypeResolutionURI;
        this.typeInitializer = typeInitializer;
        this.eventTypeAlias = eventTypeAlias;
    }

    /**
     * Returns the URI specified for resolving the event type, may be a child URI
     * of the event representation URI and may carry additional parameters
     * @return URI
     */
    public URI getEventTypeResolutionURI()
    {
        return eventTypeResolutionURI;
    }

    /**
     * Returns optional configuration for the type, or null if none supplied. An String XML document if
     * the configuration was read from an XML file.
     * @return configuration, or null if none supplied
     */
    public Serializable getTypeInitializer()
    {
        return typeInitializer;
    }

    /**
     * Returns the name assigned to the event type.
     * @return alias
     */
    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }
}
