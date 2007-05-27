package net.esper.client;

import java.util.Properties;

/**
 * Holds configuration for an input/output adapter loader.
 */
public class ConfigurationAdapterLoader
{
    private String loaderName;
    private String className;
    private Properties configProperties;

    /**
     * Ctor.
     */
    public ConfigurationAdapterLoader()
    {
    }

    /**
     * Returns the loader class name.
     * @return class name of loader
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Sets the loader classname.
     * @param className of loader
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * Returns loader configuration properties.
     * @return config entries
     */
    public Properties getConfigProperties()
    {
        return configProperties;
    }

    /**
     * Sets the loader configuration.
     * @param configProperties is the configuration of the loader
     */
    public void setConfigProperties(Properties configProperties)
    {
        this.configProperties = configProperties;
    }

    /**
     * Returns the loader name.
     * @return loader name
     */
    public String getLoaderName()
    {
        return loaderName;
    }

    /**
     * Sets the loader name.
     * @param loaderName is the loader name
     */
    public void setLoaderName(String loaderName)
    {
        this.loaderName = loaderName;
    }
}
