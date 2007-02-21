package net.esper.client;

import java.util.List;
import java.util.LinkedList;

/**
 * Configuration information for plugging in a custom view.
 */
public class ConfigurationPlugInView
{
    private String namespace;
    private String name;
    private String factoryClassName;

    /**
     * Ctor.
     */
    public ConfigurationPlugInView()
    {
    }

    public String getNamespace()
    {
        return namespace;
    }

    public String getName()
    {
        return name;
    }

    public String getFactoryClassName()
    {
        return factoryClassName;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setFactoryClassName(String factoryClassName)
    {
        this.factoryClassName = factoryClassName;
    }
}
