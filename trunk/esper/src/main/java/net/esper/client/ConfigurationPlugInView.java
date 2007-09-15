package net.esper.client;

import java.io.Serializable;

/**
 * Configuration information for plugging in a custom view.
 */
public class ConfigurationPlugInView implements Serializable
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

    /**
     * Returns the namespace
     * @return namespace
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * Returns the view name.
     * @return view name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the view factory class name.
     * @return factory class name
     */
    public String getFactoryClassName()
    {
        return factoryClassName;
    }

    /**
     * Sets the view namespace.
     * @param namespace to set
     */
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * Sets the view name.
     * @param name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the view factory class name.
     * @param factoryClassName is the class name of the view factory
     */
    public void setFactoryClassName(String factoryClassName)
    {
        this.factoryClassName = factoryClassName;
    }
}
