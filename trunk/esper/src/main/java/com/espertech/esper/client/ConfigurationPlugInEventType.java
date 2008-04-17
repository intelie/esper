package com.espertech.esper.client;

import java.io.Serializable;

public class ConfigurationPlugInEventType implements Serializable
{
    private String eventRepresentationURI;
    private Serializable initializer;

    public ConfigurationPlugInEventType()
    {
    }

    public void setEventRepresentationURI(String eventRepresentationURI)
    {
        this.eventRepresentationURI = eventRepresentationURI;
    }

    public void setInitializer(Serializable initializer)
    {
        this.initializer = initializer;
    }

    public String getEventRepresentationURI()
    {
        return eventRepresentationURI;
    }

    public Serializable getInitializer()
    {
        return initializer;
    }
}
