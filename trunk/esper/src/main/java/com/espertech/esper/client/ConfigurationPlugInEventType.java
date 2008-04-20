package com.espertech.esper.client;

import java.io.Serializable;
import java.net.URI;

public class ConfigurationPlugInEventType implements Serializable
{
    private URI[] eventTypeURI;
    private Serializable initializer;

    public ConfigurationPlugInEventType()
    {
    }

    public void setEventTypeURI(URI[] eventTypeURI)
    {
        this.eventTypeURI = eventTypeURI;
    }

    public void setInitializer(Serializable initializer)
    {
        this.initializer = initializer;
    }

    public URI[] getEventTypeURI()
    {
        return eventTypeURI;
    }

    public Serializable getInitializer()
    {
        return initializer;
    }
}
