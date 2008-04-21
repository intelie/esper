package com.espertech.esper.plugin;

import java.net.URI;

/**
 * Context for use in {@link PlugInEventRepresentation} to provide information to help decide
 * whether an event representation can handle the requested resolution URI for creating event object wrappers.
 */
public class PlugInEventBeanReflectorContext
{
    private final URI resolutionURI;

    /**
     * Ctor.
     * @param uri is the resolution URI provided as part of {@link com.espertech.esper.client.EPRuntime#getEventSender(java.net.URI[])} 
     */
    public PlugInEventBeanReflectorContext(URI uri)
    {
        this.resolutionURI = uri;
    }

    /**
     * Returns the resolution URI.
     * @return resolution URI
     */
    public URI getResolutionURI()
    {
        return resolutionURI;
    }
}
