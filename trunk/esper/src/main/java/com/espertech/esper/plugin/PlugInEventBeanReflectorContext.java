package com.espertech.esper.plugin;

import java.net.URI;

public class PlugInEventBeanReflectorContext
{
    private final URI resolutionURI;

    public PlugInEventBeanReflectorContext(URI uri)
    {
        this.resolutionURI = uri;
    }

    public URI getResolutionURI()
    {
        return resolutionURI;
    }
}
