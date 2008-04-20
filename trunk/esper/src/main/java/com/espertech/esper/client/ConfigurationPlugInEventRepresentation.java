package com.espertech.esper.client;

import java.io.Serializable;

public class ConfigurationPlugInEventRepresentation implements Serializable
{
    private String eventRepresentationClassName;
    private Serializable configuration;

    public ConfigurationPlugInEventRepresentation()
    {
    }

    public String getEventRepresentationClassName()
    {
        return eventRepresentationClassName;
    }

    public void setEventRepresentationClassName(String factoryClassName)
    {
        this.eventRepresentationClassName = factoryClassName;
    }

    public Serializable getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Serializable configuration)
    {
        this.configuration = configuration;
    }
}
