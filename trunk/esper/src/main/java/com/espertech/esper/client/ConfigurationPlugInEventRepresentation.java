package com.espertech.esper.client;

import java.io.Serializable;

public class ConfigurationPlugInEventRepresentation implements Serializable
{
    private String factoryClassName;
    private Serializable factoryConfiguration;

    public ConfigurationPlugInEventRepresentation()
    {
    }

    public String getFactoryClassName()
    {
        return factoryClassName;
    }

    public void setFactoryClassName(String factoryClassName)
    {
        this.factoryClassName = factoryClassName;
    }

    public Serializable getFactoryConfiguration()
    {
        return factoryConfiguration;
    }

    public void setFactoryConfiguration(Serializable factoryConfiguration)
    {
        this.factoryConfiguration = factoryConfiguration;
    }
}
