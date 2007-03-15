package net.esper.client;

import org.w3c.dom.Element;

import java.util.Properties;

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

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public Properties getConfigProperties()
    {
        return configProperties;
    }

    public void setConfigProperties(Properties configProperties)
    {
        this.configProperties = configProperties;
    }


    public String getLoaderName()
    {
        return loaderName;
    }

    public void setLoaderName(String loaderName)
    {
        this.loaderName = loaderName;
    }
}
